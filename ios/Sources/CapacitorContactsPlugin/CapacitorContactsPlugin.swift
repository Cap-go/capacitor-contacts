import Capacitor
import Contacts
import UIKit

@objc(CapacitorContactsPlugin)
public class CapacitorContactsPlugin: CAPPlugin, CAPBridgedPlugin {
    private let PLUGIN_VERSION: String = "7.2.0"
    public let identifier = "CapacitorContactsPlugin"
    public let jsName = "CapacitorContacts"
    public let pluginMethods: [CAPPluginMethod] = [
        CAPPluginMethod(name: "countContacts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createContact", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "createGroup", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteContactById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "deleteGroupById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "displayContactById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "displayCreateContact", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "displayUpdateContactById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getAccounts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getContactById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getContacts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGroupById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getGroups", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isAvailable", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "isSupported", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "openSettings", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pickContact", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "pickContacts", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "updateContactById", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "checkPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "requestPermissions", returnType: CAPPluginReturnPromise),
        CAPPluginMethod(name: "getPluginVersion", returnType: CAPPluginReturnPromise)
    ]

    private let contactStore = CNContactStore()

    // MARK: - Implemented API surface

    @objc func countContacts(_ call: CAPPluginCall) {
        ensureAuthorized(call) {
            do {
                var count = 0
                let request = CNContactFetchRequest(keysToFetch: [CNContactIdentifierKey as CNKeyDescriptor])
                try self.contactStore.enumerateContacts(with: request) { _, _ in
                    count += 1
                }
                call.resolve(["count": count])
            } catch {
                call.reject("Failed to count contacts.", nil, error)
            }
        }
    }

    @objc func getContacts(_ call: CAPPluginCall) {
        ensureAuthorized(call) {
            let options = (call.options["options"] as? JSObject) ?? [:]
            let fields = (options["fields"] as? [String]).map { Set($0) }
            let limit = options["limit"] as? Int
            let offset = options["offset"] as? Int ?? 0

            let keysToFetch = self.keysToFetch(for: fields)
            let request = CNContactFetchRequest(keysToFetch: keysToFetch)
            request.sortOrder = .userDefault

            var contacts: [CNContact] = []
            var index = 0

            do {
                try self.contactStore.enumerateContacts(with: request) { contact, stop in
                    if index < offset {
                        index += 1
                        return
                    }
                    if let limit, contacts.count >= limit {
                        stop.pointee = true
                        return
                    }
                    contacts.append(contact)
                    index += 1
                }

                let includeGroupIds = fields == nil || fields!.contains("groupIds")
                var membership: [String: [String]] = [:]
                if includeGroupIds {
                    membership = self.groupMembershipMap(for: contacts.map(\.identifier))
                }

                let serialized = contacts.map { self.serialize(contact: $0, fields: fields, membership: membership) }
                call.resolve(["contacts": serialized])
            } catch {
                call.reject("Failed to fetch contacts.", nil, error)
            }
        }
    }

    @objc func getContactById(_ call: CAPPluginCall) {
        guard let options = call.options["options"] as? JSObject, let identifier = options["id"] as? String else {
            call.reject("Missing contact identifier.")
            return
        }

        ensureAuthorized(call) {
            let fields = (options["fields"] as? [String]).map { Set($0) }
            let keysToFetch = self.keysToFetch(for: fields)

            do {
                let contact = try self.contactStore.unifiedContact(withIdentifier: identifier, keysToFetch: keysToFetch)
                let membership = self.groupMembershipMap(for: [identifier])
                let serialized = self.serialize(contact: contact, fields: fields, membership: membership)
                call.resolve(["contact": serialized])
            } catch {
                call.reject("Failed to fetch contact.", nil, error)
            }
        }
    }

    @objc func getAccounts(_ call: CAPPluginCall) {
        do {
            let containers = try contactStore.containers(matching: nil)
            let accounts: [JSObject] = containers.map {
                var account: JSObject = [:]
                account["name"] = $0.name
                account["type"] = mapContainerType($0.type)
                return account
            }
            call.resolve(["accounts": accounts])
        } catch {
            call.reject("Failed to fetch accounts.", nil, error)
        }
    }

    @objc func isSupported(_ call: CAPPluginCall) {
        call.resolve(["isSupported": true])
    }

    @objc func isAvailable(_ call: CAPPluginCall) {
        call.resolve(["isAvailable": true])
    }

    @objc func openSettings(_ call: CAPPluginCall) {
        DispatchQueue.main.async {
            guard let url = URL(string: UIApplication.openSettingsURLString) else {
                call.reject("Unable to open settings.")
                return
            }
            UIApplication.shared.open(url, options: [:]) { success in
                if success {
                    call.resolve()
                } else {
                    call.reject("Unable to open settings.")
                }
            }
        }
    }

    @objc func getPluginVersion(_ call: CAPPluginCall) {
        call.resolve(["version": self.PLUGIN_VERSION])
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        let status = authorizationStatus()
        call.resolve(status)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        let requested = (call.options["options"] as? JSObject)?["permissions"] as? [String] ?? ["readContacts", "writeContacts"]
        let needsRequest = requested.contains { _ in mapAuthorizationStatus(CNContactStore.authorizationStatus(for: .contacts)) != "granted" }

        if !needsRequest {
            call.resolve(authorizationStatus())
            return
        }

        contactStore.requestAccess(for: .contacts, completionHandler: { (granted: Bool, error: Error?) in
            DispatchQueue.main.async {
                if let error {
                    call.reject("Permission request failed.", nil, error)
                    return
                }
                _ = granted
                call.resolve(self.authorizationStatus())
            }
        })
    }

    // MARK: - Not yet implemented operations

    private func notImplemented(_ call: CAPPluginCall) {
        call.reject("Method not implemented yet.")
    }

    @objc func createContact(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func createGroup(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func deleteContactById(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func deleteGroupById(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func displayContactById(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func displayCreateContact(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func displayUpdateContactById(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func getGroupById(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func getGroups(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func pickContact(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func pickContacts(_ call: CAPPluginCall) { notImplemented(call) }
    @objc func updateContactById(_ call: CAPPluginCall) { notImplemented(call) }

    // MARK: - Helpers

    private func ensureAuthorized(_ call: CAPPluginCall, completion: @escaping () -> Void) {
        switch CNContactStore.authorizationStatus(for: .contacts) {
        case .authorized, .limited:
            completion()
        case .notDetermined:
            contactStore.requestAccess(for: .contacts, completionHandler: { granted, error in
                DispatchQueue.main.async {
                    if let error {
                        call.reject("Permission request failed.", nil, error)
                        return
                    }
                    if granted {
                        completion()
                    } else {
                        call.reject("Contacts permission not granted.")
                    }
                }
            })
        case .denied, .restricted:
            call.reject("Contacts permission not granted.")
        @unknown default:
            call.reject("Contacts permission state unknown.")
        }
    }

    private func authorizationStatus() -> JSObject {
        let status = mapAuthorizationStatus(CNContactStore.authorizationStatus(for: .contacts))
        return [
            "readContacts": status,
            "writeContacts": status
        ]
    }

    private func mapAuthorizationStatus(_ status: CNAuthorizationStatus) -> String {
        switch status {
        case .authorized, .limited:
            return "granted"
        case .denied:
            return "denied"
        case .restricted:
            return "denied"
        case .notDetermined:
            return "prompt"
        @unknown default:
            return "prompt"
        }
    }

    private func keysToFetch(for fields: Set<String>?) -> [CNKeyDescriptor] {
        var keys: [CNKeyDescriptor] = [
            CNContactIdentifierKey as CNKeyDescriptor,
            CNContactGivenNameKey as CNKeyDescriptor,
            CNContactFamilyNameKey as CNKeyDescriptor,
            CNContactMiddleNameKey as CNKeyDescriptor,
            CNContactNamePrefixKey as CNKeyDescriptor,
            CNContactNameSuffixKey as CNKeyDescriptor,
            CNContactOrganizationNameKey as CNKeyDescriptor,
            CNContactJobTitleKey as CNKeyDescriptor,
            CNContactEmailAddressesKey as CNKeyDescriptor,
            CNContactPhoneNumbersKey as CNKeyDescriptor,
            CNContactPostalAddressesKey as CNKeyDescriptor,
            CNContactUrlAddressesKey as CNKeyDescriptor,
            CNContactBirthdayKey as CNKeyDescriptor,
            CNContactNoteKey as CNKeyDescriptor,
            CNContactImageDataAvailableKey as CNKeyDescriptor,
            CNContactImageDataKey as CNKeyDescriptor
        ]

        if let fields, fields.contains("groupIds") {
            // No additional keys required, but this preserves the behaviour if custom keys are needed later.
        }

        if let fields, fields.contains("fullName") {
            keys.append(CNContactFormatter.descriptorForRequiredKeys(for: .fullName))
        }

        return keys
    }

    private func groupMembershipMap(for identifiers: [String]) -> [String: [String]] {
        guard !identifiers.isEmpty else { return [:] }

        var result: [String: [String]] = [:]
        let identifierSet = Set(identifiers)

        do {
            let groups = try contactStore.groups(matching: nil)
            for group in groups {
                let predicate = CNContact.predicateForContactsInGroup(withIdentifier: group.identifier)
                let members = try contactStore.unifiedContacts(matching: predicate, keysToFetch: [CNContactIdentifierKey as CNKeyDescriptor])
                for member in members where identifierSet.contains(member.identifier) {
                    result[member.identifier, default: []].append(group.identifier)
                }
            }
        } catch {
            CAPLog.print("CapacitorContactsPlugin", "Failed to compute group membership: \(error.localizedDescription)")
        }

        return result
    }

    private func serialize(contact: CNContact, fields: Set<String>?, membership: [String: [String]]) -> JSObject {
        let includeAll = fields == nil
        func shouldInclude(_ field: String) -> Bool {
            includeAll || fields!.contains(field)
        }

        var result: JSObject = [:]

        if shouldInclude("id") {
            result["id"] = contact.identifier
        }
        if shouldInclude("givenName") {
            result["givenName"] = contact.givenName
        }
        if shouldInclude("familyName") {
            result["familyName"] = contact.familyName
        }
        if shouldInclude("middleName") {
            result["middleName"] = contact.middleName
        }
        if shouldInclude("namePrefix") {
            result["namePrefix"] = contact.namePrefix
        }
        if shouldInclude("nameSuffix") {
            result["nameSuffix"] = contact.nameSuffix
        }
        if shouldInclude("organizationName") {
            result["organizationName"] = contact.organizationName
        }
        if shouldInclude("jobTitle") {
            result["jobTitle"] = contact.jobTitle
        }
        if shouldInclude("note") {
            result["note"] = contact.note
        }
        if shouldInclude("fullName") {
            result["fullName"] = CNContactFormatter.string(from: contact, style: .fullName) ?? ""
        }

        if shouldInclude("emailAddresses") {
            let emails: [JSObject] = contact.emailAddresses.map { labeledValue in
                var entry: JSObject = [:]
                entry["value"] = labeledValue.value as String
                let (type, label) = mapEmailLabel(labeledValue.label)
                entry["type"] = type
                if let label { entry["label"] = label }
                entry["isPrimary"] = false
                return entry
            }
            result["emailAddresses"] = emails
        }

        if shouldInclude("phoneNumbers") {
            let phones: [JSObject] = contact.phoneNumbers.map { labeledValue in
                var entry: JSObject = [:]
                entry["value"] = labeledValue.value.stringValue
                let (type, label) = mapPhoneLabel(labeledValue.label)
                entry["type"] = type
                if let label { entry["label"] = label }
                entry["isPrimary"] = false
                return entry
            }
            result["phoneNumbers"] = phones
        }

        if shouldInclude("postalAddresses") {
            let addresses: [JSObject] = contact.postalAddresses.map { labeledValue in
                let postal = labeledValue.value
                var entry: JSObject = [:]
                entry["city"] = postal.city
                entry["country"] = postal.country
                entry["formatted"] = CNPostalAddressFormatter.string(from: postal, style: .mailingAddress)
                entry["isoCountryCode"] = postal.isoCountryCode
                entry["isPrimary"] = false
                entry["neighborhood"] = postal.subLocality
                entry["postalCode"] = postal.postalCode
                entry["state"] = postal.state
                entry["street"] = postal.street
                let (type, label) = mapPostalLabel(labeledValue.label)
                entry["type"] = type
                if let label { entry["label"] = label }
                return entry
            }
            result["postalAddresses"] = addresses
        }

        if shouldInclude("urlAddresses") {
            let urls: [JSObject] = contact.urlAddresses.map { labeledValue in
                var entry: JSObject = [:]
                entry["value"] = labeledValue.value as String
                let (type, label) = mapURLLabel(labeledValue.label)
                entry["type"] = type
                if let label { entry["label"] = label }
                return entry
            }
            result["urlAddresses"] = urls
        }

        if shouldInclude("birthday"), let birthday = contact.birthday {
            var birthdayJS: JSObject = [:]
            if let day = birthday.day { birthdayJS["day"] = day }
            if let month = birthday.month { birthdayJS["month"] = month }
            if let year = birthday.year { birthdayJS["year"] = year }
            result["birthday"] = birthdayJS
        }

        if shouldInclude("photo"), contact.imageDataAvailable, let imageData = contact.imageData {
            result["photo"] = imageData.base64EncodedString()
        }

        if shouldInclude("groupIds") {
            result["groupIds"] = membership[contact.identifier] ?? []
        }

        if shouldInclude("account") {
            result["account"] = NSNull()
        }

        return result
    }

    private func mapEmailLabel(_ label: String?) -> (String, String?) {
        switch label {
        case CNLabelHome:
            return ("HOME", nil)
        case CNLabelWork:
            return ("WORK", nil)
        case CNLabelEmailiCloud:
            return ("ICLOUD", nil)
        case CNLabelOther:
            return ("OTHER", nil)
        case .none:
            return ("OTHER", nil)
        default:
            return ("CUSTOM", CNLabeledValue<NSString>.localizedString(forLabel: label ?? ""))
        }
    }

    private func mapPhoneLabel(_ label: String?) -> (String, String?) {
        switch label {
        case CNLabelPhoneNumberMobile:
            return ("MOBILE", nil)
        case CNLabelPhoneNumberiPhone:
            return ("IPHONE", nil)
        case CNLabelPhoneNumberMain:
            return ("MAIN", nil)
        case CNLabelPhoneNumberHomeFax:
            return ("HOME_FAX", nil)
        case CNLabelPhoneNumberWorkFax:
            return ("WORK_FAX", nil)
        case CNLabelPhoneNumberOtherFax:
            return ("OTHER_FAX", nil)
        case CNLabelPhoneNumberPager:
            return ("PAGER", nil)
        case CNLabelHome:
            return ("HOME", nil)
        case CNLabelWork:
            return ("WORK", nil)
        case CNLabelOther:
            return ("OTHER", nil)
        case .none:
            return ("OTHER", nil)
        default:
            return ("CUSTOM", CNLabeledValue<NSString>.localizedString(forLabel: label ?? ""))
        }
    }

    private func mapPostalLabel(_ label: String?) -> (String, String?) {
        switch label {
        case CNLabelHome:
            return ("HOME", nil)
        case CNLabelWork:
            return ("WORK", nil)
        case CNLabelOther:
            return ("OTHER", nil)
        case .none:
            return ("OTHER", nil)
        default:
            return ("CUSTOM", CNLabeledValue<CNPostalAddress>.localizedString(forLabel: label ?? ""))
        }
    }

    private func mapURLLabel(_ label: String?) -> (String, String?) {
        switch label {
        case CNLabelURLAddressHomePage:
            return ("HOMEPAGE", nil)
        case CNLabelHome:
            return ("HOME", nil)
        case CNLabelWork:
            return ("WORK", nil)
        case CNLabelOther:
            return ("OTHER", nil)
        case .none:
            return ("OTHER", nil)
        default:
            return ("CUSTOM", CNLabeledValue<NSString>.localizedString(forLabel: label ?? ""))
        }
    }

    private func mapContainerType(_ type: CNContainerType) -> String {
        switch type {
        case .local:
            return "local"
        case .exchange:
            return "exchange"
        case .cardDAV:
            return "carddav"
        case .unassigned:
            return "unassigned"
        @unknown default:
            return "unknown"
        }
    }
}
