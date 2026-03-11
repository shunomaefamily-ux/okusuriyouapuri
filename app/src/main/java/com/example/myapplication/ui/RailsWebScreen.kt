package com.example.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun RailsWebScreen(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.databaseEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false

                webViewClient = WebViewClient()

                webChromeClient = object : WebChromeClient() {
                    override fun onPermissionRequest(request: PermissionRequest) {
                        post {
                            val hasCameraPermission =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                            if (!hasCameraPermission) {
                                request.deny()
                                return@post
                            }

                            val grants = request.resources.filter {
                                it == PermissionRequest.RESOURCE_VIDEO_CAPTURE
                            }

                            if (grants.isNotEmpty()) {
                                request.grant(grants.toTypedArray())
                            } else {
                                request.deny()
                            }
                        }
                    }
                }

                loadUrl(url)
            }
        }
    )
}