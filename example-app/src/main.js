import './style.css';
import { CapacitorContacts } from '@capgo/capacitor-contacts';

const permissionLabel = document.getElementById('permission');
const supportedLabel = document.getElementById('supported');
const outcomeLabel = document.getElementById('outcome');

const checkPermissionButton = document.getElementById('check-permission');
const requestPermissionButton = document.getElementById('request-permission');
const pickContactButton = document.getElementById('pick-contact');
const listContactsButton = document.getElementById('list-contacts');

function setOutcome(message) {
  outcomeLabel.textContent = message;
}

async function refreshState() {
  try {
    const supported = await CapacitorContacts.isSupported();
    supportedLabel.textContent = supported.isSupported ? 'Available' : 'Unavailable';
  } catch (error) {
    supportedLabel.textContent = error?.message ?? 'Unavailable';
  }

  try {
    const status = await CapacitorContacts.checkPermissions();
    permissionLabel.textContent = `read=${status.readContacts}, write=${status.writeContacts}`;
  } catch (error) {
    permissionLabel.textContent = error?.message ?? 'Unknown';
  }
}

checkPermissionButton.addEventListener('click', async () => {
  await refreshState();
  setOutcome('Permissions refreshed.');
});

requestPermissionButton.addEventListener('click', async () => {
  try {
    const status = await CapacitorContacts.requestPermissions();
    permissionLabel.textContent = `read=${status.readContacts}, write=${status.writeContacts}`;
    setOutcome('Requested contacts permissions.');
  } catch (error) {
    setOutcome(error?.message ?? String(error));
  }
});

pickContactButton.addEventListener('click', async () => {
  try {
    const result = await CapacitorContacts.pickContact();
    setOutcome(JSON.stringify(result, null, 2));
  } catch (error) {
    setOutcome(error?.message ?? String(error));
  }
});

listContactsButton.addEventListener('click', async () => {
  try {
    const result = await CapacitorContacts.getContacts({ limit: 5 });
    setOutcome(JSON.stringify(result, null, 2));
  } catch (error) {
    setOutcome(error?.message ?? String(error));
  }
});

refreshState().catch((error) => setOutcome(error?.message ?? String(error)));
