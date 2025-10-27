# @capgo/capacitor-contacts
 <a href="https://capgo.app/"><img src='https://raw.githubusercontent.com/Cap-go/capgo/main/assets/capgo_banner.png' alt='Capgo - Instant updates for capacitor'/></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin"> ➡️ Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin"> Missing a feature? We’ll build the plugin for you 💪</a></h2>
</div>


Manage device contacts across iOS, Android, and the Web with a unified Capacitor API.

WIP: the plugin is not yet ready for production

## Documentation

The most complete doc is available here: https://capgo.app/docs/plugins/contacts/

## Install

```bash
npm install @capgo/capacitor-contacts
npx cap sync
```

## API

<docgen-index>

* [`countContacts()`](#countcontacts)
* [`createContact(...)`](#createcontact)
* [`createGroup(...)`](#creategroup)
* [`deleteContactById(...)`](#deletecontactbyid)
* [`deleteGroupById(...)`](#deletegroupbyid)
* [`displayContactById(...)`](#displaycontactbyid)
* [`displayCreateContact(...)`](#displaycreatecontact)
* [`displayUpdateContactById(...)`](#displayupdatecontactbyid)
* [`getAccounts()`](#getaccounts)
* [`getContactById(...)`](#getcontactbyid)
* [`getContacts(...)`](#getcontacts)
* [`getGroupById(...)`](#getgroupbyid)
* [`getGroups()`](#getgroups)
* [`isAvailable()`](#isavailable)
* [`isSupported()`](#issupported)
* [`openSettings()`](#opensettings)
* [`pickContact(...)`](#pickcontact)
* [`pickContacts(...)`](#pickcontacts)
* [`updateContactById(...)`](#updatecontactbyid)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions(...)`](#requestpermissions)
* [`getPluginVersion()`](#getpluginversion)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### countContacts()

```typescript
countContacts() => Promise<CountContactsResult>
```

**Returns:** <code>Promise&lt;<a href="#countcontactsresult">CountContactsResult</a>&gt;</code>

--------------------


### createContact(...)

```typescript
createContact(options: CreateContactOptions) => Promise<CreateContactResult>
```

| Param         | Type                                                                  |
| ------------- | --------------------------------------------------------------------- |
| **`options`** | <code><a href="#createcontactoptions">CreateContactOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#createcontactresult">CreateContactResult</a>&gt;</code>

--------------------


### createGroup(...)

```typescript
createGroup(options: CreateGroupOptions) => Promise<CreateGroupResult>
```

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#creategroupoptions">CreateGroupOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#creategroupresult">CreateGroupResult</a>&gt;</code>

--------------------


### deleteContactById(...)

```typescript
deleteContactById(options: DeleteContactByIdOptions) => Promise<void>
```

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletecontactbyidoptions">DeleteContactByIdOptions</a></code> |

--------------------


### deleteGroupById(...)

```typescript
deleteGroupById(options: DeleteGroupByIdOptions) => Promise<void>
```

| Param         | Type                                                                      |
| ------------- | ------------------------------------------------------------------------- |
| **`options`** | <code><a href="#deletegroupbyidoptions">DeleteGroupByIdOptions</a></code> |

--------------------


### displayContactById(...)

```typescript
displayContactById(options: DisplayContactByIdOptions) => Promise<void>
```

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displaycontactbyidoptions">DisplayContactByIdOptions</a></code> |

--------------------


### displayCreateContact(...)

```typescript
displayCreateContact(options?: DisplayCreateContactOptions | undefined) => Promise<DisplayCreateContactResult>
```

| Param         | Type                                                                                |
| ------------- | ----------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displaycreatecontactoptions">DisplayCreateContactOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#displaycreatecontactresult">DisplayCreateContactResult</a>&gt;</code>

--------------------


### displayUpdateContactById(...)

```typescript
displayUpdateContactById(options: DisplayUpdateContactByIdOptions) => Promise<void>
```

| Param         | Type                                                                                        |
| ------------- | ------------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#displayupdatecontactbyidoptions">DisplayUpdateContactByIdOptions</a></code> |

--------------------


### getAccounts()

```typescript
getAccounts() => Promise<GetAccountsResult>
```

**Returns:** <code>Promise&lt;<a href="#getaccountsresult">GetAccountsResult</a>&gt;</code>

--------------------


### getContactById(...)

```typescript
getContactById(options: GetContactByIdOptions) => Promise<GetContactByIdResult>
```

| Param         | Type                                                                    |
| ------------- | ----------------------------------------------------------------------- |
| **`options`** | <code><a href="#getcontactbyidoptions">GetContactByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcontactbyidresult">GetContactByIdResult</a>&gt;</code>

--------------------


### getContacts(...)

```typescript
getContacts(options?: GetContactsOptions | undefined) => Promise<GetContactsResult>
```

| Param         | Type                                                              |
| ------------- | ----------------------------------------------------------------- |
| **`options`** | <code><a href="#getcontactsoptions">GetContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getcontactsresult">GetContactsResult</a>&gt;</code>

--------------------


### getGroupById(...)

```typescript
getGroupById(options: GetGroupByIdOptions) => Promise<GetGroupByIdResult>
```

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#getgroupbyidoptions">GetGroupByIdOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#getgroupbyidresult">GetGroupByIdResult</a>&gt;</code>

--------------------


### getGroups()

```typescript
getGroups() => Promise<GetGroupsResult>
```

**Returns:** <code>Promise&lt;<a href="#getgroupsresult">GetGroupsResult</a>&gt;</code>

--------------------


### isAvailable()

```typescript
isAvailable() => Promise<IsAvailableResult>
```

**Returns:** <code>Promise&lt;<a href="#isavailableresult">IsAvailableResult</a>&gt;</code>

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

--------------------


### pickContact(...)

```typescript
pickContact(options?: PickContactsOptions | undefined) => Promise<PickContactResult>
```

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

--------------------


### pickContacts(...)

```typescript
pickContacts(options?: PickContactsOptions | undefined) => Promise<PickContactsResult>
```

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#pickcontactsoptions">PickContactsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#pickcontactsresult">PickContactsResult</a>&gt;</code>

--------------------


### updateContactById(...)

```typescript
updateContactById(options: UpdateContactByIdOptions) => Promise<void>
```

| Param         | Type                                                                          |
| ------------- | ----------------------------------------------------------------------------- |
| **`options`** | <code><a href="#updatecontactbyidoptions">UpdateContactByIdOptions</a></code> |

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### requestPermissions(...)

```typescript
requestPermissions(options?: RequestPermissionsOptions | undefined) => Promise<PermissionStatus>
```

| Param         | Type                                                                            |
| ------------- | ------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#requestpermissionsoptions">RequestPermissionsOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### getPluginVersion()

```typescript
getPluginVersion() => Promise<{ version: string; }>
```

Get the native Capacitor plugin version.

**Returns:** <code>Promise&lt;{ version: string; }&gt;</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### CountContactsResult

| Prop        | Type                |
| ----------- | ------------------- |
| **`count`** | <code>number</code> |


#### CreateContactResult

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### CreateContactOptions

| Prop          | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> |


#### Contact

| Prop                   | Type                                          |
| ---------------------- | --------------------------------------------- |
| **`id`**               | <code>string</code>                           |
| **`account`**          | <code><a href="#account">Account</a></code>   |
| **`birthday`**         | <code><a href="#birthday">Birthday</a></code> |
| **`emailAddresses`**   | <code>EmailAddress[]</code>                   |
| **`familyName`**       | <code>string</code>                           |
| **`fullName`**         | <code>string</code>                           |
| **`givenName`**        | <code>string</code>                           |
| **`groupIds`**         | <code>string[]</code>                         |
| **`jobTitle`**         | <code>string</code>                           |
| **`middleName`**       | <code>string</code>                           |
| **`namePrefix`**       | <code>string</code>                           |
| **`nameSuffix`**       | <code>string</code>                           |
| **`note`**             | <code>string</code>                           |
| **`organizationName`** | <code>string</code>                           |
| **`phoneNumbers`**     | <code>PhoneNumber[]</code>                    |
| **`photo`**            | <code>string</code>                           |
| **`postalAddresses`**  | <code>PostalAddress[]</code>                  |
| **`urlAddresses`**     | <code>UrlAddress[]</code>                     |


#### Account

| Prop       | Type                |
| ---------- | ------------------- |
| **`name`** | <code>string</code> |
| **`type`** | <code>string</code> |


#### Birthday

| Prop        | Type                |
| ----------- | ------------------- |
| **`day`**   | <code>number</code> |
| **`month`** | <code>number</code> |
| **`year`**  | <code>number</code> |


#### EmailAddress

| Prop            | Type                                                          |
| --------------- | ------------------------------------------------------------- |
| **`value`**     | <code>string</code>                                           |
| **`type`**      | <code><a href="#emailaddresstype">EmailAddressType</a></code> |
| **`label`**     | <code>string</code>                                           |
| **`isPrimary`** | <code>boolean</code>                                          |


#### PhoneNumber

| Prop            | Type                                                        |
| --------------- | ----------------------------------------------------------- |
| **`value`**     | <code>string</code>                                         |
| **`type`**      | <code><a href="#phonenumbertype">PhoneNumberType</a></code> |
| **`label`**     | <code>string</code>                                         |
| **`isPrimary`** | <code>boolean</code>                                        |


#### PostalAddress

| Prop                 | Type                                                            |
| -------------------- | --------------------------------------------------------------- |
| **`city`**           | <code>string</code>                                             |
| **`country`**        | <code>string</code>                                             |
| **`formatted`**      | <code>string</code>                                             |
| **`isoCountryCode`** | <code>string</code>                                             |
| **`isPrimary`**      | <code>boolean</code>                                            |
| **`label`**          | <code>string</code>                                             |
| **`neighborhood`**   | <code>string</code>                                             |
| **`postalCode`**     | <code>string</code>                                             |
| **`state`**          | <code>string</code>                                             |
| **`street`**         | <code>string</code>                                             |
| **`type`**           | <code><a href="#postaladdresstype">PostalAddressType</a></code> |


#### UrlAddress

| Prop        | Type                                                      |
| ----------- | --------------------------------------------------------- |
| **`value`** | <code>string</code>                                       |
| **`type`**  | <code><a href="#urladdresstype">UrlAddressType</a></code> |
| **`label`** | <code>string</code>                                       |


#### CreateGroupResult

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### CreateGroupOptions

| Prop        | Type                                                                          |
| ----------- | ----------------------------------------------------------------------------- |
| **`group`** | <code><a href="#omit">Omit</a>&lt;<a href="#group">Group</a>, 'id'&gt;</code> |


#### Group

| Prop       | Type                |
| ---------- | ------------------- |
| **`id`**   | <code>string</code> |
| **`name`** | <code>string</code> |


#### DeleteContactByIdOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### DeleteGroupByIdOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### DisplayContactByIdOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### DisplayCreateContactResult

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### DisplayCreateContactOptions

| Prop          | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> |


#### DisplayUpdateContactByIdOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### GetAccountsResult

| Prop           | Type                   |
| -------------- | ---------------------- |
| **`accounts`** | <code>Account[]</code> |


#### GetContactByIdResult

| Prop          | Type                                                |
| ------------- | --------------------------------------------------- |
| **`contact`** | <code><a href="#contact">Contact</a> \| null</code> |


#### GetContactByIdOptions

| Prop         | Type                                                  |
| ------------ | ----------------------------------------------------- |
| **`id`**     | <code>string</code>                                   |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> |


#### GetContactsResult

| Prop           | Type                   |
| -------------- | ---------------------- |
| **`contacts`** | <code>Contact[]</code> |


#### GetContactsOptions

| Prop         | Type                                                  |
| ------------ | ----------------------------------------------------- |
| **`fields`** | <code>(keyof <a href="#contact">Contact</a>)[]</code> |
| **`limit`**  | <code>number</code>                                   |
| **`offset`** | <code>number</code>                                   |


#### GetGroupByIdResult

| Prop        | Type                                            |
| ----------- | ----------------------------------------------- |
| **`group`** | <code><a href="#group">Group</a> \| null</code> |


#### GetGroupByIdOptions

| Prop     | Type                |
| -------- | ------------------- |
| **`id`** | <code>string</code> |


#### GetGroupsResult

| Prop         | Type                 |
| ------------ | -------------------- |
| **`groups`** | <code>Group[]</code> |


#### IsAvailableResult

| Prop              | Type                 |
| ----------------- | -------------------- |
| **`isAvailable`** | <code>boolean</code> |


#### IsSupportedResult

| Prop              | Type                 |
| ----------------- | -------------------- |
| **`isSupported`** | <code>boolean</code> |


#### PickContactsResult

| Prop           | Type                   |
| -------------- | ---------------------- |
| **`contacts`** | <code>Contact[]</code> |


#### PickContactsOptions

| Prop           | Type                                                  |
| -------------- | ----------------------------------------------------- |
| **`fields`**   | <code>(keyof <a href="#contact">Contact</a>)[]</code> |
| **`multiple`** | <code>boolean</code>                                  |


#### UpdateContactByIdOptions

| Prop          | Type                                                                              |
| ------------- | --------------------------------------------------------------------------------- |
| **`id`**      | <code>string</code>                                                               |
| **`contact`** | <code><a href="#omit">Omit</a>&lt;<a href="#contact">Contact</a>, 'id'&gt;</code> |


#### PermissionStatus

| Prop                | Type                                                                        |
| ------------------- | --------------------------------------------------------------------------- |
| **`readContacts`**  | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> |
| **`writeContacts`** | <code><a href="#contactspermissionstate">ContactsPermissionState</a></code> |


#### RequestPermissionsOptions

| Prop              | Type                                  |
| ----------------- | ------------------------------------- |
| **`permissions`** | <code>ContactsPermissionType[]</code> |


### Type Aliases


#### Omit

Construct a type with the properties of T except for those in type K.

<code><a href="#pick">Pick</a>&lt;T, <a href="#exclude">Exclude</a>&lt;keyof T, K&gt;&gt;</code>


#### Pick

From T, pick a set of properties whose keys are in the union K

<code>{ [P in K]: T[P]; }</code>


#### Exclude

<a href="#exclude">Exclude</a> from T those types that are assignable to U

<code>T extends U ? never : T</code>


#### EmailAddressType

<code>'CUSTOM' | 'HOME' | 'ICLOUD' | 'OTHER' | 'WORK'</code>


#### PhoneNumberType

<code>'ASSISTANT' | 'CALLBACK' | 'CAR' | 'COMPANY_MAIN' | 'CUSTOM' | 'FAX_HOME' | 'FAX_WORK' | 'HOME' | 'HOME_FAX' | 'ISDN' | 'MAIN' | 'MMS' | 'MOBILE' | 'OTHER' | 'OTHER_FAX' | 'PAGER' | 'RADIO' | 'TELEX' | 'TTY_TDD' | 'WORK' | 'WORK_MOBILE' | 'WORK_PAGER'</code>


#### PostalAddressType

<code>'CUSTOM' | 'HOME' | 'OTHER' | 'WORK'</code>


#### UrlAddressType

<code>'BLOG' | 'CUSTOM' | 'FTP' | 'HOME' | 'HOMEPAGE' | 'OTHER' | 'PROFILE' | 'SCHOOL' | 'WORK'</code>


#### ContactField

<code>keyof <a href="#contact">Contact</a></code>


#### PickContactOptions

<code><a href="#pickcontactsoptions">PickContactsOptions</a></code>


#### PickContactResult

<code><a href="#pickcontactsresult">PickContactsResult</a></code>


#### ContactsPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


#### ContactsPermissionType

<code>'readContacts' | 'writeContacts'</code>

</docgen-api>

### Credit

This plugin was inspired from: https://github.com/kesha-antonov/react-native-background-downloader
