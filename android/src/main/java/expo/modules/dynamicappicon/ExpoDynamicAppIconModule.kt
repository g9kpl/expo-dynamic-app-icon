package expo.modules.dynamicappicon

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.ComponentName
import expo.modules.kotlin.modules.Module
import android.util.Log
import expo.modules.kotlin.modules.ModuleDefinition

class ExpoDynamicAppIconModule : Module() {

  // For Support Contact: bashahowin@gmail.com

  override fun definition() = ModuleDefinition {
    Name("ExpoDynamicAppIcon")

    Function("setAppIcon") { name: String?, isInBackground: Boolean? ->
      try {
        SharedObject.packageName = context.packageName
        SharedObject.pm = pm
        SharedObject.shouldChangeIcon = true
        SharedObject.isInBackground = isInBackground ?: true

        if (name == null) {
          // Reseting  to default icon if nothing  passed
          var currentIcon = if (!SharedObject.icon.isEmpty()) SharedObject.icon else context.packageName + ".MainActivity"

          if (currentIcon == context.packageName + ".MainActivity") {
            SharedObject.icon = context.packageName + ".MainActivity"
            return@Function "DEFAULT"
          }

          SharedObject.classesToKill.add(currentIcon) 
          SharedObject.icon = context.packageName + ".MainActivity"

          return@Function "DEFAULT" 
        } else {

          var newIcon: String = context.packageName + ".MainActivity" + name
          var currentIcon: String = if (!SharedObject.icon.isEmpty()) SharedObject.icon else context.packageName + ".MainActivity"

          if (currentIcon == newIcon) {
            return@Function name
          }

          SharedObject.classesToKill.add(currentIcon)
          SharedObject.icon = newIcon

          return@Function name
        }
      } catch (e: Exception) {
        return@Function false
      }
    }

    Function("getAppIcon") {
      var componentClass: String = currentActivity.componentName.className
      var currentIcon: String = if (!SharedObject.icon.isEmpty()) SharedObject.icon else componentClass
      var currentIconName: String = currentIcon.split("MainActivity")[1]

      return@Function if (currentIconName.isEmpty()) "DEFAULT" else currentIconName
    }
  }

  private val context: Context
    get() = requireNotNull(appContext.reactContext) { "React Application Context is null" }

  private val currentActivity
    get() = requireNotNull(appContext.activityProvider?.currentActivity)

  private val pm
    get() = requireNotNull(currentActivity.packageManager)
}
