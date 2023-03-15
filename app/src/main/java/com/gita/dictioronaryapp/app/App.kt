package com.gita.dictioronaryapp.app

import android.app.Application
import com.gita.dictioronaryapp.db.DbHelper

class App:Application ()   {
    override fun onCreate() {
        super.onCreate()
        DbHelper.init(this)
    }
}