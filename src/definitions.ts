import type { PermissionState } from '@capacitor/core';

export type ContactsPermissionState = PermissionState | 'limited';
export type ContactsPermissionType = 'readContacts' | 'writeContacts';

export interface PermissionStatus {
  readContacts: ContactsPermissionState;
  writeContacts: ContactsPermissionState;
}

export interface RequestPermissionsOptions {
  permissions?: ContactsPermissionType[];
}

export interface Account {
  name: string;
  type?: string;
}

export interface Birthday {
  day?: number;
  month?: number;
  year?: number;
}

export type EmailAddressType = 'CUSTOM' | 'HOME' | 'ICLOUD' | 'OTHER' | 'WORK';

export interface EmailAddress {
  value: string;
  type?: EmailAddressType;
  label?: string;
  isPrimary?: boolean;
}

export type PhoneNumberType =
  | 'ASSISTANT'
  | 'CALLBACK'
  | 'CAR'
  | 'COMPANY_MAIN'
  | 'CUSTOM'
  | 'FAX_HOME'
  | 'FAX_WORK'
  | 'HOME'
  | 'HOME_FAX'
  | 'ISDN'
  | 'MAIN'
  | 'MMS'
  | 'MOBILE'
  | 'OTHER'
  | 'OTHER_FAX'
  | 'PAGER'
  | 'RADIO'
  | 'TELEX'
  | 'TTY_TDD'
  | 'WORK'
  | 'WORK_MOBILE'
  | 'WORK_PAGER';

export interface PhoneNumber {
  value: string;
  type?: PhoneNumberType;
  label?: string;
  isPrimary?: boolean;
}

export type PostalAddressType = 'CUSTOM' | 'HOME' | 'OTHER' | 'WORK';

export interface PostalAddress {
  city?: string;
  country?: string;
  formatted?: string;
  isoCountryCode?: string;
  isPrimary?: boolean;
  label?: string;
  neighborhood?: string;
  postalCode?: string;
  state?: string;
  street?: string;
  type?: PostalAddressType;
}

export type UrlAddressType = 'BLOG' | 'CUSTOM' | 'FTP' | 'HOME' | 'HOMEPAGE' | 'OTHER' | 'PROFILE' | 'SCHOOL' | 'WORK';

export interface UrlAddress {
  value: string;
  type?: UrlAddressType;
  label?: string;
}

export interface Contact {
  id?: string;
  account?: Account;
  birthday?: Birthday;
  emailAddresses?: EmailAddress[];
  familyName?: string;
  fullName?: string;
  givenName?: string;
  groupIds?: string[];
  jobTitle?: string;
  middleName?: string;
  namePrefix?: string;
  nameSuffix?: string;
  note?: string;
  organizationName?: string;
  phoneNumbers?: PhoneNumber[];
  photo?: string;
  postalAddresses?: PostalAddress[];
  urlAddresses?: UrlAddress[];
}

export type ContactField = keyof Contact;

export interface CountContactsResult {
  count: number;
}

export interface CreateContactOptions {
  contact: Omit<Contact, 'id'>;
}

export interface CreateContactResult {
  id: string;
}

export interface DisplayCreateContactOptions {
  contact?: Omit<Contact, 'id'>;
}

export interface DisplayCreateContactResult {
  id?: string;
}

export interface DisplayContactByIdOptions {
  id: string;
}

export interface DisplayUpdateContactByIdOptions {
  id: string;
}

export interface GetAccountsResult {
  accounts: Account[];
}

export interface GetContactByIdOptions {
  id: string;
  fields?: ContactField[];
}

export interface GetContactByIdResult {
  contact: Contact | null;
}

export interface GetContactsOptions {
  fields?: ContactField[];
  limit?: number;
  offset?: number;
}

export interface GetContactsResult {
  contacts: Contact[];
}

export interface GetGroupByIdOptions {
  id: string;
}

export interface GetGroupByIdResult {
  group: Group | null;
}

export interface GetGroupsResult {
  groups: Group[];
}

export interface Group {
  id: string;
  name: string;
}

export interface CreateGroupOptions {
  group: Omit<Group, 'id'>;
}

export interface CreateGroupResult {
  id: string;
}

export interface DeleteContactByIdOptions {
  id: string;
}

export interface DeleteGroupByIdOptions {
  id: string;
}

export interface PickContactsOptions {
  fields?: ContactField[];
  multiple?: boolean;
}

export interface PickContactsResult {
  contacts: Contact[];
}

export type PickContactOptions = PickContactsOptions;
export type PickContactResult = PickContactsResult;

export interface UpdateContactByIdOptions {
  id: string;
  contact: Omit<Contact, 'id'>;
}

export interface IsSupportedResult {
  isSupported: boolean;
}

export interface IsAvailableResult {
  isAvailable: boolean;
}

export interface CapacitorContactsPlugin {
  countContacts(): Promise<CountContactsResult>;
  createContact(options: CreateContactOptions): Promise<CreateContactResult>;
  createGroup(options: CreateGroupOptions): Promise<CreateGroupResult>;
  deleteContactById(options: DeleteContactByIdOptions): Promise<void>;
  deleteGroupById(options: DeleteGroupByIdOptions): Promise<void>;
  displayContactById(options: DisplayContactByIdOptions): Promise<void>;
  displayCreateContact(options?: DisplayCreateContactOptions): Promise<DisplayCreateContactResult>;
  displayUpdateContactById(options: DisplayUpdateContactByIdOptions): Promise<void>;
  getAccounts(): Promise<GetAccountsResult>;
  getContactById(options: GetContactByIdOptions): Promise<GetContactByIdResult>;
  getContacts(options?: GetContactsOptions): Promise<GetContactsResult>;
  getGroupById(options: GetGroupByIdOptions): Promise<GetGroupByIdResult>;
  getGroups(): Promise<GetGroupsResult>;
  isAvailable(): Promise<IsAvailableResult>;
  isSupported(): Promise<IsSupportedResult>;
  openSettings(): Promise<void>;
  pickContact(options?: PickContactOptions): Promise<PickContactResult>;
  pickContacts(options?: PickContactsOptions): Promise<PickContactsResult>;
  updateContactById(options: UpdateContactByIdOptions): Promise<void>;
  checkPermissions(): Promise<PermissionStatus>;
  requestPermissions(options?: RequestPermissionsOptions): Promise<PermissionStatus>;

  /**
   * Get the native Capacitor plugin version.
   *
   * @returns Promise that resolves with the plugin version
   * @since 1.0.0
   */
  getPluginVersion(): Promise<{ version: string }>;
}
