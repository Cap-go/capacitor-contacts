package app.capgo.contacts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import com.getcapacitor.PluginCall;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class CapacitorContactsPluginDisplayUpdateTest {

    @Test
    public void displayUpdateContactById_launchesEditIntentWithRequestedId() {
        Context context = mock(Context.class);
        ArgumentCaptor<Intent> intentCaptor = ArgumentCaptor.forClass(Intent.class);
        doAnswer(invocation -> null).when(context).startActivity(intentCaptor.capture());

        CapacitorContactsPlugin plugin = new CapacitorContactsPlugin() {
            @Override
            public Context getContext() {
                return context;
            }
        };

        String contactId = "42";
        PluginCall call = mock(PluginCall.class);
        when(call.getString("id")).thenReturn(contactId);

        plugin.displayUpdateContactById(call);

        Intent started = intentCaptor.getValue();
        assertNotNull(started);
        assertEquals(Intent.ACTION_EDIT, started.getAction());
        assertEquals(ContactsContract.Contacts.CONTENT_ITEM_TYPE, started.getType());
        assertEquals(Long.parseLong(contactId), ContentUris.parseId(started.getData()));
        verify(call).resolve();
    }
}
