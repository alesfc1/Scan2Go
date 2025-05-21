package com.keru.pdfcreator.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.TimeUnit

fun Context.sharePdfFile(pdfUri: Uri) {
    val path = pdfUri.path
    path?.let {
        val externalUri = FileProvider.getUriForFile(this, "$packageName.provider", File(path))
        val shareIntent =
            Intent(Intent.ACTION_SEND).apply {
                putExtra(Intent.EXTRA_STREAM, externalUri)
                type = "application/pdf"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
        startActivity(Intent.createChooser(shareIntent, "Share PDF"))
    }
}

fun Long.getTimeAgo(): String {
    val currentTimeMillis = System.currentTimeMillis()
    val timeDifferenceMillis = currentTimeMillis - this

    val seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMillis)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifferenceMillis)
    val hours = TimeUnit.MILLISECONDS.toHours(timeDifferenceMillis)
    val days = TimeUnit.MILLISECONDS.toDays(timeDifferenceMillis)

    return when {
        seconds < 60 -> "$seconds seconds ago"
        minutes < 60 -> "$minutes minutes ago"
        hours < 24 -> "$hours hours ago"
        days == 1L -> "yesterday"
        days < 7 -> "$days days ago"
        else -> {
            SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(this)
        }
    }
}

fun Long.formatDate(): String{
    return SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(this)
}

fun Context.savePdfToDownloads(sourceUri: Uri, fileName: String): Boolean {
    // Check for storage permission
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (!Environment.isExternalStorageManager()) {
            // Request MANAGE_EXTERNAL_STORAGE permission
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", applicationContext.packageName))
                startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivity(intent)
            }
            return false
        }
    } else {
        // For Android 10 and below, request WRITE_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this as android.app.Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1001
            )
            return false
        }
    }

    return try {
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs()
        }

        val destinationFile = File(downloadsDir, "$fileName.pdf")
        
        // Handle content URIs (like those from FileProvider)
        val inputStream: InputStream? = contentResolver.openInputStream(sourceUri)
        val outputStream = FileOutputStream(destinationFile)
        
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        
        // Notify the system about the new file so it appears in Downloads
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        mediaScanIntent.data = Uri.fromFile(destinationFile)
        sendBroadcast(mediaScanIntent)
        
        Toast.makeText(this, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()
        
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}