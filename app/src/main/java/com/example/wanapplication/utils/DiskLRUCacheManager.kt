package com.example.wanapplication.utils

import android.content.Context
import android.os.Environment
import com.example.wanapplication.MyApplication
import com.example.wanapplication.bean.home.BannerData
import com.jakewharton.disklrucache.DiskLruCache
import java.io.File

object DiskLRUCacheManager {
    fun makeCache(bannerData : List<BannerData>){
        val file = getCacheFile(MyApplication.getContext(), "liuchache")
        val diskLruCache = DiskLruCache.open(file,1,1,10*1024*1024)

    }

    private fun getCacheFile(context: Context, uniqueName:String): File {
        var cachePath : String ?= null
        if (Environment.MEDIA_CHECKING.equals(Environment.getExternalStorageState())
            || !Environment.isExternalStorageRemovable()
            && context.getExternalCacheDir() != null){
            cachePath = context.externalCacheDir?.path
        }else{
            cachePath = context.cacheDir?.path
        }
        return File("$cachePath${File.separator}$uniqueName")
    }
}