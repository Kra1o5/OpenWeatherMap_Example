package com.randomdroids.openweathermap.common

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

/**
 * Alert dialog.
 *
 * @param context Context
 * @param message Message to show
 * @param okListener Ok listener
 * @param cancelListener Cancel listener
 */
fun alertDialog(
    context: Context,
    message: String,
    okListener: DialogInterface.OnClickListener?,
    cancelListener: DialogInterface.OnClickListener?
) {
    AlertDialog.Builder(context)
        .setMessage(message)
        .setCancelable(false)
        .setNegativeButton(android.R.string.cancel, cancelListener)
        .setPositiveButton(android.R.string.ok, okListener)
        .show()
}