package com.randomdroids.openweathermap.common

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

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