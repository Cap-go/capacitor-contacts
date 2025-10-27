import type { PermissionState } from '@capacitor/core';

/**
 * Permission state for contacts access, including the 'limited' state for iOS 18+.
 *
 * @since 1.0.0
 */
export type ContactsPermissionState = PermissionState | 'limited';

/**
 * Type of contacts permission to request.
 *
 * @since 1.0.0
 */
export type ContactsPermissionType = 'readContacts' | 'writeContacts';

/**
 * Status of contacts permissions.
 *
 * @since 1.0.0
 */
export interface PermissionStatus {
  /**
   * Permission state for reading contacts.
   *
   * @since 1.0.0
   */
  readContacts: ContactsPermissionState;

  /**
   * Permission state for writing contacts.
   *
   * @since 1.0.0
   */
  writeContacts: ContactsPermissionState;
}

/**
 * Options for requesting contacts permissions.
 *
 * @since 1.0.0
 */
export interface RequestPermissionsOptions {
  /**
   * Specific permissions to request. If not provided, all permissions will be requested.
   *
   * @since 1.0.0
   */
  permissions?: ContactsPermissionType[];
}

/**
 * Account information for a contact.
 *
 * @since 1.0.0
 */
export interface Account {
  /**
   * The name of the account.
   *
   * @since 1.0.0
   */
  name: string;

  /**
   * The type of the account.
   *
   * @since 1.0.0
   */
  type?: string;
}

/**
 * Birthday information for a contact.
 *
 * @since 1.0.0
 */
export interface Birthday {
  /**
   * The day of the month (1-31).
   *
   * @since 1.0.0
   */
  day?: number;

  /**
   * The month (1-12).
   *
   * @since 1.0.0
   */
  month?: number;

  /**
   * The year.
   *
   * @since 1.0.0
   */
  year?: number;
}

/**
 * Type of email address.
 *
 * @since 1.0.0
 */
export type EmailAddressType = 'CUSTOM' | 'HOME' | 'ICLOUD' | 'OTHER' | 'WORK';

/**
 * Email address information for a contact.
 *
 * @since 1.0.0
 */
export interface EmailAddress {
  /**
   * The email address value.
   *
   * @since 1.0.0
   */
  value: string;

  /**
   * The type of email address.
   *
   * @since 1.0.0
   */
  type?: EmailAddressType;

  /**
   * Custom label for the email address.
   *
   * @since 1.0.0
   */
  label?: string;

  /**
   * Whether this is the primary email address.
   *
   * @since 1.0.0
   */
  isPrimary?: boolean;
}

/**
 * Type of phone number.
 *
 * @since 1.0.0
 */
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

/**
 * Phone number information for a contact.
 *
 * @since 1.0.0
 */
export interface PhoneNumber {
  /**
   * The phone number value.
   *
   * @since 1.0.0
   */
  value: string;

  /**
   * The type of phone number.
   *
   * @since 1.0.0
   */
  type?: PhoneNumberType;

  /**
   * Custom label for the phone number.
   *
   * @since 1.0.0
   */
  label?: string;

  /**
   * Whether this is the primary phone number.
   *
   * @since 1.0.0
   */
  isPrimary?: boolean;
}

/**
 * Type of postal address.
 *
 * @since 1.0.0
 */
export type PostalAddressType = 'CUSTOM' | 'HOME' | 'OTHER' | 'WORK';

/**
 * Postal address information for a contact.
 *
 * @since 1.0.0
 */
export interface PostalAddress {
  /**
   * The city name.
   *
   * @since 1.0.0
   */
  city?: string;

  /**
   * The country name.
   *
   * @since 1.0.0
   */
  country?: string;

  /**
   * The formatted address string.
   *
   * @since 1.0.0
   */
  formatted?: string;

  /**
   * The ISO country code.
   *
   * @since 1.0.0
   */
  isoCountryCode?: string;

  /**
   * Whether this is the primary postal address.
   *
   * @since 1.0.0
   */
  isPrimary?: boolean;

  /**
   * Custom label for the postal address.
   *
   * @since 1.0.0
   */
  label?: string;

  /**
   * The neighborhood name.
   *
   * @since 1.0.0
   */
  neighborhood?: string;

  /**
   * The postal code.
   *
   * @since 1.0.0
   */
  postalCode?: string;

  /**
   * The state or province name.
   *
   * @since 1.0.0
   */
  state?: string;

  /**
   * The street address.
   *
   * @since 1.0.0
   */
  street?: string;

  /**
   * The type of postal address.
   *
   * @since 1.0.0
   */
  type?: PostalAddressType;
}

/**
 * Type of URL address.
 *
 * @since 1.0.0
 */
export type UrlAddressType = 'BLOG' | 'CUSTOM' | 'FTP' | 'HOME' | 'HOMEPAGE' | 'OTHER' | 'PROFILE' | 'SCHOOL' | 'WORK';

/**
 * URL address information for a contact.
 *
 * @since 1.0.0
 */
export interface UrlAddress {
  /**
   * The URL value.
   *
   * @since 1.0.0
   */
  value: string;

  /**
   * The type of URL.
   *
   * @since 1.0.0
   */
  type?: UrlAddressType;

  /**
   * Custom label for the URL.
   *
   * @since 1.0.0
   */
  label?: string;
}

/**
 * Contact information.
 *
 * @since 1.0.0
 */
export interface Contact {
  /**
   * Unique identifier for the contact.
   *
   * @since 1.0.0
   */
  id?: string;

  /**
   * Account information for the contact.
   *
   * @since 1.0.0
   */
  account?: Account;

  /**
   * Birthday information for the contact.
   *
   * @since 1.0.0
   */
  birthday?: Birthday;

  /**
   * Email addresses for the contact.
   *
   * @since 1.0.0
   */
  emailAddresses?: EmailAddress[];

  /**
   * Family name (last name) of the contact.
   *
   * @since 1.0.0
   */
  familyName?: string;

  /**
   * Full name of the contact.
   *
   * @since 1.0.0
   */
  fullName?: string;

  /**
   * Given name (first name) of the contact.
   *
   * @since 1.0.0
   */
  givenName?: string;

  /**
   * Group IDs the contact belongs to.
   *
   * @since 1.0.0
   */
  groupIds?: string[];

  /**
   * Job title of the contact.
   *
   * @since 1.0.0
   */
  jobTitle?: string;

  /**
   * Middle name of the contact.
   *
   * @since 1.0.0
   */
  middleName?: string;

  /**
   * Name prefix (e.g., "Dr.", "Mr.", "Ms.") of the contact.
   *
   * @since 1.0.0
   */
  namePrefix?: string;

  /**
   * Name suffix (e.g., "Jr.", "Sr.", "III") of the contact.
   *
   * @since 1.0.0
   */
  nameSuffix?: string;

  /**
   * Notes about the contact.
   *
   * @since 1.0.0
   */
  note?: string;

  /**
   * Organization name of the contact.
   *
   * @since 1.0.0
   */
  organizationName?: string;

  /**
   * Phone numbers for the contact.
   *
   * @since 1.0.0
   */
  phoneNumbers?: PhoneNumber[];

  /**
   * Base64-encoded photo of the contact.
   *
   * @since 1.0.0
   */
  photo?: string;

  /**
   * Postal addresses for the contact.
   *
   * @since 1.0.0
   */
  postalAddresses?: PostalAddress[];

  /**
   * URL addresses for the contact.
   *
   * @since 1.0.0
   */
  urlAddresses?: UrlAddress[];
}

/**
 * Field names available in a Contact object.
 *
 * @since 1.0.0
 */
export type ContactField = keyof Contact;

/**
 * Result from counting contacts.
 *
 * @since 1.0.0
 */
export interface CountContactsResult {
  /**
   * Total number of contacts.
   *
   * @since 1.0.0
   */
  count: number;
}

/**
 * Options for creating a contact.
 *
 * @since 1.0.0
 */
export interface CreateContactOptions {
  /**
   * Contact information to create. The 'id' field will be generated automatically.
   *
   * @since 1.0.0
   */
  contact: Omit<Contact, 'id'>;
}

/**
 * Result from creating a contact.
 *
 * @since 1.0.0
 */
export interface CreateContactResult {
  /**
   * The ID of the newly created contact.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Options for displaying the native create contact UI.
 *
 * @since 1.0.0
 */
export interface DisplayCreateContactOptions {
  /**
   * Optional pre-filled contact information for the create UI.
   *
   * @since 1.0.0
   */
  contact?: Omit<Contact, 'id'>;
}

/**
 * Result from displaying the native create contact UI.
 *
 * @since 1.0.0
 */
export interface DisplayCreateContactResult {
  /**
   * The ID of the created contact, if one was created. Undefined if the user cancelled.
   *
   * @since 1.0.0
   */
  id?: string;
}

/**
 * Options for displaying a contact by ID.
 *
 * @since 1.0.0
 */
export interface DisplayContactByIdOptions {
  /**
   * The ID of the contact to display.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Options for displaying the native update contact UI.
 *
 * @since 1.0.0
 */
export interface DisplayUpdateContactByIdOptions {
  /**
   * The ID of the contact to update.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Result from getting accounts.
 *
 * @since 1.0.0
 */
export interface GetAccountsResult {
  /**
   * List of accounts available on the device.
   *
   * @since 1.0.0
   */
  accounts: Account[];
}

/**
 * Options for getting a contact by ID.
 *
 * @since 1.0.0
 */
export interface GetContactByIdOptions {
  /**
   * The ID of the contact to retrieve.
   *
   * @since 1.0.0
   */
  id: string;

  /**
   * Optional list of specific fields to retrieve. If not specified, all fields are returned.
   *
   * @since 1.0.0
   */
  fields?: ContactField[];
}

/**
 * Result from getting a contact by ID.
 *
 * @since 1.0.0
 */
export interface GetContactByIdResult {
  /**
   * The contact, or null if not found.
   *
   * @since 1.0.0
   */
  contact: Contact | null;
}

/**
 * Options for getting contacts.
 *
 * @since 1.0.0
 */
export interface GetContactsOptions {
  /**
   * Optional list of specific fields to retrieve. If not specified, all fields are returned.
   *
   * @since 1.0.0
   */
  fields?: ContactField[];

  /**
   * Maximum number of contacts to return.
   *
   * @since 1.0.0
   */
  limit?: number;

  /**
   * Number of contacts to skip before starting to return results.
   *
   * @since 1.0.0
   */
  offset?: number;
}

/**
 * Result from getting contacts.
 *
 * @since 1.0.0
 */
export interface GetContactsResult {
  /**
   * List of contacts.
   *
   * @since 1.0.0
   */
  contacts: Contact[];
}

/**
 * Options for getting a group by ID.
 *
 * @since 1.0.0
 */
export interface GetGroupByIdOptions {
  /**
   * The ID of the group to retrieve.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Result from getting a group by ID.
 *
 * @since 1.0.0
 */
export interface GetGroupByIdResult {
  /**
   * The group, or null if not found.
   *
   * @since 1.0.0
   */
  group: Group | null;
}

/**
 * Result from getting groups.
 *
 * @since 1.0.0
 */
export interface GetGroupsResult {
  /**
   * List of groups.
   *
   * @since 1.0.0
   */
  groups: Group[];
}

/**
 * Contact group information.
 *
 * @since 1.0.0
 */
export interface Group {
  /**
   * Unique identifier for the group.
   *
   * @since 1.0.0
   */
  id: string;

  /**
   * Name of the group.
   *
   * @since 1.0.0
   */
  name: string;
}

/**
 * Options for creating a group.
 *
 * @since 1.0.0
 */
export interface CreateGroupOptions {
  /**
   * Group information to create. The 'id' field will be generated automatically.
   *
   * @since 1.0.0
   */
  group: Omit<Group, 'id'>;
}

/**
 * Result from creating a group.
 *
 * @since 1.0.0
 */
export interface CreateGroupResult {
  /**
   * The ID of the newly created group.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Options for deleting a contact by ID.
 *
 * @since 1.0.0
 */
export interface DeleteContactByIdOptions {
  /**
   * The ID of the contact to delete.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Options for deleting a group by ID.
 *
 * @since 1.0.0
 */
export interface DeleteGroupByIdOptions {
  /**
   * The ID of the group to delete.
   *
   * @since 1.0.0
   */
  id: string;
}

/**
 * Options for picking contacts using the native contact picker.
 *
 * @since 1.0.0
 */
export interface PickContactsOptions {
  /**
   * Optional list of specific fields to retrieve. If not specified, all fields are returned.
   *
   * @since 1.0.0
   */
  fields?: ContactField[];

  /**
   * Whether to allow selecting multiple contacts. Default is false.
   *
   * @since 1.0.0
   */
  multiple?: boolean;
}

/**
 * Result from picking contacts.
 *
 * @since 1.0.0
 */
export interface PickContactsResult {
  /**
   * List of selected contacts.
   *
   * @since 1.0.0
   */
  contacts: Contact[];
}

/**
 * Alias for PickContactsOptions.
 *
 * @since 1.0.0
 */
export type PickContactOptions = PickContactsOptions;

/**
 * Alias for PickContactsResult.
 *
 * @since 1.0.0
 */
export type PickContactResult = PickContactsResult;

/**
 * Options for updating a contact by ID.
 *
 * @since 1.0.0
 */
export interface UpdateContactByIdOptions {
  /**
   * The ID of the contact to update.
   *
   * @since 1.0.0
   */
  id: string;

  /**
   * Updated contact information.
   *
   * @since 1.0.0
   */
  contact: Omit<Contact, 'id'>;
}

/**
 * Result from checking if the plugin is supported on the platform.
 *
 * @since 1.0.0
 */
export interface IsSupportedResult {
  /**
   * Whether the plugin is supported on this platform.
   *
   * @since 1.0.0
   */
  isSupported: boolean;
}

/**
 * Result from checking if contacts are available on the device.
 *
 * @since 1.0.0
 */
export interface IsAvailableResult {
  /**
   * Whether contacts are available on this device.
   *
   * @since 1.0.0
   */
  isAvailable: boolean;
}

/**
 * Capacitor Contacts Plugin interface for managing device contacts.
 *
 * @since 1.0.0
 */
export interface CapacitorContactsPlugin {
  /**
   * Count the total number of contacts on the device.
   *
   * @returns Promise that resolves with the total count of contacts
   * @since 1.0.0
   */
  countContacts(): Promise<CountContactsResult>;

  /**
   * Create a new contact programmatically.
   *
   * @param options - The contact information to create
   * @returns Promise that resolves with the ID of the newly created contact
   * @since 1.0.0
   */
  createContact(options: CreateContactOptions): Promise<CreateContactResult>;

  /**
   * Create a new contact group.
   *
   * @param options - The group information to create
   * @returns Promise that resolves with the ID of the newly created group
   * @since 1.0.0
   */
  createGroup(options: CreateGroupOptions): Promise<CreateGroupResult>;

  /**
   * Delete a contact by ID.
   *
   * @param options - The ID of the contact to delete
   * @returns Promise that resolves when the contact is deleted
   * @since 1.0.0
   */
  deleteContactById(options: DeleteContactByIdOptions): Promise<void>;

  /**
   * Delete a group by ID.
   *
   * @param options - The ID of the group to delete
   * @returns Promise that resolves when the group is deleted
   * @since 1.0.0
   */
  deleteGroupById(options: DeleteGroupByIdOptions): Promise<void>;

  /**
   * Display a contact using the native contact viewer.
   *
   * @param options - The ID of the contact to display
   * @returns Promise that resolves when the viewer is closed
   * @since 1.0.0
   */
  displayContactById(options: DisplayContactByIdOptions): Promise<void>;

  /**
   * Display the native create contact UI.
   *
   * @param options - Optional pre-filled contact information
   * @returns Promise that resolves with the ID of the created contact, or undefined if cancelled
   * @since 1.0.0
   */
  displayCreateContact(options?: DisplayCreateContactOptions): Promise<DisplayCreateContactResult>;

  /**
   * Display the native update contact UI for a specific contact.
   *
   * @param options - The ID of the contact to update
   * @returns Promise that resolves when the update UI is closed
   * @since 1.0.0
   */
  displayUpdateContactById(options: DisplayUpdateContactByIdOptions): Promise<void>;

  /**
   * Get all accounts available on the device.
   *
   * @returns Promise that resolves with the list of accounts
   * @since 1.0.0
   */
  getAccounts(): Promise<GetAccountsResult>;

  /**
   * Get a specific contact by ID.
   *
   * @param options - The ID and optional fields to retrieve
   * @returns Promise that resolves with the contact, or null if not found
   * @since 1.0.0
   */
  getContactById(options: GetContactByIdOptions): Promise<GetContactByIdResult>;

  /**
   * Get all contacts from the device.
   *
   * @param options - Optional filters and pagination options
   * @returns Promise that resolves with the list of contacts
   * @since 1.0.0
   */
  getContacts(options?: GetContactsOptions): Promise<GetContactsResult>;

  /**
   * Get a specific group by ID.
   *
   * @param options - The ID of the group to retrieve
   * @returns Promise that resolves with the group, or null if not found
   * @since 1.0.0
   */
  getGroupById(options: GetGroupByIdOptions): Promise<GetGroupByIdResult>;

  /**
   * Get all contact groups.
   *
   * @returns Promise that resolves with the list of groups
   * @since 1.0.0
   */
  getGroups(): Promise<GetGroupsResult>;

  /**
   * Check if contacts are available on the device.
   *
   * @returns Promise that resolves with availability status
   * @since 1.0.0
   */
  isAvailable(): Promise<IsAvailableResult>;

  /**
   * Check if the plugin is supported on the current platform.
   *
   * @returns Promise that resolves with support status
   * @since 1.0.0
   */
  isSupported(): Promise<IsSupportedResult>;

  /**
   * Open the device's contacts settings.
   *
   * @returns Promise that resolves when the settings are opened
   * @since 1.0.0
   */
  openSettings(): Promise<void>;

  /**
   * Pick a single contact using the native contact picker.
   *
   * @param options - Optional fields to retrieve and picker configuration
   * @returns Promise that resolves with the selected contact(s)
   * @since 1.0.0
   */
  pickContact(options?: PickContactOptions): Promise<PickContactResult>;

  /**
   * Pick one or more contacts using the native contact picker.
   *
   * @param options - Optional fields to retrieve and picker configuration
   * @returns Promise that resolves with the selected contacts
   * @since 1.0.0
   */
  pickContacts(options?: PickContactsOptions): Promise<PickContactsResult>;

  /**
   * Update an existing contact by ID.
   *
   * @param options - The ID and updated contact information
   * @returns Promise that resolves when the contact is updated
   * @since 1.0.0
   */
  updateContactById(options: UpdateContactByIdOptions): Promise<void>;

  /**
   * Check the current permission status for contacts.
   *
   * @returns Promise that resolves with the current permission status
   * @since 1.0.0
   */
  checkPermissions(): Promise<PermissionStatus>;

  /**
   * Request permissions to access contacts.
   *
   * @param options - Optional specific permissions to request
   * @returns Promise that resolves with the updated permission status
   * @since 1.0.0
   */
  requestPermissions(options?: RequestPermissionsOptions): Promise<PermissionStatus>;

  /**
   * Get the native Capacitor plugin version.
   *
   * @returns Promise that resolves with the plugin version
   * @since 1.0.0
   */
  getPluginVersion(): Promise<{ version: string }>;
}
