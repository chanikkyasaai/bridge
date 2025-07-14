package com.example.phoneinfogetter
import android.content.Context
import android.os.Build
import android.provider.Settings

fun getDeviceInfo(context:Context):Map<String,String>{
    val isRooted=RootUtil.isDeviceRooted(context).toString()

    return mapOf(
        "Brand" to Build.BRAND,
        "Manufacturer" to Build.MANUFACTURER,
        "Model" to Build.MODEL,
        "Device" to Build.DEVICE,
        "Board" to Build.BOARD,
        "Hardware" to Build.HARDWARE,
        "Product" to Build.PRODUCT,
        "Display" to Build.DISPLAY,
        "Host" to Build.HOST,
        "ID" to Build.ID,
        "User" to Build.USER,
        "Android Version" to Build.VERSION.RELEASE,
        "SDK" to Build.VERSION.SDK_INT.toString(),
        "Secure Android ID" to Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID),
        "Is Device Rooted" to isRooted
    )

}