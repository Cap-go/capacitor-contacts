import { registerPlugin } from '@capacitor/core';

import type { CapacitorContactsPlugin } from './definitions';

const CapacitorContacts = registerPlugin<CapacitorContactsPlugin>('CapacitorContacts', {
  web: () => import('./web').then((m) => new m.CapacitorContactsWeb()),
});

export * from './definitions';
export { CapacitorContacts };
