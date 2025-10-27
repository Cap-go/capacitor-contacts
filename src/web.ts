import { WebPlugin } from '@capacitor/core';

import type {
  CapacitorContactsPlugin,
  CountContactsResult,
  CreateContactOptions,
  CreateContactResult,
  CreateGroupOptions,
  CreateGroupResult,
  DeleteContactByIdOptions,
  DeleteGroupByIdOptions,
  DisplayContactByIdOptions,
  DisplayCreateContactOptions,
  DisplayCreateContactResult,
  DisplayUpdateContactByIdOptions,
  GetAccountsResult,
  GetContactByIdOptions,
  GetContactByIdResult,
  GetContactsOptions,
  GetContactsResult,
  GetGroupByIdOptions,
  GetGroupByIdResult,
  GetGroupsResult,
  IsAvailableResult,
  IsSupportedResult,
  PermissionStatus,
  PickContactOptions,
  PickContactResult,
  PickContactsOptions,
  PickContactsResult,
  RequestPermissionsOptions,
  UpdateContactByIdOptions,
} from './definitions';

export class CapacitorContactsWeb extends WebPlugin implements CapacitorContactsPlugin {
  async countContacts(): Promise<CountContactsResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async createContact(_options: CreateContactOptions): Promise<CreateContactResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async createGroup(_options: CreateGroupOptions): Promise<CreateGroupResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async deleteContactById(_options: DeleteContactByIdOptions): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async deleteGroupById(_options: DeleteGroupByIdOptions): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async displayContactById(_options: DisplayContactByIdOptions): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async displayCreateContact(_options?: DisplayCreateContactOptions): Promise<DisplayCreateContactResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async displayUpdateContactById(_options: DisplayUpdateContactByIdOptions): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async getAccounts(): Promise<GetAccountsResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async getContactById(_options: GetContactByIdOptions): Promise<GetContactByIdResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async getContacts(_options?: GetContactsOptions): Promise<GetContactsResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async getGroupById(_options: GetGroupByIdOptions): Promise<GetGroupByIdResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async getGroups(): Promise<GetGroupsResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async isAvailable(): Promise<IsAvailableResult> {
    return { isAvailable: false };
  }

  async isSupported(): Promise<IsSupportedResult> {
    return { isSupported: false };
  }

  async openSettings(): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async pickContact(_options?: PickContactOptions): Promise<PickContactResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async pickContacts(_options?: PickContactsOptions): Promise<PickContactsResult> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async updateContactById(_options: UpdateContactByIdOptions): Promise<void> {
    throw this.unavailable('Contacts API not available on the web implementation.');
  }

  async checkPermissions(): Promise<PermissionStatus> {
    return { readContacts: 'denied', writeContacts: 'denied' };
  }

  async requestPermissions(_options?: RequestPermissionsOptions): Promise<PermissionStatus> {
    return { readContacts: 'denied', writeContacts: 'denied' };
  }

  async getPluginVersion(): Promise<{ version: string }> {
    return { version: 'web' };
  }
}
