# Example App for `@capgo/capacitor-contacts`

This Vite project links directly to the local plugin source so you can try out the Contacts API integration while developing.

## Playground actions

- **Check permissions** – Reads the current read/write contacts permission states.
- **Request permissions** – Prompts the system permission dialog (native only once implemented).
- **Pick contact** – Opens the native contact picker (full contact; Android below 17 still needs `READ_CONTACTS`).
- **Pick phone / email / address** – Picks one contact property. No `READ_CONTACTS` permission on any Android version.
- **List contacts** – Reads the address book (`READ_CONTACTS` required).

## Getting started

```bash
npm install
npm start
```

Add native shells with `npx cap add ios` or `npx cap add android` from this folder to try behaviour on device or simulator.
