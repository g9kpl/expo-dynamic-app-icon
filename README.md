# 🎨 @g9k/expo-dynamic-app-icon

Easily **change your app icon dynamically** in **Expo SDK 52, 53 & 54**!

## 📑 Table of Contents

- [What's New](#-whats-new-in-this-fork)
- [Features](#-features)
- [Requirements](#️-requirements)
- [Quick Start](#-quick-start)
- [Installation](#-installation)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [Troubleshooting](#-troubleshooting)
- [Roadmap](#️-roadmap)

---

## 🚀 **What's New in This Fork:**

✨ **Available as an NPM package**

## 🎁 **Features:**

✅ Reset icon to default
✅ Support for **round icons**
✅ Different icons for **iOS and Android**
✅ Dynamic icon variants for **iOS** (light, dark, tinted)
✅ iOS icon update **with or without alert popup**
✅ **Simple API** to get and set the app icon

## Demo🚀

![dynamic-icon-demo-5](https://github.com/user-attachments/assets/3dced15a-8d4e-4eb9-b76c-4c7c8fc9f59a)

---

## ⚙️ Requirements

- **Expo SDK 52, 53 or 54** (SDK 54 support is untested but should work)
- **Development Client required** - This module uses native code and cannot work with Expo Go
- **Both iOS and Android** supported

---

## 🚀 Quick Start

```sh
# 1. Install
npx expo install @g9k/expo-dynamic-app-icon

# 2. Add icons to your app.json (see Configuration below)

# 3. Generate native files
expo prebuild

# 4. Build and run
npx expo run:ios    # or npx expo run:android
```

Then in your code:

```typescript
import { setAppIcon, getAppIcon } from "@g9k/expo-dynamic-app-icon";

// Change icon (use the names you configured in app.json)
setAppIcon("blue");

// Get current icon
const current = getAppIcon();
```

---

## 📦 Installation

```sh
npx expo install @g9k/expo-dynamic-app-icon
```

---

## 🔧 Configuration

### Step 1: Configure Your Icons

Add the plugin to your `app.json` or `app.config.js`:

```json
"plugins": [
  [
    "@g9k/expo-dynamic-app-icon",
    {
      "blue": {
        "ios": "./assets/ios_icon_blue.png",
        "android": {
          "foregroundImage": "./assets/android_icon_blue_fg.png",
          "backgroundColor": "#0000FF"
        }
      },
      "red": {
        "ios": "./assets/ios_icon_red.png",
        "android": {
          "foregroundImage": "./assets/android_icon_red_fg.png",
          "backgroundColor": "#FF0000"
        }
      },
      "purple": {
        "ios": "./assets/ios_icon_purple.png",
        "android": "./assets/android_icon_purple.png" // Legacy string format still supported
      },
      "summer": {
        "ios": {
          "light": "./assets/ios_icon_summer_light.png",
          "dark": "./assets/ios_icon_summer_dark.png",
          "tinted": "./assets/ios_icon_summer_tinted.png"
        }
        // Android can also use the adaptive format here if desired
      }
    }
  ]
]
```

**Note on Android Adaptive Icons:**
For Android, you can now provide an object with `foregroundImage` (path to your foreground asset) and `backgroundColor` (hex string) to generate proper adaptive icons. If you provide a direct string path, it will be treated as a legacy icon.

**Icon Name Reference:**
The icon names you configure here (e.g., `blue`, `red`, `purple`, `summer`) are what you'll pass to `setAppIcon()` in your code.

---

### Step 2: Prebuild (Generate Native Code)

After configuring your icons, you need to generate the native files:

```sh
expo prebuild
```

This command will:

- ✅ Generate native iOS and Android projects
- ✅ Add icon aliases to `AndroidManifest.xml`
- ✅ Configure iOS project settings
- ✅ Copy and process your icon assets

**Important:** You must run `expo prebuild` again whenever you:

- Add or remove icon configurations
- Change icon file paths
- Update the plugin configuration

---

### Step 3: Build Development Client

Since this module uses native code, you can't use Expo Go. Build a development client:

```sh
# For iOS
npx expo run:ios

# For Android
npx expo run:android
```

This will:

- Compile the native code
- Install the app on your device/simulator
- Start the Metro bundler

---

## 📜 Verifying Native Setup

After running `expo prebuild`, you can verify the setup:

### Android Verification

Check if the following lines have been added to `android/app/src/main/AndroidManifest.xml`. The `android:icon` and `android:roundIcon` attributes will point to different resources based on your configuration:

**For Adaptive Icons (example: `blue`):**

```xml
<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivityblue"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/ic_launcher_adaptive_blue"
  android:roundIcon="@mipmap/ic_launcher_adaptive_blue"
  android:targetActivity=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
  </intent-filter>
</activity-alias>
```

**For Legacy Icons (example: `purple`):**

```xml
<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivitypurple"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/purple"
  android:roundIcon="@mipmap/purple_round"
  android:targetActivity=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
  </intent-filter>
</activity-alias>
```

---

## 🚀 Usage

### **Set App Icon**

```typescript
import { setAppIcon } from "@g9k/expo-dynamic-app-icon";

/**
 * Change app icon to 'blue'
 * (Use the icon names from your app.json configuration)
 */
setAppIcon("blue");

/**
 * Change to 'red' icon
 */
setAppIcon("red");

/**
 * Reset to default icon
 */
setAppIcon(null);
```

#### ✅ Available Parameters:

```typescript
setAppIcon(
  name: IconName | null,
  isInBackground?: boolean
)
```

| Parameter        | Type               | Default | Description                                                                                                                    |
| ---------------- | ------------------ | ------- | ------------------------------------------------------------------------------------------------------------------------------ |
| `name`           | `IconName \| null` | `null`  | The icon name to switch to. Pass `null` to reset to the default icon.                                                          |
| `isInBackground` | `boolean`          | `true`  | - `true`: Icon changes silently in the background (no alert on iOS).<br>- `false`: Immediate change, with system alert on iOS. |

#### ✅ Returns:

- `"DEFAULT"` if reset to the original icon.
- The **new icon name** on success.
- `false` if an error occurs.

---

### **Get Current Icon**

```typescript
import { getAppIcon } from "@g9k/expo-dynamic-app-icon";

// Get the current app icon name
const icon = getAppIcon();
console.log(icon); // "blue" or "red" or "purple" (or "DEFAULT" if not changed)
```

---

### ⚠️ Notes:

- **Android limitations:**
  Android does **not** support icon changes while the app is running in the foreground.
  To work around this, the icon is changed when the app enters the **Pause state** (background).

- ⚠️ **Pause state** can also trigger during events like permission dialogs.
  To avoid unwanted icon changes, a **5-second delay** is added to ensure the app is truly in the background.

- To disable the delay and apply the icon change immediately (with the risk of it running during permission dialogs or other pause events), set:

  ```typescript
  setAppIcon("blue", false);
  ```

  - On **iOS**, `isInBackground: false` triggers the system alert immediately.
  - On **Android**, it applies the icon change right away without waiting.

---

## 🔧 Troubleshooting

### "Module not found" or import errors

**Problem:** Getting errors like `Cannot find module '@g9k/expo-dynamic-app-icon'`

**Solution:**

1. Make sure you installed the package: `npx expo install @g9k/expo-dynamic-app-icon`
2. Clear cache: `npx expo start --clear`
3. Rebuild the app: `npx expo run:ios` or `npx expo run:android`

### Icons not changing

**Problem:** `setAppIcon()` returns false or icons don't change

**Solution:**

1. Verify you ran `expo prebuild` after configuring icons
2. Check that icon names match your `app.json` configuration exactly (case-sensitive)
3. Ensure icon assets exist at the paths you specified
4. Rebuild your app: `npx expo run:ios` or `npx expo run:android`
5. Check native logs for errors:
   - iOS: Open Xcode logs
   - Android: Run `npx react-native log-android`

### "This module uses native code and cannot work with Expo Go"

**Problem:** App crashes or module doesn't work in Expo Go

**Solution:**
This is expected behavior. You **must** use a development client:

```sh
npx expo run:ios   # For iOS
npx expo run:android   # For Android
```

### Icons not appearing after prebuild

**Problem:** Ran prebuild but icons missing in native projects

**Solution:**

1. Check that icon paths in `app.json` are correct and files exist
2. Delete `ios/` and `android/` folders
3. Run `expo prebuild --clean`
4. Rebuild: `npx expo run:ios` or `npx expo run:android`

### Android icon changes but shows wrong icon temporarily

**Problem:** Icon briefly shows old icon before changing

**Solution:**
This is an Android limitation. The system caches launcher icons. Try:

1. Clear launcher cache (varies by device)
2. Restart device
3. Uninstall and reinstall the app

### iOS shows alert popup when changing icon

**Problem:** iOS displays a system alert when icon changes

**Solution:**
This is iOS default behavior when changing icons immediately. To change in background:

```typescript
setAppIcon("blue", true); // true = silent background change (default)
```

### Configuration changes not taking effect

**Problem:** Updated `app.json` but changes don't appear

**Solution:**

1. Run `expo prebuild` again (required after ANY config changes)
2. Rebuild the app: `npx expo run:ios` or `npx expo run:android`
3. Don't just refresh - native changes require full rebuild

---

## 🗺️ Roadmap

Help us improve this package! Here's what's on the horizon:

- [ ] **Verify compatibility with Expo SDK 54** - Test and confirm the package works with SDK 54
- [ ] **Improve documentation** - Add video tutorial or animated examples
- [ ] **Enhanced TypeScript support** - Better type definitions for icon configurations
- [ ] **Testing suite** - Add automated tests for iOS and Android
- [ ] **Performance optimization** - Reduce icon switching latency on Android

Want to contribute? Feel free to:

- Open an issue for bugs or feature requests
- Submit a PR for any of the above items
- Share your use cases and feedback

---

## ☕ Shoutout to previous contributors

A huge shoutout to:

- [outsung](https://github.com/outsung) for the original package!
- [mozzius](https://github.com/mozzius) for adding support for Expo SDK 51!
- [howincodes](https://github.com/howincodes) for adding support for Expo SDK 52 and for a couple of other improvements!
- [elberfeld2](https://github.com/elberfeld2) for adding support for Expo SDK 53!

---

## 🌐 About Us

This package is maintained by Piotr Grzegorczyk.

🔥 **Enjoy building dynamic and customizable apps with Expo!** 🚀
