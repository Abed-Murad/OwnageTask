package com.am.ownagetask

import androidx.work.Operation
import com.chibatching.kotpref.KotprefModel

object GlobalValues : KotprefModel() {
    var uiStatus: String by stringPref(CONST.LOADING)
    var permissionsStatus: String by stringPref(CONST.NO_PERMISSIONS_NORMAL)
}


object CONST {
    const val LOADING = "loading"
    const val SUCCESS = "success"
    const val NO_PERMISSIONS_NORMAL = "no_permissions_normal"
    const val NO_PERMISSIONS_SETTINGS = "no_permissions_settings"
    const val PERMISSIONS_GRANTED = "permissions_granted"

}

