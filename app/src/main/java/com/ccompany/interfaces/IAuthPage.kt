package com.ccompany.interfaces

import android.os.Bundle

interface IAuthPage {
    fun switchToRegistration(bundle: Bundle?, addToBack: Boolean? = true)
    fun switchToLogin(bundle: Bundle?, addToBack: Boolean? = true)
}
