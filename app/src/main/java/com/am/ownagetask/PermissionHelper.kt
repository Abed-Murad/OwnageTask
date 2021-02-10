package com.am.ownagetask

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun checkPermission(context: Context?, Permission: String?): Boolean {
    return ContextCompat.checkSelfPermission(
        context!!,
        Permission!!
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(
    thisActivity: Activity?,
    Permission: Array<String>,
    code: Int
) {
    if (ContextCompat.checkSelfPermission(
            thisActivity!!,
            Permission[0]
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity, Permission[0])) {
        } else {
            ActivityCompat.requestPermissions(thisActivity, Permission, code)
        }
    }
}
