package app.capgo.contacts;

import android.content.ContentUris;
import android.content.Intent;
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
}
