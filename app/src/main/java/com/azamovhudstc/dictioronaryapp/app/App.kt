package com.azamovhudstc.dictioronaryapp.app

import android.app.Application
import com.azamovhudstc.dictioronaryapp.db.DbHelper

class App:Application ()   {
    override fun onCreate() {
        super.onCreate()
        DbHelper.init(this)
    }
}