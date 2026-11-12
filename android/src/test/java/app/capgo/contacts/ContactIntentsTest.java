package app.capgo.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.ContentUris;
import android.content.Intent;
import android.provider.ContactsContract;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ContactIntentsTest {

    @Test
    public void createEditContactIntent_setsDataAndType() {
        long contactId = 42L;
        Intent intent = ContactIntents.createEditContactIntent(contactId);

        assertEquals(Intent.ACTION_EDIT, intent.getAction());
        assertEquals(ContactsContract.Contacts.CONTENT_ITEM_TYPE, intent.getType());
        assertNotNull(intent.getData());
        assertEquals(contactId, ContentUris.parseId(intent.getData()));
    }

    @Test
    public void createPickContactIntent_phoneNumber_usesMimeType() {
        Intent intent = ContactIntents.createPickContactIntent("phoneNumber");

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE, intent.getType());
        assertNull(intent.getData());
    }

    @Test
    public void createPickContactIntent_emailAddress_usesMimeType() {
        Intent intent = ContactIntents.createPickContactIntent("emailAddress");

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE, intent.getType());
        assertNull(intent.getData());
    }

    @Test
    public void createPickContactIntent_postalAddress_usesMimeType() {
        Intent intent = ContactIntents.createPickContactIntent("postalAddress");

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_TYPE, intent.getType());
        assertNull(intent.getData());
    }

    @Test
    public void createPickContactIntent_withoutProperty_usesContactsDirType() {
        Intent intent = ContactIntents.createPickContactIntent(null);

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.Contacts.CONTENT_TYPE, intent.getType());
        assertNull(intent.getData());
    }

    @Test
    public void createPickContactIntentUriFallback_phoneNumber_usesPhoneContentUri() {
        Intent intent = ContactIntents.createPickContactIntentUriFallback("phoneNumber");

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, intent.getData());
    }

    @Test
    public void createPickContactIntentUriFallback_withoutProperty_usesContactsContentUri() {
        Intent intent = ContactIntents.createPickContactIntentUriFallback(null);

        assertEquals(Intent.ACTION_PICK, intent.getAction());
        assertEquals(ContactsContract.Contacts.CONTENT_URI, intent.getData());
    }
}
