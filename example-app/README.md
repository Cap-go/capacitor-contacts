# Example App for `@capgo/capacitor-contacts`

This Vite project links directly to the local plugin source so you can try out the Contacts API integration while developing.

## Playground actions

- **Check permissions** – Reads the current read/write contacts permission states.
- **Request permissions** – Prompts the system permission dialog (native only once implemented).
- **Pick contact / List contacts** – Placeholder actions that will surface results once the native bridges are complete.

## Getting started

```bash
npm install
npm start
```

Add native shells with `npx cap add ios` or `npx cap add android` from this folder to try behaviour on device or simulator.
