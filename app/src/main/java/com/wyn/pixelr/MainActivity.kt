package com.wyn.pixelr

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val textView = TextView(this)
        setContentView(textView)

        val isRooted = isDeviceRooted()
        textView.text = if (isRooted) "Device is ROOTED" else "Device is NOT ROOTED"
    }

    private fun isDeviceRooted(): Boolean {
        return checkBuildTags() || checkSuBinary() || checkSuperUserApk()
    }

    private fun checkBuildTags(): Boolean {
        val tags = android.os.Build.TAGS
        return tags != null && tags.contains("test-keys")
    }

    private fun checkSuBinary(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun checkSuperUserApk(): Boolean {
        return File("/system/app/Superuser.apk").exists()
    }
}