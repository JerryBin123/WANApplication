package com.example.wanapplication

import android.app.Application

class MyApplication: Application() {
    companion object{
        var _context:Application ?= null
        fun getContext() = _context!!
    }

    override fun onCreate() {
        super.onCreate()
        _context = this
    }
}