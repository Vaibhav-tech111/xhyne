package com.xhyne.os

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.content.ContextCompat

object PhoneActions {

    /**
     * Initiates a phone call or opens the dialer, depending on permissions.
     *
     * @param context The application context.
     * @param number The phone number to call.
     *
     * This function attempts to make an ACTION_CALL intent if the CALL_PHONE permission
     * is granted. ACTION_CALL directly initiates a call without user confirmation.
     *
     * If the CALL_PHONE permission is NOT granted, it falls back to an ACTION_DIAL intent.
     * ACTION_DIAL opens the phone's dialer application with the number pre-filled,
     * allowing the user to initiate the call manually.
     *
     * To use ACTION_CALL, you must declare the <uses-permission android:name="android.permission.CALL_PHONE" />
     * in your AndroidManifest.xml and request it at runtime on Android M (API 23) and above.
     */
    fun initiateCall(context: Context, number: String) {
        val callIntent: Intent
        val uri = Uri.parse("tel:$number")

        // Check if the CALL_PHONE permission is granted.
        // CALL_PHONE is a dangerous permission that needs to be requested at runtime on Android M and above.
        // If granted, we can directly initiate the call.
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, use ACTION_CALL to directly make the call.
            callIntent = Intent(Intent.ACTION_CALL, uri)
        } else {
            // Permission is not granted, fall back to ACTION_DIAL.
            // ACTION_DIAL will open the dialer with the number pre-filled,
            // allowing the user to press the call button themselves.
            callIntent = Intent(Intent.ACTION_DIAL, uri)
        }

        // Ensure there's an app to handle the intent to prevent a crash.
        if (callIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(callIntent)
        }
    }
}