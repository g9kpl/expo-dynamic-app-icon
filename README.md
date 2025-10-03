# 🎨 @g9kpl/expo-dynamic-app-icon

Easily **change your app icon dynamically** in **Expo SDK 52 & 53**!

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

## 📦 Installation

```sh
npx expo install @g9kpl/expo-dynamic-app-icon
```

---

## 🔧 Setup

Add the plugin to your `app.json`:

```json
"plugins": [
  [
    "@g9kpl/expo-dynamic-app-icon",
    {
      "defaultLight": {
        "ios": "./assets/ios_icon_light.png",
        "android": {
          "foregroundImage": "./assets/android_icon_light_fg.png",
          "backgroundColor": "#FFFFFF"
        }
      },
      "defaultDark": {
        "ios": "./assets/ios_icon_dark.png",
        "android": {
          "foregroundImage": "./assets/android_icon_dark_fg.png",
          "backgroundColor": "#121212"
        }
      },
      "legacyRed": {
        "ios": "./assets/ios_icon_red.png",
        "android": "./assets/android_icon_red_legacy.png" // Legacy string format still supported
      },
      "dynamicTheme": {
        "ios": {
          "light": "./assets/ios_icon_themed_light.png",
          "dark": "./assets/ios_icon_themed_dark.png",
          "tinted": "./assets/ios_icon_themed_tinted.png"
        }
        // Android can also use the adaptive format here if desired
      }
    }
  ]
]
```

**Note on Android Adaptive Icons:**
For Android, you can now provide an object with `foregroundImage` (path to your foreground asset) and `backgroundColor` (hex string) to generate proper adaptive icons. If you provide a direct string path, it will be treated as a legacy icon.

---

## 📜 Android Setup

Run the following command:

```sh
expo prebuild
```

Then, check if the following lines have been added to `AndroidManifest.xml`. The `android:icon` and `android:roundIcon` attributes will point to different resources based on your configuration:

**For Adaptive Icons (example: `defaultDark`):**

```xml
<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivitydefaultDark"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/ic_launcher_adaptive_defaultdark"
  android:roundIcon="@mipmap/ic_launcher_adaptive_defaultdark"
  android:targetActivity=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN"/>
    <category android:name="android.intent.category.LAUNCHER"/>
  </intent-filter>
</activity-alias>
```

**For Legacy Icons (example: `legacyRed`):**

```xml
<activity-alias
  android:name="expo.modules.dynamicappicon.example.MainActivitylegacyRed"
  android:enabled="false"
  android:exported="true"
  android:icon="@mipmap/legacyred"
  android:roundIcon="@mipmap/legacyred_round"
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
import { setAppIcon } from "@g9kpl/expo-dynamic-app-icon";

/**
 * Change app icon to 'red'
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
import { getAppIcon } from "@howincodes/expo-dynamic-app-icon";

// Get the current app icon name
const icon = getAppIcon();
console.log(icon); // "red" (or "DEFAULT" if not changed)
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
  setAppIcon("red", false);
  ```

  - On **iOS**, `isInBackground: false` triggers the system alert immediately.
  - On **Android**, it applies the icon change right away without waiting.

## ☕ Support the Original Author

A huge shoutout to:

- [outsung](https://github.com/outsung) for the original package!
- [mozzius](https://github.com/mozzius) for adding support for Expo SDK 51!
- [howincodes](https://github.com/howincodes) for adding support for Expo SDK 52 and for a couple of other improvements!
- [elberfeld2](https://github.com/elberfeld2) for adding support for Expo SDK 53!

---

## 🌐 About Us

This package is maintained by Piotr Grzegorczyk.

🔥 **Enjoy building dynamic and customizable apps with Expo!** 🚀
