package app.capgo.contacts;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.ContactsContract;

/** Builds {@link Intent} instances for native contact UI flows. */
final class ContactIntents {

    private ContactIntents() {}

    static Intent createEditContactIntent(long contactId) {
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId);
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setDataAndType(contactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE);
        return intent;
    }

    static boolean isValidPickProperty(String property) {
        return contentTypeForProperty(property) != null;
    }

    /**
     * Resolves a contact-picker intent, preferring MIME {@link Intent#setType} (required on Xiaomi HyperOS)
     * and falling back to a collection {@link Uri} when no activity handles the typed intent.
     */
    static Intent resolvePickContactIntent(Context context, String property) {
        Intent primary = createPickContactIntent(property);
        if (canResolve(context, primary)) {
            return primary;
        }
        Intent fallback = createPickContactIntentUriFallback(property);
        if (canResolve(context, fallback)) {
            return fallback;
        }
        return null;
    }

    static Intent createPickContactIntent(String property) {
        String type = contentTypeForProperty(property);
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (type != null) {
            intent.setType(type);
        } else {
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        }
        return intent;
    }

    static Intent createPickContactIntentUriFallback(String property) {
        Uri uri = contentUriForProperty(property);
        if (uri != null) {
            return new Intent(Intent.ACTION_PICK, uri);
        }
        return new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
    }

    private static boolean canResolve(Context context, Intent intent) {
        return context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null;
    }

    private static String contentTypeForProperty(String property) {
        if (property == null) {
            return null;
        }
        if ("phoneNumber".equals(property)) {
            return ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE;
        }
        if ("emailAddress".equals(property)) {
            return ContactsContract.CommonDataKinds.Email.CONTENT_TYPE;
        }
        if ("postalAddress".equals(property)) {
            return ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_TYPE;
        }
        return null;
    }

    private static Uri contentUriForProperty(String property) {
        if ("phoneNumber".equals(property)) {
            return ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        }
        if ("emailAddress".equals(property)) {
            return ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        }
        if ("postalAddress".equals(property)) {
            return ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        }
        return null;
    }
}
