package app.capgo.contacts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Base64;
import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@CapacitorPlugin(
    name = "CapacitorContacts",
    permissions = {
        @Permission(alias = "readContacts", strings = { Manifest.permission.READ_CONTACTS }),
        @Permission(alias = "writeContacts", strings = { Manifest.permission.WRITE_CONTACTS })
    }
)
public class CapacitorContactsPlugin extends Plugin {

    private final String pluginVersion = "7.2.3";

    // MARK: - Implemented API surface

    @PluginMethod
    public void countContacts(PluginCall call) {
        if (!hasReadPermission()) {
            call.reject("READ_CONTACTS permission not granted.");
            return;
        }

        ContentResolver resolver = getContext().getContentResolver();
        try (
            Cursor cursor = resolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                new String[] { ContactsContract.Contacts._ID },
                null,
                null,
                null
            )
        ) {
            int count = cursor != null ? cursor.getCount() : 0;
            JSObject result = new JSObject();
            result.put("count", count);
            call.resolve(result);
        } catch (Exception ex) {
            call.reject("Failed to count contacts.", null, ex);
        }
    }

    @PluginMethod
    public void getContacts(PluginCall call) {
        if (!hasReadPermission()) {
            call.reject("READ_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        Integer limit = options.has("limit") ? options.getInteger("limit") : null;
        Integer offset = options.has("offset") ? options.getInteger("offset") : null;

        try {
            List<ContactBuilder> builders = fetchContacts(limit, offset);
            JSArray contacts = new JSArray();
            for (ContactBuilder builder : builders) {
                contacts.put(builder.toJSObject());
            }
            JSObject result = new JSObject();
            result.put("contacts", contacts);
            call.resolve(result);
        } catch (Exception ex) {
            call.reject("Failed to fetch contacts.", null, ex);
        }
    }

    @PluginMethod
    public void getContactById(PluginCall call) {
        if (!hasReadPermission()) {
            call.reject("READ_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        String identifier = options.getString("id");
        if (identifier == null) {
            call.reject("Missing contact identifier.");
            return;
        }

        try {
            ContactBuilder builder = fetchContact(identifier);
            if (builder == null) {
                call.resolve(new JSObject().put("contact", null));
                return;
            }
            JSObject result = new JSObject();
            result.put("contact", builder.toJSObject());
            call.resolve(result);
        } catch (Exception ex) {
            call.reject("Failed to fetch contact.", null, ex);
        }
    }

    @PluginMethod
    public void getAccounts(PluginCall call) {
        ContentResolver resolver = getContext().getContentResolver();
        JSArray accounts = new JSArray();
        Set<String> seen = new HashSet<>();

        try (
            Cursor cursor = resolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[] { ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE },
                null,
                null,
                null
            )
        ) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.RawContacts.ACCOUNT_NAME));
                    String type = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.RawContacts.ACCOUNT_TYPE));
                    String key = (name == null ? "" : name) + "|" + (type == null ? "" : type);
                    if (seen.add(key)) {
                        JSObject account = new JSObject();
                        account.put("name", name);
                        account.put("type", type);
                        accounts.put(account);
                    }
                }
            }
            call.resolve(new JSObject().put("accounts", accounts));
        } catch (Exception ex) {
            call.reject("Failed to fetch accounts.", null, ex);
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        call.resolve(new JSObject().put("isSupported", true));
    }

    @PluginMethod
    public void isAvailable(PluginCall call) {
        call.resolve(new JSObject().put("isAvailable", true));
    }

    @PluginMethod
    public void openSettings(PluginCall call) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
        intent.setData(uri);
        getContext().startActivity(intent);
        call.resolve();
    }

    @PluginMethod
    public void checkPermissions(PluginCall call) {
        JSObject status = buildPermissionStatus();
        call.resolve(status);
    }

    @PluginMethod
    public void requestPermissions(PluginCall call) {
        // In Capacitor 7, the permission system works differently
        // We just need to request the permissions defined in the plugin annotation
        requestPermissionForAlias("readContacts", call, "handleRequestPermissions");
    }

    @PermissionCallback
    public void handleRequestPermissions(PluginCall call) {
        JSObject status = buildPermissionStatus();
        call.resolve(status);
    }

    @PluginMethod
    public void getPluginVersion(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            ret.put("version", pluginVersion);
            call.resolve(ret);
        } catch (Exception e) {
            call.reject("Could not get plugin version", e);
        }
    }

    // MARK: - Write operations

    @PluginMethod
    public void createContact(PluginCall call) {
        if (!hasWritePermission()) {
            call.reject("WRITE_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        JSObject contactData = options.getJSObject("contact");
        if (contactData == null) {
            call.reject("Missing contact data.");
            return;
        }

        try {
            String contactId = insertContact(contactData);
            call.resolve(new JSObject().put("id", contactId));
        } catch (Exception ex) {
            call.reject("Failed to create contact.", null, ex);
        }
    }

    @PluginMethod
    public void updateContactById(PluginCall call) {
        if (!hasWritePermission()) {
            call.reject("WRITE_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        String contactId = options.getString("id");
        JSObject contactData = options.getJSObject("contact");

        if (contactId == null || contactData == null) {
            call.reject("Missing contact identifier or data.");
            return;
        }

        try {
            updateContact(contactId, contactData);
            call.resolve();
        } catch (Exception ex) {
            call.reject("Failed to update contact.", null, ex);
        }
    }

    @PluginMethod
    public void deleteContactById(PluginCall call) {
        if (!hasWritePermission()) {
            call.reject("WRITE_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        String contactId = options.getString("id");

        if (contactId == null) {
            call.reject("Missing contact identifier.");
            return;
        }

        try {
            ContentResolver resolver = getContext().getContentResolver();
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
            int rowsDeleted = resolver.delete(contactUri, null, null);
            if (rowsDeleted > 0) {
                call.resolve();
            } else {
                call.reject("Contact not found or could not be deleted.");
            }
        } catch (Exception ex) {
            call.reject("Failed to delete contact.", null, ex);
        }
    }

    // MARK: - Group operations

    @PluginMethod
    public void getGroups(PluginCall call) {
        if (!hasReadPermission()) {
            call.reject("READ_CONTACTS permission not granted.");
            return;
        }

        ContentResolver resolver = getContext().getContentResolver();
        JSArray groups = new JSArray();

        try (
            Cursor cursor = resolver.query(
                ContactsContract.Groups.CONTENT_URI,
                new String[] { ContactsContract.Groups._ID, ContactsContract.Groups.TITLE },
                null,
                null,
                ContactsContract.Groups.TITLE + " ASC"
            )
        ) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups._ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups.TITLE));
                    JSObject group = new JSObject();
                    group.put("id", id);
                    group.put("name", name);
                    groups.put(group);
                }
            }
            call.resolve(new JSObject().put("groups", groups));
        } catch (Exception ex) {
            call.reject("Failed to fetch groups.", null, ex);
        }
    }

    @PluginMethod
    public void getGroupById(PluginCall call) {
        if (!hasReadPermission()) {
            call.reject("READ_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        String groupId = options.getString("id");

        if (groupId == null) {
            call.reject("Missing group identifier.");
            return;
        }

        ContentResolver resolver = getContext().getContentResolver();

        try (
            Cursor cursor = resolver.query(
                ContactsContract.Groups.CONTENT_URI,
                new String[] { ContactsContract.Groups._ID, ContactsContract.Groups.TITLE },
                ContactsContract.Groups._ID + " = ?",
                new String[] { groupId },
                null
            )
        ) {
            if (cursor != null && cursor.moveToFirst()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups._ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Groups.TITLE));
                JSObject group = new JSObject();
                group.put("id", id);
                group.put("name", name);
                call.resolve(new JSObject().put("group", group));
            } else {
                call.resolve(new JSObject().put("group", null));
            }
        } catch (Exception ex) {
            call.reject("Failed to fetch group.", null, ex);
        }
    }

    @PluginMethod
    public void createGroup(PluginCall call) {
        if (!hasWritePermission()) {
            call.reject("WRITE_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        JSObject groupData = options.getJSObject("group");

        if (groupData == null) {
            call.reject("Missing group data.");
            return;
        }

        String name = groupData.getString("name");
        if (name == null) {
            call.reject("Missing group name.");
            return;
        }

        try {
            ContentResolver resolver = getContext().getContentResolver();
            ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<>();

            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Groups.CONTENT_URI)
                    .withValue(ContactsContract.Groups.TITLE, name)
                    .build()
            );

            android.content.ContentProviderResult[] results = resolver.applyBatch(ContactsContract.AUTHORITY, ops);
            if (results.length > 0 && results[0].uri != null) {
                String groupId = results[0].uri.getLastPathSegment();
                call.resolve(new JSObject().put("id", groupId));
            } else {
                call.reject("Failed to create group.");
            }
        } catch (Exception ex) {
            call.reject("Failed to create group.", null, ex);
        }
    }

    @PluginMethod
    public void deleteGroupById(PluginCall call) {
        if (!hasWritePermission()) {
            call.reject("WRITE_CONTACTS permission not granted.");
            return;
        }

        JSObject options = call.getObject("options", new JSObject());
        String groupId = options.getString("id");

        if (groupId == null) {
            call.reject("Missing group identifier.");
            return;
        }

        try {
            ContentResolver resolver = getContext().getContentResolver();
            int rowsDeleted = resolver.delete(
                ContactsContract.Groups.CONTENT_URI,
                ContactsContract.Groups._ID + " = ?",
                new String[] { groupId }
            );

            if (rowsDeleted > 0) {
                call.resolve();
            } else {
                call.reject("Group not found or could not be deleted.");
            }
        } catch (Exception ex) {
            call.reject("Failed to delete group.", null, ex);
        }
    }

    // MARK: - UI picker and display operations

    private static final int PICK_CONTACT_REQUEST = 7001;
    private static final int VIEW_CONTACT_REQUEST = 7002;
    private static final int CREATE_CONTACT_REQUEST = 7003;
    private static final int EDIT_CONTACT_REQUEST = 7004;

    private PluginCall currentPickerCall;

    @PluginMethod
    public void pickContact(PluginCall call) {
        currentPickerCall = call;
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(call, intent, PICK_CONTACT_REQUEST);
    }

    @PluginMethod
    public void pickContacts(PluginCall call) {
        // Android doesn't have a native multi-select contact picker
        // Fall back to single selection
        pickContact(call);
    }

    @PluginMethod
    public void displayContactById(PluginCall call) {
        JSObject options = call.getObject("options", new JSObject());
        String contactId = options.getString("id");

        if (contactId == null) {
            call.reject("Missing contact identifier.");
            return;
        }

        try {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
            Intent intent = new Intent(Intent.ACTION_VIEW, contactUri);
            getContext().startActivity(intent);
            call.resolve();
        } catch (Exception ex) {
            call.reject("Failed to display contact.", null, ex);
        }
    }

    @PluginMethod
    public void displayCreateContact(PluginCall call) {
        currentPickerCall = call;
        Intent intent = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);

        JSObject options = call.getObject("options", new JSObject());
        if (options != null) {
            JSObject contactData = options.getJSObject("contact");
            if (contactData != null) {
                populateIntent(intent, contactData);
            }
        }

        startActivityForResult(call, intent, CREATE_CONTACT_REQUEST);
    }

    @PluginMethod
    public void displayUpdateContactById(PluginCall call) {
        JSObject options = call.getObject("options", new JSObject());
        String contactId = options.getString("id");

        if (contactId == null) {
            call.reject("Missing contact identifier.");
            return;
        }

        try {
            Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId));
            Intent intent = new Intent(Intent.ACTION_EDIT, contactUri);
            intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
            getContext().startActivity(intent);
            call.resolve();
        } catch (Exception ex) {
            call.reject("Failed to display contact for editing.", null, ex);
        }
    }

    @Override
    protected void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        super.handleOnActivityResult(requestCode, resultCode, data);

        if (currentPickerCall == null) {
            return;
        }

        PluginCall call = currentPickerCall;
        currentPickerCall = null;

        if (resultCode != android.app.Activity.RESULT_OK) {
            if (requestCode == PICK_CONTACT_REQUEST) {
                call.resolve(new JSObject().put("contacts", new JSArray()));
            } else if (requestCode == CREATE_CONTACT_REQUEST) {
                call.resolve(new JSObject());
            }
            return;
        }

        try {
            if (requestCode == PICK_CONTACT_REQUEST) {
                if (data != null && data.getData() != null) {
                    String contactId = getContactIdFromUri(data.getData());
                    if (contactId != null) {
                        ContactBuilder builder = fetchContact(contactId);
                        if (builder != null) {
                            JSArray contacts = new JSArray();
                            contacts.put(builder.toJSObject());
                            call.resolve(new JSObject().put("contacts", contacts));
                        } else {
                            call.resolve(new JSObject().put("contacts", new JSArray()));
                        }
                    } else {
                        call.resolve(new JSObject().put("contacts", new JSArray()));
                    }
                } else {
                    call.resolve(new JSObject().put("contacts", new JSArray()));
                }
            } else if (requestCode == CREATE_CONTACT_REQUEST) {
                if (data != null && data.getData() != null) {
                    String contactId = getContactIdFromUri(data.getData());
                    if (contactId != null) {
                        call.resolve(new JSObject().put("id", contactId));
                    } else {
                        call.resolve(new JSObject());
                    }
                } else {
                    call.resolve(new JSObject());
                }
            }
        } catch (Exception ex) {
            call.reject("Failed to process contact picker result.", null, ex);
        }
    }

    private String getContactIdFromUri(Uri contactUri) {
        ContentResolver resolver = getContext().getContentResolver();
        try (Cursor cursor = resolver.query(contactUri, new String[] { ContactsContract.Contacts._ID }, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
            }
        } catch (Exception ex) {
            // Ignore
        }
        return null;
    }

    // MARK: - Contact write helpers

    private String insertContact(JSObject contactData) throws Exception {
        ContentResolver resolver = getContext().getContentResolver();
        ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<>();

        int rawContactInsertIndex = ops.size();
        ops.add(
            android.content.ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, (String) null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, (String) null)
                .build()
        );

        // Add structured name
        if (
            contactData.has("givenName") ||
            contactData.has("familyName") ||
            contactData.has("middleName") ||
            contactData.has("namePrefix") ||
            contactData.has("nameSuffix")
        ) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactData.getString("givenName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contactData.getString("familyName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contactData.getString("middleName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.PREFIX, contactData.getString("namePrefix"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.SUFFIX, contactData.getString("nameSuffix"))
                    .build()
            );
        }

        // Add organization
        if (contactData.has("organizationName") || contactData.has("jobTitle")) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, contactData.getString("organizationName"))
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, contactData.getString("jobTitle"))
                    .build()
            );
        }

        // Add note
        if (contactData.has("note")) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contactData.getString("note"))
                    .build()
            );
        }

        // Add email addresses
        if (contactData.has("emailAddresses")) {
            try {
                org.json.JSONArray emails = contactData.getJSONArray("emailAddresses");
                for (int i = 0; i < emails.length(); i++) {
                    org.json.JSONObject email = emails.getJSONObject(i);
                    String value = email.optString("value");
                    String type = email.optString("type", "OTHER");
                    String label = email.optString("label");

                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, value)
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, reverseMapEmailType(type))
                            .withValue(ContactsContract.CommonDataKinds.Email.LABEL, label)
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        // Add phone numbers
        if (contactData.has("phoneNumbers")) {
            try {
                org.json.JSONArray phones = contactData.getJSONArray("phoneNumbers");
                for (int i = 0; i < phones.length(); i++) {
                    org.json.JSONObject phone = phones.getJSONObject(i);
                    String value = phone.optString("value");
                    String type = phone.optString("type", "OTHER");
                    String label = phone.optString("label");

                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, value)
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, reverseMapPhoneType(type))
                            .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, label)
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        // Add postal addresses
        if (contactData.has("postalAddresses")) {
            try {
                org.json.JSONArray addresses = contactData.getJSONArray("postalAddresses");
                for (int i = 0; i < addresses.length(); i++) {
                    org.json.JSONObject address = addresses.getJSONObject(i);

                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.STREET, address.optString("street"))
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.CITY, address.optString("city"))
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.REGION, address.optString("state"))
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, address.optString("postalCode"))
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, address.optString("country"))
                            .withValue(
                                ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                                reverseMapPostalType(address.optString("type", "OTHER"))
                            )
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.LABEL, address.optString("label"))
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        // Add URL addresses
        if (contactData.has("urlAddresses")) {
            try {
                org.json.JSONArray urls = contactData.getJSONArray("urlAddresses");
                for (int i = 0; i < urls.length(); i++) {
                    org.json.JSONObject url = urls.getJSONObject(i);

                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Website.URL, url.optString("value"))
                            .withValue(ContactsContract.CommonDataKinds.Website.TYPE, reverseMapUrlType(url.optString("type", "OTHER")))
                            .withValue(ContactsContract.CommonDataKinds.Website.LABEL, url.optString("label"))
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        // Execute batch
        android.content.ContentProviderResult[] results = resolver.applyBatch(ContactsContract.AUTHORITY, ops);

        // Get the contact ID from the first result
        if (results.length > 0 && results[0].uri != null) {
            String rawContactId = results[0].uri.getLastPathSegment();
            // Query for the actual contact ID
            try (
                Cursor cursor = resolver.query(
                    ContactsContract.RawContacts.CONTENT_URI,
                    new String[] { ContactsContract.RawContacts.CONTACT_ID },
                    ContactsContract.RawContacts._ID + " = ?",
                    new String[] { rawContactId },
                    null
                )
            ) {
                if (cursor != null && cursor.moveToFirst()) {
                    return cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.RawContacts.CONTACT_ID));
                }
            }
        }

        throw new Exception("Failed to create contact");
    }

    @SuppressLint("Range")
    private void updateContact(String contactId, JSObject contactData) throws Exception {
        ContentResolver resolver = getContext().getContentResolver();
        ArrayList<android.content.ContentProviderOperation> ops = new ArrayList<>();

        // Find the raw contact ID for this contact
        String rawContactId = null;
        try (
            Cursor cursor = resolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[] { ContactsContract.RawContacts._ID },
                ContactsContract.RawContacts.CONTACT_ID + " = ?",
                new String[] { contactId },
                null
            )
        ) {
            if (cursor != null && cursor.moveToFirst()) {
                rawContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.RawContacts._ID));
            }
        }

        if (rawContactId == null) {
            throw new Exception("Contact not found");
        }

        // Delete existing data
        ops.add(
            android.content.ContentProviderOperation.newDelete(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data.RAW_CONTACT_ID + " = ?", new String[] { rawContactId })
                .build()
        );

        // Re-insert all data (simpler than updating individual fields)
        int rawContactIdValue = Integer.parseInt(rawContactId);

        // Add structured name
        if (
            contactData.has("givenName") ||
            contactData.has("familyName") ||
            contactData.has("middleName") ||
            contactData.has("namePrefix") ||
            contactData.has("nameSuffix")
        ) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactIdValue)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactData.getString("givenName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contactData.getString("familyName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME, contactData.getString("middleName"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.PREFIX, contactData.getString("namePrefix"))
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.SUFFIX, contactData.getString("nameSuffix"))
                    .build()
            );
        }

        // Similar logic for other fields (organization, note, emails, phones, etc.)
        // Add organization
        if (contactData.has("organizationName") || contactData.has("jobTitle")) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactIdValue)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Organization.COMPANY, contactData.getString("organizationName"))
                    .withValue(ContactsContract.CommonDataKinds.Organization.TITLE, contactData.getString("jobTitle"))
                    .build()
            );
        }

        // Add note
        if (contactData.has("note")) {
            ops.add(
                android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactIdValue)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Note.NOTE, contactData.getString("note"))
                    .build()
            );
        }

        // Add emails, phones, addresses, URLs (same logic as insert)
        if (contactData.has("emailAddresses")) {
            try {
                org.json.JSONArray emails = contactData.getJSONArray("emailAddresses");
                for (int i = 0; i < emails.length(); i++) {
                    org.json.JSONObject email = emails.getJSONObject(i);
                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactIdValue)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Email.ADDRESS, email.optString("value"))
                            .withValue(ContactsContract.CommonDataKinds.Email.TYPE, reverseMapEmailType(email.optString("type", "OTHER")))
                            .withValue(ContactsContract.CommonDataKinds.Email.LABEL, email.optString("label"))
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        if (contactData.has("phoneNumbers")) {
            try {
                org.json.JSONArray phones = contactData.getJSONArray("phoneNumbers");
                for (int i = 0; i < phones.length(); i++) {
                    org.json.JSONObject phone = phones.getJSONObject(i);
                    ops.add(
                        android.content.ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                            .withValue(ContactsContract.Data.RAW_CONTACT_ID, rawContactIdValue)
                            .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone.optString("value"))
                            .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, reverseMapPhoneType(phone.optString("type", "OTHER")))
                            .withValue(ContactsContract.CommonDataKinds.Phone.LABEL, phone.optString("label"))
                            .build()
                    );
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        resolver.applyBatch(ContactsContract.AUTHORITY, ops);
    }

    private void populateIntent(Intent intent, JSObject contactData) {
        // Build full name from components
        StringBuilder nameBuilder = new StringBuilder();
        if (contactData.has("givenName")) {
            nameBuilder.append(contactData.getString("givenName"));
        }
        if (contactData.has("familyName")) {
            if (nameBuilder.length() > 0) {
                nameBuilder.append(" ");
            }
            nameBuilder.append(contactData.getString("familyName"));
        }
        if (nameBuilder.length() > 0) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, nameBuilder.toString());
        }

        if (contactData.has("organizationName")) {
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, contactData.getString("organizationName"));
        }
        if (contactData.has("jobTitle")) {
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, contactData.getString("jobTitle"));
        }
        if (contactData.has("note")) {
            intent.putExtra(ContactsContract.Intents.Insert.NOTES, contactData.getString("note"));
        }

        // Add first email if available
        if (contactData.has("emailAddresses")) {
            try {
                org.json.JSONArray emails = contactData.getJSONArray("emailAddresses");
                if (emails.length() > 0) {
                    org.json.JSONObject email = emails.getJSONObject(0);
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.optString("value"));
                }
            } catch (Exception ex) {
                // Ignore
            }
        }

        // Add first phone if available
        if (contactData.has("phoneNumbers")) {
            try {
                org.json.JSONArray phones = contactData.getJSONArray("phoneNumbers");
                if (phones.length() > 0) {
                    org.json.JSONObject phone = phones.getJSONObject(0);
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.optString("value"));
                }
            } catch (Exception ex) {
                // Ignore
            }
        }
    }

    private int reverseMapEmailType(String type) {
        switch (type) {
            case "HOME":
                return ContactsContract.CommonDataKinds.Email.TYPE_HOME;
            case "WORK":
                return ContactsContract.CommonDataKinds.Email.TYPE_WORK;
            case "MOBILE":
                return ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;
            case "CUSTOM":
                return ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM;
            default:
                return ContactsContract.CommonDataKinds.Email.TYPE_OTHER;
        }
    }

    private int reverseMapPhoneType(String type) {
        switch (type) {
            case "HOME":
                return ContactsContract.CommonDataKinds.Phone.TYPE_HOME;
            case "WORK":
                return ContactsContract.CommonDataKinds.Phone.TYPE_WORK;
            case "MOBILE":
                return ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE;
            case "MAIN":
                return ContactsContract.CommonDataKinds.Phone.TYPE_MAIN;
            case "HOME_FAX":
                return ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME;
            case "WORK_FAX":
                return ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK;
            case "OTHER_FAX":
                return ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX;
            case "PAGER":
                return ContactsContract.CommonDataKinds.Phone.TYPE_PAGER;
            case "CAR":
                return ContactsContract.CommonDataKinds.Phone.TYPE_CAR;
            case "CALLBACK":
                return ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK;
            case "COMPANY_MAIN":
                return ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN;
            case "ASSISTANT":
                return ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT;
            case "CUSTOM":
                return ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM;
            default:
                return ContactsContract.CommonDataKinds.Phone.TYPE_OTHER;
        }
    }

    private int reverseMapPostalType(String type) {
        switch (type) {
            case "HOME":
                return ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME;
            case "WORK":
                return ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK;
            case "CUSTOM":
                return ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM;
            default:
                return ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER;
        }
    }

    private int reverseMapUrlType(String type) {
        switch (type) {
            case "HOME":
                return ContactsContract.CommonDataKinds.Website.TYPE_HOME;
            case "WORK":
                return ContactsContract.CommonDataKinds.Website.TYPE_WORK;
            case "BLOG":
                return ContactsContract.CommonDataKinds.Website.TYPE_BLOG;
            case "PROFILE":
                return ContactsContract.CommonDataKinds.Website.TYPE_PROFILE;
            case "FTP":
                return ContactsContract.CommonDataKinds.Website.TYPE_FTP;
            case "CUSTOM":
                return ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM;
            default:
                return ContactsContract.CommonDataKinds.Website.TYPE_OTHER;
        }
    }

    // MARK: - Permissions helpers

    private boolean hasReadPermission() {
        return getPermissionState("readContacts") == PermissionState.GRANTED;
    }

    private boolean hasWritePermission() {
        return getPermissionState("writeContacts") == PermissionState.GRANTED;
    }

    private JSObject buildPermissionStatus() {
        JSObject status = new JSObject();
        status.put("readContacts", mapPermissionState(getPermissionState("readContacts")));
        status.put("writeContacts", mapPermissionState(getPermissionState("writeContacts")));
        return status;
    }

    private String mapPermissionState(PermissionState state) {
        if (state == null) {
            return "prompt";
        }
        switch (state) {
            case GRANTED:
                return "granted";
            case DENIED:
                return "denied";
            case PROMPT_WITH_RATIONALE:
                return "prompt-with-rationale";
            default:
                return "prompt";
        }
    }

    // MARK: - Contact access helpers

    private List<ContactBuilder> fetchContacts(Integer limit, Integer offset) {
        List<ContactBuilder> builders = new ArrayList<>();
        ContentResolver resolver = getContext().getContentResolver();

        Uri queryUri = ContactsContract.Contacts.CONTENT_URI;
        if (limit != null) {
            android.net.Uri.Builder builder = ContactsContract.Contacts.CONTENT_URI.buildUpon();
            builder.appendQueryParameter(ContactsContract.LIMIT_PARAM_KEY, String.valueOf(limit));
            if (offset != null) {
                // START_PARAM_KEY was removed, use "offset" directly
                builder.appendQueryParameter("offset", String.valueOf(offset));
            }
            queryUri = builder.build();
        }

        try (
            Cursor cursor = resolver.query(
                queryUri,
                new String[] { ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME_PRIMARY },
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " ASC"
            )
        ) {
            if (cursor == null) {
                return builders;
            }

            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                ContactBuilder contact = fetchContact(id);
                if (contact != null) {
                    contact.fullName = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    builders.add(contact);
                }
            }
        }

        return builders;
    }

    private ContactBuilder fetchContact(@NonNull String contactId) {
        ContentResolver resolver = getContext().getContentResolver();
        ContactBuilder builder = new ContactBuilder(contactId);

        // Structured name, emails, phones, etc.
        try (
            Cursor dataCursor = resolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                ContactsContract.Data.CONTACT_ID + " = ?",
                new String[] { contactId },
                null
            )
        ) {
            if (dataCursor == null) {
                return null;
            }

            while (dataCursor.moveToNext()) {
                String mimeType = dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.Data.MIMETYPE));
                switch (mimeType) {
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        builder.givenName = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME)
                        );
                        builder.familyName = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME)
                        );
                        builder.middleName = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.MIDDLE_NAME)
                        );
                        builder.namePrefix = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.PREFIX)
                        );
                        builder.nameSuffix = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.SUFFIX)
                        );
                        break;
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        builder.addEmail(
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS)),
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.TYPE)),
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.LABEL)),
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.IS_PRIMARY)) == 1
                        );
                        break;
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        builder.addPhone(
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.TYPE)),
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LABEL)),
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.IS_PRIMARY)) == 1
                        );
                        break;
                    case ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE:
                        builder.addPostalAddress(
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)),
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.LABEL)),
                            dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.STREET)
                            ),
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.CITY)),
                            dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.REGION)
                            ),
                            dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)
                            ),
                            dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)
                            ),
                            dataCursor.getString(
                                dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)
                            ),
                            dataCursor.getInt(
                                    dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.IS_PRIMARY)
                                ) ==
                                1
                        );
                        break;
                    case ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE:
                        builder.addUrlAddress(
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Website.URL)),
                            dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Website.TYPE)),
                            dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Website.LABEL))
                        );
                        break;
                    case ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE:
                        builder.organizationName = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.COMPANY)
                        );
                        builder.jobTitle = dataCursor.getString(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Organization.TITLE)
                        );
                        break;
                    case ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE:
                        builder.note = dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Note.NOTE));
                        break;
                    case ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE:
                        int eventType = dataCursor.getInt(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Event.TYPE));
                        if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) {
                            builder.setBirthday(
                                dataCursor.getString(dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Event.START_DATE))
                            );
                        }
                        break;
                    case ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE:
                        long groupId = dataCursor.getLong(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID)
                        );
                        builder.addGroupId(String.valueOf(groupId));
                        break;
                    case ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE:
                        byte[] photoData = dataCursor.getBlob(
                            dataCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Photo.PHOTO)
                        );
                        if (photoData != null) {
                            builder.photoBase64 = Base64.encodeToString(photoData, Base64.NO_WRAP);
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        // Account information
        try (
            Cursor rawCursor = resolver.query(
                ContactsContract.RawContacts.CONTENT_URI,
                new String[] { ContactsContract.RawContacts.ACCOUNT_NAME, ContactsContract.RawContacts.ACCOUNT_TYPE },
                ContactsContract.RawContacts.CONTACT_ID + " = ?",
                new String[] { contactId },
                null
            )
        ) {
            if (rawCursor != null && rawCursor.moveToFirst()) {
                String accountName = rawCursor.getString(rawCursor.getColumnIndexOrThrow(ContactsContract.RawContacts.ACCOUNT_NAME));
                String accountType = rawCursor.getString(rawCursor.getColumnIndexOrThrow(ContactsContract.RawContacts.ACCOUNT_TYPE));
                builder.accountName = accountName;
                builder.accountType = accountType;
            }
        }

        if (builder.fullName == null) {
            builder.fullName = resolveDisplayName(contactId);
        }

        return builder;
    }

    private String resolveDisplayName(String contactId) {
        ContentResolver resolver = getContext().getContentResolver();
        try (
            Cursor cursor = resolver.query(
                ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.parseLong(contactId)),
                new String[] { ContactsContract.Contacts.DISPLAY_NAME_PRIMARY },
                null,
                null,
                null
            )
        ) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
            }
        }
        return null;
    }

    // MARK: - Contact builder helper

    private static class ContactBuilder {

        final String id;
        String givenName;
        String familyName;
        String middleName;
        String namePrefix;
        String nameSuffix;
        String organizationName;
        String jobTitle;
        String note;
        String fullName;
        String photoBase64;
        String accountName;
        String accountType;
        Integer birthdayYear;
        Integer birthdayMonth;
        Integer birthdayDay;
        final JSArray groupIds = new JSArray();
        final JSArray emailAddresses = new JSArray();
        final JSArray phoneNumbers = new JSArray();
        final JSArray postalAddresses = new JSArray();
        final JSArray urlAddresses = new JSArray();

        ContactBuilder(String id) {
            this.id = id;
        }

        void addEmail(String value, int type, String label, boolean isPrimary) {
            if (value == null) {
                return;
            }
            JSObject email = new JSObject();
            email.put("value", value);
            email.put("type", mapEmailType(type));
            if (type == ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM && label != null) {
                email.put("label", label);
            }
            email.put("isPrimary", isPrimary);
            emailAddresses.put(email);
        }

        void addPhone(String value, int type, String label, boolean isPrimary) {
            if (value == null) {
                return;
            }
            JSObject phone = new JSObject();
            phone.put("value", value);
            phone.put("type", mapPhoneType(type));
            if (type == ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM && label != null) {
                phone.put("label", label);
            }
            phone.put("isPrimary", isPrimary);
            phoneNumbers.put(phone);
        }

        void addPostalAddress(
            int type,
            String label,
            String street,
            String city,
            String state,
            String postalCode,
            String country,
            String neighborhood,
            boolean isPrimary
        ) {
            JSObject address = new JSObject();
            address.put("street", street);
            address.put("city", city);
            address.put("state", state);
            address.put("postalCode", postalCode);
            address.put("country", country);
            address.put("neighborhood", neighborhood);
            address.put("formatted", buildFormattedAddress(street, city, state, postalCode, country));
            address.put("isoCountryCode", null);
            address.put("isPrimary", isPrimary);
            address.put("type", mapPostalType(type));
            if (type == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM && label != null) {
                address.put("label", label);
            }
            postalAddresses.put(address);
        }

        void addUrlAddress(String value, int type, String label) {
            if (value == null) {
                return;
            }
            JSObject url = new JSObject();
            url.put("value", value);
            url.put("type", mapUrlType(type));
            if (type == ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM && label != null) {
                url.put("label", label);
            }
            urlAddresses.put(url);
        }

        void addGroupId(String groupId) {
            if (groupId == null) {
                return;
            }
            groupIds.put(groupId);
        }

        void setBirthday(String startDate) {
            if (startDate == null || startDate.isEmpty()) {
                return;
            }
            String[] parts = startDate.split("-");
            if (parts.length >= 2) {
                birthdayMonth = safeParse(parts[0]);
                birthdayDay = safeParse(parts[1]);
            }
            if (parts.length >= 3) {
                birthdayYear = safeParse(parts[2]);
            }
        }

        JSObject toJSObject() {
            JSObject contact = new JSObject();
            contact.put("id", id);
            contact.put("givenName", givenName);
            contact.put("familyName", familyName);
            contact.put("middleName", middleName);
            contact.put("namePrefix", namePrefix);
            contact.put("nameSuffix", nameSuffix);
            contact.put("organizationName", organizationName);
            contact.put("jobTitle", jobTitle);
            contact.put("note", note);
            contact.put("fullName", fullName);
            contact.put("photo", photoBase64);
            contact.put("groupIds", groupIds);
            contact.put("emailAddresses", emailAddresses);
            contact.put("phoneNumbers", phoneNumbers);
            contact.put("postalAddresses", postalAddresses);
            contact.put("urlAddresses", urlAddresses);

            if (birthdayYear != null || birthdayMonth != null || birthdayDay != null) {
                JSObject birthday = new JSObject();
                if (birthdayDay != null) birthday.put("day", birthdayDay);
                if (birthdayMonth != null) birthday.put("month", birthdayMonth);
                if (birthdayYear != null) birthday.put("year", birthdayYear);
                contact.put("birthday", birthday);
            }

            if (accountName != null || accountType != null) {
                JSObject account = new JSObject();
                account.put("name", accountName);
                account.put("type", accountType);
                contact.put("account", account);
            } else {
                contact.put("account", null);
            }

            return contact;
        }

        private static Integer safeParse(String value) {
            try {
                return Integer.parseInt(value);
            } catch (Exception ex) {
                return null;
            }
        }

        private static String buildFormattedAddress(String street, String city, String state, String postalCode, String country) {
            StringBuilder builder = new StringBuilder();
            if (street != null && !street.isEmpty()) builder.append(street).append('\n');
            if (city != null && !city.isEmpty()) builder.append(city);
            if (state != null && !state.isEmpty()) builder.append(builder.length() > 0 ? ", " : "").append(state);
            if (postalCode != null && !postalCode.isEmpty()) builder.append(' ').append(postalCode);
            if (country != null && !country.isEmpty()) builder.append(builder.length() > 0 ? "\n" : "").append(country);
            return builder.toString();
        }

        private static String mapEmailType(int type) {
            switch (type) {
                case ContactsContract.CommonDataKinds.Email.TYPE_HOME:
                    return "HOME";
                case ContactsContract.CommonDataKinds.Email.TYPE_WORK:
                    return "WORK";
                case ContactsContract.CommonDataKinds.Email.TYPE_OTHER:
                    return "OTHER";
                case ContactsContract.CommonDataKinds.Email.TYPE_MOBILE:
                    return "MOBILE";
                case ContactsContract.CommonDataKinds.Email.TYPE_CUSTOM:
                    return "CUSTOM";
                // TYPE_MAIN constant was removed from Android SDK
                default:
                    return "OTHER";
            }
        }

        private static String mapPhoneType(int type) {
            switch (type) {
                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                    return "HOME";
                case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                    return "WORK";
                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                    return "MOBILE";
                case ContactsContract.CommonDataKinds.Phone.TYPE_MAIN:
                    return "MAIN";
                case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_HOME:
                    return "HOME_FAX";
                case ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK:
                    return "WORK_FAX";
                case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER_FAX:
                    return "OTHER_FAX";
                case ContactsContract.CommonDataKinds.Phone.TYPE_PAGER:
                    return "PAGER";
                case ContactsContract.CommonDataKinds.Phone.TYPE_CAR:
                    return "CAR";
                case ContactsContract.CommonDataKinds.Phone.TYPE_CALLBACK:
                    return "CALLBACK";
                case ContactsContract.CommonDataKinds.Phone.TYPE_COMPANY_MAIN:
                    return "COMPANY_MAIN";
                case ContactsContract.CommonDataKinds.Phone.TYPE_ASSISTANT:
                    return "ASSISTANT";
                case ContactsContract.CommonDataKinds.Phone.TYPE_OTHER:
                    return "OTHER";
                case ContactsContract.CommonDataKinds.Phone.TYPE_CUSTOM:
                    return "CUSTOM";
                default:
                    return "OTHER";
            }
        }

        private static String mapPostalType(int type) {
            switch (type) {
                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME:
                    return "HOME";
                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK:
                    return "WORK";
                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER:
                    return "OTHER";
                case ContactsContract.CommonDataKinds.StructuredPostal.TYPE_CUSTOM:
                    return "CUSTOM";
                default:
                    return "OTHER";
            }
        }

        private static String mapUrlType(int type) {
            switch (type) {
                case ContactsContract.CommonDataKinds.Website.TYPE_HOME:
                    return "HOME";
                case ContactsContract.CommonDataKinds.Website.TYPE_WORK:
                    return "WORK";
                case ContactsContract.CommonDataKinds.Website.TYPE_BLOG:
                    return "BLOG";
                case ContactsContract.CommonDataKinds.Website.TYPE_PROFILE:
                    return "PROFILE";
                case ContactsContract.CommonDataKinds.Website.TYPE_FTP:
                    return "FTP";
                // TYPE_HOME_PAGE and TYPE_SCHOOL constants were removed from Android SDK
                case ContactsContract.CommonDataKinds.Website.TYPE_OTHER:
                    return "OTHER";
                case ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM:
                    return "CUSTOM";
                default:
                    return "OTHER";
            }
        }
    }
}
