# @capgo/capacitor-contacts
 <a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_contacts"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_contacts"> Missing a feature? We’ll build the plugin for you 💪</a></h2>
</div>


Manage device contacts across iOS, Android, and the Web with a unified Capacitor API.

WIP: the plugin is not yet ready for production

## Documentation

The most complete doc is available here: https://capgo.app/docs/plugins/contacts/

## Install

```bash
npm install @capgo/capacitor-contacts
npx cap sync
```

## API

<docgen-index>

* [`countContacts()`](#countcontacts)
* [`createContact(...)`](#createcontact)
* [`createGroup(...)`](#creategroup)
* [`deleteContactById(...)`](#deletecontactbyid)
* [`deleteGroupById(...)`](#deletegroupbyid)
* [`displayContactById(...)`](#displaycontactbyid)
* [`displayCreateContact(...)`](#displaycreatecontact)
* [`displayUpdateContactById(...)`](#displayupdatecontactbyid)
* [`getAccounts()`](#getaccounts)
* [`getContactById(...)`](#getcontactbyid)
* [`getContacts(...)`](#getcontacts)
* [`getGroupById(...)`](#getgroupbyid)
* [`getGroups()`](#getgroups)
* [`isAvailable()`](#isavailable)
* [`isSupported()`](#issupported)
* [`openSettings()`](#opensettings)
* [`pickContact(...)`](#pickcontact)
* [`pickContacts(...)`](#pickcontacts)
* [`updateContactById(...)`](#updatecontactbyid)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`getPluginVersion()`](#getpluginversion)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Capacitor Contacts Plugin interface for managing device contacts.

### countContacts()

```typescript
countContacts() => Promise<CountContactsResult>
```

Count the total number of contacts on the device.

**Returns:** <code>Promise&lt;<a href="#countcontactsresult">CountContactsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### createContact(...)

```typescript
createContact(options: CreateContactOptions) => Promise<CreateContactResult>
```

Create a new contact programmatically.

| Param         | Type                                                                  | Description                         |
| ------------- | --------------------------------------------------------------------- | ----------------------------------- |
| **`options`** | <code><a href="#createcontactoptions">CreateContactOptions</a></code> | - The contact information to create |

**Returns:** <code>Promise&lt;<a href="#createcontactresult">CreateContactResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### createGroup(...)

```typescript
createGroup(options: CreateGroupOptions) => Promise<CreateGroupResult>
```

Create a new contact group.

| Param         | Type                                                              | Description                       |
| ------------- | ----------------------------------------------------------------- | --------------------------------- |
| **`options`** | <code><a href="#creategroupoptions">CreateGroupOptions</a></code> | - The group information to create |

**Returns:** <code>Promise&lt;<a href="#creategroupresult">CreateGroupResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### deleteContactById(...)

```typescript
deleteContactById(options: DeleteContactByIdOptions) => Promise<void>
```

Delete a contact by ID.

| Param         | Type                                                                          | Description                       |
| ------------- | ----------------------------------------------------------------------------- | --------------------------------- |
| **`options`** | <code><a href="#deletecontactbyidoptions">DeleteContactByIdOptions</a></code> | - The ID of the contact to delete |

**Since:** 1.0.0

--------------------


### deleteGroupById(...)

```typescript
deleteGroupById(options: DeleteGroupByIdOptions) => Promise<void>
```

Delete a group by ID.

| Param         | Type                                                                      | Description                     |
| ------------- | ------------------------------------------------------------------------- | ------------------------------- |
| **`options`** | <code><a href="#deletegroupbyidoptions">DeleteGroupByIdOptions</a></code> | - The ID of the group to delete |

**Since:** 1.0.0

--------------------


### displayContactById(...)

```typescript
displayContactById(options: DisplayContactByIdOptions) => Promise<void>
```

Display a contact using the native contact viewer.

| Param         | Type                                                                            | Description                        |
| ------------- | ------------------------------------------------------------------------------- | ---------------------------------- |
| **`options`** | <code><a href="#displaycontactbyidoptions">DisplayContactByIdOptions</a></code> | - The ID of the contact to display |

**Since:** 1.0.0

--------------------


### displayCreateContact(...)

```typescript
displayCreateContact(options?: DisplayCreateContactOptions | undefined) => Promise<DisplayCreateContactResult>
```

Display the native create contact UI.

| Param         | Type                                                                                | Description                               |
| ------------- | ----------------------------------------------------------------------------------- | ----------------------------------------- |
| **`options`** | <code><a href="#displaycreatecontactoptions">DisplayCreateContactOptions</a></code> | - Optional pre-filled contact information |

**Returns:** <code>Promise&lt;<a href="#displaycreatecontactresult">DisplayCreateContactResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### displayUpdateContactById(...)

```typescript
displayUpdateContactById(options: DisplayUpdateContactByIdOptions) => Promise<void>
```

Display the native update contact UI for a specific contact.

| Param         | Type                                                                                        | Description                       |
| ------------- | ------------------------------------------------------------------------------------------- | --------------------------------- |
| **`options`** | <code><a href="#displayupdatecontactbyidoptions">DisplayUpdateContactByIdOptions</a></code> | - The ID of the contact to update |

**Since:** 1.0.0

--------------------


### getAccounts()

```typescript
getAccounts() => Promise<GetAccountsResult>
```

Get all accounts available on the device.

**Returns:** <code>Promise&lt;<a href="#getaccountsresult">GetAccountsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getContactById(...)

```typescript
getContactById(options: GetContactByIdOptions) => Promise<GetContactByIdResult>
```

Get a specific contact by ID.

| Param         | Type                                                                    | Description                              |
| ------------- | ----------------------------------------------------------------------- | ---------------------------------------- |
| **`options`** | <code><a href="#getcontactbyidoptions">GetContactByIdOptions</a></code> | - The ID and optional fields to retrieve |

**Returns:** <code>Promise&lt;<a href="#getcontactbyidresult">GetContactByIdResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getContacts(...)

```typescript
getContacts(options?: GetContactsOptions | undefined) => Promise<GetContactsResult>
```

Get all contacts from the device.

| Param         | Type                                                              | Description                               |
| ------------- | ----------------------------------------------------------------- | ----------------------------------------- |
| **`options`** | <code><a href="#getcontactsoptions">GetContactsOptions</a></code> | - Optional filters and pagination options |

**Returns:** <code>Promise&lt;<a href="#getcontactsresult">GetContactsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getGroupById(...)

```typescript
getGroupById(options: GetGroupByIdOptions) => Promise<GetGroupByIdResult>
```

Get a specific group by ID.

| Param         | Type                                                                | Description                       |
| ------------- | ------------------------------------------------------------------- | --------------------------------- |
| **`options`** | <code><a href="#getgroupbyidoptions">GetGroupByIdOptions</a></code> | - The ID of the group to retrieve |

**Returns:** <code>Promise&lt;<a href="#getgroupbyidresult">GetGroupByIdResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getGroups()

```typescript
getGroups() => Promise<GetGroupsResult>
```

Get all contact groups.

**Returns:** <code>Promise&lt;<a href="#getgroupsresult">GetGroupsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

Check if contacts are available on the device.

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Check if the plugin is supported on the current platform.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the device's contacts settings.

**Since:** 1.0.0

--------------------


### pickContact(...)

```typescript
pickContact(options?: PickContactsOptions | undefined) => Promise<PickContactResult>
```

<a href="#pick">Pick</a> a single contact using the native contact picker.

| Param         | Type                                                                | Description                                            |
| ------------- | ------------------------------------------------------------------- | ------------------------------------------------------ |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> | - Optional fields to retrieve and picker configuration |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### pickContacts(...)

```typescript
pickContacts(options?: PickContactsOptions | undefined) => Promise<PickContactsResult>
```

<a href="#pick">Pick</a> one or more contacts using the native contact picker.

| Param         | Type                                                                | Description                                            |
| ------------- | ------------------------------------------------------------------- | ------------------------------------------------------ |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> | - Optional fields to retrieve and picker configuration |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

**Since:** 1.0.0

--------------------


### updateContactById(...)

```typescript
updateContactById(options: UpdateContactByIdOptions) => Promise<void>
```

Update an existing contact by ID.

| Param         | Type                                                                          | Description                              |
| ------------- | ----------------------------------------------------------------------------- | ---------------------------------------- |
| **`options`** | <code><a href="#updatecontactbyidoptions">UpdateContactByIdOptions</a></code> | - The ID and updated contact information |

**Since:** 1.0.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check the current permission status for contacts.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 1.0.0

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

Request permissions to access contacts.

| Param         | Type                                                                            | Description                                |
| ------------- | ------------------------------------------------------------------------------- | ------------------------------------------ |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> | - Optional specific permissions to request |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 1.0.0

--------------------


### getPluginVersion()

```typescript
getPluginVersion() => Promise<{ version: string; }>
```

Get the native Capacitor plugin version.

**Returns:** <code>Promise&lt;{ version: string; }&gt;</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### CountContactsResult

Result from counting contacts.

| Prop        | Type                | Description               | Since |
| ----------- | ------------------- | ------------------------- | ----- |
| **`count`** | <code>number</code> | Total number of contacts. | 1.0.0 |


#### CreateContactResult

Result from creating a contact.

| Prop     | Type                | Description                          | Since |
| -------- | ------------------- | ------------------------------------ | ----- |
| **`id`** | <code>string</code> | The ID of the newly created contact. | 1.0.0 |


#### CreateContactOptions

Options for creating a contact.

| Prop          | Type                                                                              | Description                                                                                           | Since |
| ------------- | --------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------- | ----- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | <a href="#contact">Contact</a> information to create. The 'id' field will be generated automatically. | 1.0.0 |


#### Contact

<a href="#contact">Contact</a> information.

| Prop                   | Type                                          | Description                                                   | Since |
| ---------------------- | --------------------------------------------- | ------------------------------------------------------------- | ----- |
| **`id`**               | <code>string</code>                           | Unique identifier for the contact.                            | 1.0.0 |
| **`account`**          | <code><a href="#account">Account</a></code>   | <a href="#account">Account</a> information for the contact.   | 1.0.0 |
| **`birthday`**         | <code><a href="#birthday">Birthday</a></code> | <a href="#birthday">Birthday</a> information for the contact. | 1.0.0 |
| **`emailAddresses`**   | <code>EmailAddress[]</code>                   | Email addresses for the contact.                              | 1.0.0 |
| **`familyName`**       | <code>string</code>                           | Family name (last name) of the contact.                       | 1.0.0 |
| **`fullName`**         | <code>string</code>                           | Full name of the contact.                                     | 1.0.0 |
| **`givenName`**        | <code>string</code>                           | Given name (first name) of the contact.                       | 1.0.0 |
| **`groupIds`**         | <code>string[]</code>                         | <a href="#group">Group</a> IDs the contact belongs to.        | 1.0.0 |
| **`jobTitle`**         | <code>string</code>                           | Job title of the contact.                                     | 1.0.0 |
| **`middleName`**       | <code>string</code>                           | Middle name of the contact.                                   | 1.0.0 |
| **`namePrefix`**       | <code>string</code>                           | Name prefix (e.g., "Dr.", "Mr.", "Ms.") of the contact.       | 1.0.0 |
| **`nameSuffix`**       | <code>string</code>                           | Name suffix (e.g., "Jr.", "Sr.", "III") of the contact.       | 1.0.0 |
| **`note`**             | <code>string</code>                           | Notes about the contact.                                      | 1.0.0 |
| **`organizationName`** | <code>string</code>                           | Organization name of the contact.                             | 1.0.0 |
| **`phoneNumbers`**     | <code>PhoneNumber[]</code>                    | Phone numbers for the contact.                                | 1.0.0 |
| **`photo`**            | <code>string</code>                           | Base64-encoded photo of the contact.                          | 1.0.0 |
| **`postalAddresses`**  | <code>PostalAddress[]</code>                  | Postal addresses for the contact.                             | 1.0.0 |
| **`urlAddresses`**     | <code>UrlAddress[]</code>                     | URL addresses for the contact.                                | 1.0.0 |


#### Account

<a href="#account">Account</a> information for a contact.

| Prop       | Type                | Description              | Since |
| ---------- | ------------------- | ------------------------ | ----- |
| **`name`** | <code>string</code> | The name of the account. | 1.0.0 |
| **`type`** | <code>string</code> | The type of the account. | 1.0.0 |


#### Birthday

<a href="#birthday">Birthday</a> information for a contact.

| Prop        | Type                | Description                  | Since |
| ----------- | ------------------- | ---------------------------- | ----- |
| **`day`**   | <code>number</code> | The day of the month (1-31). | 1.0.0 |
| **`month`** | <code>number</code> | The month (1-12).            | 1.0.0 |
| **`year`**  | <code>number</code> | The year.                    | 1.0.0 |


#### EmailAddress

Email address information for a contact.

| Prop            | Type                                                          | Description                                | Since |
| --------------- | ------------------------------------------------------------- | ------------------------------------------ | ----- |
| **`value`**     | <code>string</code>                                           | The email address value.                   | 1.0.0 |
| **`type`**      | <code><a href="#emailaddresstype">EmailAddressType</a></code> | The type of email address.                 | 1.0.0 |
| **`label`**     | <code>string</code>                                           | Custom label for the email address.        | 1.0.0 |
| **`isPrimary`** | <code>boolean</code>                                          | Whether this is the primary email address. | 1.0.0 |


#### PhoneNumber

Phone number information for a contact.

| Prop            | Type                                                        | Description                               | Since |
| --------------- | ----------------------------------------------------------- | ----------------------------------------- | ----- |
| **`value`**     | <code>string</code>                                         | The phone number value.                   | 1.0.0 |
| **`type`**      | <code><a href="#phonenumbertype">PhoneNumberType</a></code> | The type of phone number.                 | 1.0.0 |
| **`label`**     | <code>string</code>                                         | Custom label for the phone number.        | 1.0.0 |
| **`isPrimary`** | <code>boolean</code>                                        | Whether this is the primary phone number. | 1.0.0 |


#### PostalAddress

Postal address information for a contact.

| Prop                 | Type                                                            | Description                                 | Since |
| -------------------- | --------------------------------------------------------------- | ------------------------------------------- | ----- |
| **`city`**           | <code>string</code>                                             | The city name.                              | 1.0.0 |
| **`country`**        | <code>string</code>                                             | The country name.                           | 1.0.0 |
| **`formatted`**      | <code>string</code>                                             | The formatted address string.               | 1.0.0 |
| **`isoCountryCode`** | <code>string</code>                                             | The ISO country code.                       | 1.0.0 |
| **`isPrimary`**      | <code>boolean</code>                                            | Whether this is the primary postal address. | 1.0.0 |
| **`label`**          | <code>string</code>                                             | Custom label for the postal address.        | 1.0.0 |
| **`neighborhood`**   | <code>string</code>                                             | The neighborhood name.                      | 1.0.0 |
| **`postalCode`**     | <code>string</code>                                             | The postal code.                            | 1.0.0 |
| **`state`**          | <code>string</code>                                             | The state or province name.                 | 1.0.0 |
| **`street`**         | <code>string</code>                                             | The street address.                         | 1.0.0 |
| **`type`**           | <code><a href="#postaladdresstype">PostalAddressType</a></code> | The type of postal address.                 | 1.0.0 |


#### UrlAddress

URL address information for a contact.

| Prop        | Type                                                      | Description               | Since |
| ----------- | --------------------------------------------------------- | ------------------------- | ----- |
| **`value`** | <code>string</code>                                       | The URL value.            | 1.0.0 |
| **`type`**  | <code><a href="#urladdresstype">UrlAddressType</a></code> | The type of URL.          | 1.0.0 |
| **`label`** | <code>string</code>                                       | Custom label for the URL. | 1.0.0 |


#### CreateGroupResult

Result from creating a group.

| Prop     | Type                | Description                        | Since |
| -------- | ------------------- | ---------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the newly created group. | 1.0.0 |


#### CreateGroupOptions

Options for creating a group.

| Prop        | Type                                                                          | Description                                                                                       | Since |
| ----------- | ----------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------- | ----- |
| **`group`** | <code><a href="#omit">Omit</a>&lt;<a href="#group">Group</a>, 'id'&gt;</code> | <a href="#group">Group</a> information to create. The 'id' field will be generated automatically. | 1.0.0 |


#### Group

<a href="#contact">Contact</a> group information.

| Prop       | Type                | Description                      | Since |
| ---------- | ------------------- | -------------------------------- | ----- |
| **`id`**   | <code>string</code> | Unique identifier for the group. | 1.0.0 |
| **`name`** | <code>string</code> | Name of the group.               | 1.0.0 |


#### DeleteContactByIdOptions

Options for deleting a contact by ID.

| Prop     | Type                | Description                      | Since |
| -------- | ------------------- | -------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the contact to delete. | 1.0.0 |


#### DeleteGroupByIdOptions

Options for deleting a group by ID.

| Prop     | Type                | Description                    | Since |
| -------- | ------------------- | ------------------------------ | ----- |
| **`id`** | <code>string</code> | The ID of the group to delete. | 1.0.0 |


#### DisplayContactByIdOptions

Options for displaying a contact by ID.

| Prop     | Type                | Description                       | Since |
| -------- | ------------------- | --------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the contact to display. | 1.0.0 |


#### DisplayCreateContactResult

Result from displaying the native create contact UI.

| Prop     | Type                | Description                                                                         | Since |
| -------- | ------------------- | ----------------------------------------------------------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the created contact, if one was created. Undefined if the user cancelled. | 1.0.0 |


#### DisplayCreateContactOptions

Options for displaying the native create contact UI.

| Prop          | Type                                                                              | Description                                                | Since |
| ------------- | --------------------------------------------------------------------------------- | ---------------------------------------------------------- | ----- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | Optional pre-filled contact information for the create UI. | 1.0.0 |


#### DisplayUpdateContactByIdOptions

Options for displaying the native update contact UI.

| Prop     | Type                | Description                      | Since |
| -------- | ------------------- | -------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the contact to update. | 1.0.0 |


#### GetAccountsResult

Result from getting accounts.

| Prop           | Type                   | Description                               | Since |
| -------------- | ---------------------- | ----------------------------------------- | ----- |
| **`accounts`** | <code>Account[]</code> | List of accounts available on the device. | 1.0.0 |


#### GetContactByIdResult

Result from getting a contact by ID.

| Prop          | Type                                                | Description                        | Since |
| ------------- | --------------------------------------------------- | ---------------------------------- | ----- |
| **`contact`** | <code><a href="#contact">Contact</a> \| null</code> | The contact, or null if not found. | 1.0.0 |


#### GetContactByIdOptions

Options for getting a contact by ID.

| Prop         | Type                                                  | Description                                                                              | Since |
| ------------ | ----------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`id`**     | <code>string</code>                                   | The ID of the contact to retrieve.                                                       | 1.0.0 |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> | Optional list of specific fields to retrieve. If not specified, all fields are returned. | 1.0.0 |


#### GetContactsResult

Result from getting contacts.

| Prop           | Type                   | Description       | Since |
| -------------- | ---------------------- | ----------------- | ----- |
| **`contacts`** | <code>Contact[]</code> | List of contacts. | 1.0.0 |


#### GetContactsOptions

Options for getting contacts.

| Prop         | Type                                                  | Description                                                                              | Since |
| ------------ | ----------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> | Optional list of specific fields to retrieve. If not specified, all fields are returned. | 1.0.0 |
| **`limit`**  | <code>number</code>                                   | Maximum number of contacts to return.                                                    | 1.0.0 |
| **`offset`** | <code>number</code>                                   | Number of contacts to skip before starting to return results.                            | 1.0.0 |


#### GetGroupByIdResult

Result from getting a group by ID.

| Prop        | Type                                            | Description                      | Since |
| ----------- | ----------------------------------------------- | -------------------------------- | ----- |
| **`group`** | <code><a href="#group">Group</a> \| null</code> | The group, or null if not found. | 1.0.0 |


#### GetGroupByIdOptions

Options for getting a group by ID.

| Prop     | Type                | Description                      | Since |
| -------- | ------------------- | -------------------------------- | ----- |
| **`id`** | <code>string</code> | The ID of the group to retrieve. | 1.0.0 |


#### GetGroupsResult

Result from getting groups.

| Prop         | Type                 | Description     | Since |
| ------------ | -------------------- | --------------- | ----- |
| **`groups`** | <code>Group[]</code> | List of groups. | 1.0.0 |


#### IsAvailableResult

Result from checking if contacts are available on the device.

| Prop              | Type                 | Description                                    | Since |
| ----------------- | -------------------- | ---------------------------------------------- | ----- |
| **`isAvailable`** | <code>boolean</code> | Whether contacts are available on this device. | 1.0.0 |


#### IsSupportedResult

Result from checking if the plugin is supported on the platform.

| Prop              | Type                 | Description                                       | Since |
| ----------------- | -------------------- | ------------------------------------------------- | ----- |
| **`isSupported`** | <code>boolean</code> | Whether the plugin is supported on this platform. | 1.0.0 |


#### PickContactsResult

Result from picking contacts.

| Prop           | Type                   | Description                | Since |
| -------------- | ---------------------- | -------------------------- | ----- |
| **`contacts`** | <code>Contact[]</code> | List of selected contacts. | 1.0.0 |


#### PickContactsOptions

Options for picking contacts using the native contact picker.

| Prop           | Type                                                  | Description                                                                              | Since |
| -------------- | ----------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`fields`**   | <code>(keyof <a href="#contact">Contact</a>)[]</code> | Optional list of specific fields to retrieve. If not specified, all fields are returned. | 1.0.0 |
| **`multiple`** | <code>boolean</code>                                  | Whether to allow selecting multiple contacts. Default is false.                          | 1.0.0 |


#### UpdateContactByIdOptions

Options for updating a contact by ID.

| Prop          | Type                                                                              | Description                      | Since |
| ------------- | --------------------------------------------------------------------------------- | -------------------------------- | ----- |
| **`id`**      | <code>string</code>                                                               | The ID of the contact to update. | 1.0.0 |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> | Updated contact information.     | 1.0.0 |


#### PermissionStatus

Status of contacts permissions.

| Prop                | Type                                                                        | Description                            | Since |
| ------------------- | --------------------------------------------------------------------------- | -------------------------------------- | ----- |
| **`readContacts`**  | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> | Permission state for reading contacts. | 1.0.0 |
| **`writeContacts`** | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> | Permission state for writing contacts. | 1.0.0 |


#### RequestPermissionsOptions

Options for requesting contacts permissions.

| Prop              | Type                                  | Description                                                                          | Since |
| ----------------- | ------------------------------------- | ------------------------------------------------------------------------------------ | ----- |
| **`permissions`** | <code>ContactsPermissionType[]</code> | Specific permissions to request. If not provided, all permissions will be requested. | 1.0.0 |


### Type Aliases


#### Omit

Construct a type with the properties of T except for those in type K.

<code><a href="#pick">Pick</a>&lt;T, <a href="#exclude">Exclude</a>&lt;keyof T, K&gt;&gt;</code>


#### Pick

From T, pick a set of properties whose keys are in the union K

<code>{ [P in K]: T[P]; }</code>


#### Exclude

<a href="#exclude">Exclude</a> from T those types that are assignable to U

<code>T extends U ? never : T</code>


#### EmailAddressType

Type of email address.

<code>'CUSTOM' | 'HOME' | 'ICLOUD' | 'OTHER' | 'WORK'</code>


#### PhoneNumberType

Type of phone number.

<code>'ASSISTANT' | 'CALLBACK' | 'CAR' | 'COMPANY_MAIN' | 'CUSTOM' | 'FAX_HOME' | 'FAX_WORK' | 'HOME' | 'HOME_FAX' | 'ISDN' | 'MAIN' | 'MMS' | 'MOBILE' | 'OTHER' | 'OTHER_FAX' | 'PAGER' | 'RADIO' | 'TELEX' | 'TTY_TDD' | 'WORK' | 'WORK_MOBILE' | 'WORK_PAGER'</code>


#### PostalAddressType

Type of postal address.

<code>'CUSTOM' | 'HOME' | 'OTHER' | 'WORK'</code>


#### UrlAddressType

Type of URL address.

<code>'BLOG' | 'CUSTOM' | 'FTP' | 'HOME' | 'HOMEPAGE' | 'OTHER' | 'PROFILE' | 'SCHOOL' | 'WORK'</code>


#### ContactField

Field names available in a <a href="#contact">Contact</a> object.

<code>keyof <a href="#contact">Contact</a></code>


#### PickContactOptions

Alias for <a href="#pickcontactsoptions">PickContactsOptions</a>.

<code><a href="#pickcontactsoptions">PickContactsOptions</a></code>


#### PickContactResult

Alias for <a href="#pickcontactsresult">PickContactsResult</a>.

<code><a href="#pickcontactsresult">PickContactsResult</a></code>


#### ContactsPermissionState

Permission state for contacts access, including the 'limited' state for iOS 18+.

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ContactsPermissionType

Type of contacts permission to request.

<code>'readContacts' | 'writeContacts'</code>

</docgen-api>

### Credit

This plugin was inspired from: https://github.com/kesha-antonov/react-native-background-downloader
