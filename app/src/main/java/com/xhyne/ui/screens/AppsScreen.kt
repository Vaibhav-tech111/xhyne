package com.xhyne.ui.screens

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class AppItem(
    val packageName: String,
    val appName: String,
    val appIcon: Drawable
)

@Composable
fun AppsScreen() {
    val context = LocalContext.current
    val packageManager = context.packageManager

    var installedApps by remember { mutableStateOf<List<AppItem>?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .mapNotNull { appInfo ->
                    try {
                        AppItem(
                            packageName = appInfo.packageName,
                            appName = packageManager.getApplicationLabel(appInfo).toString(),
                            appIcon = packageManager.getApplicationIcon(appInfo)
                        )
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }
                }
                .sortedBy { it.appName.lowercase() }

            withContext(Dispatchers.Main) {
                installedApps = apps
                isLoading = false
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (installedApps != null && installedApps!!.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(installedApps!!) { app ->
                    AppItemRow(app)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No applications found or an error occurred.")
            }
        }
    }
}

@Composable
fun AppItemRow(app: AppItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            bitmap = app.appIcon.toBitmap(width = 96, height = 96).asImageBitmap(),
            contentDescription = "${app.appName} icon",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
        Text(text = app.appName, style = MaterialTheme.typography.bodyLarge)
    }
}