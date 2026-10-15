package app.capgo.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
}
