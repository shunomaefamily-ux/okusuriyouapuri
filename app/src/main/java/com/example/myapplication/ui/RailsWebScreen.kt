package com.example.myapplication.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat

private const val TAG = "RailsWebScreen"

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
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                settings.loadsImagesAutomatically = true
                settings.allowFileAccess = false
                settings.allowContentAccess = true

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        Log.d(TAG, "onPageFinished: $url")
                    }
                }

                webChromeClient = object : WebChromeClient() {
                    override fun onPermissionRequest(request: PermissionRequest) {
                        post {
                            Log.d(TAG, "onPermissionRequest: ${request.resources.joinToString()}")
                            Log.d(TAG, "permission origin: ${request.origin}")

                            val hasCameraPermission =
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                            if (!hasCameraPermission) {
                                Log.d(TAG, "CAMERA permission denied on Android side")
                                request.deny()
                                return@post
                            }

                            val grants = request.resources.filter {
                                it == PermissionRequest.RESOURCE_VIDEO_CAPTURE
                            }

                            if (grants.isNotEmpty()) {
                                Log.d(TAG, "Granting: ${grants.joinToString()}")
                                request.grant(grants.toTypedArray())
                            } else {
                                Log.d(TAG, "No supported resources requested")
                                request.deny()
                            }
                        }
                    }
                }

                Log.d(TAG, "loadUrl(factory): $url")
                loadUrl(url)
            }
        },
        update = { webView ->
            if (webView.url != url) {
                Log.d(TAG, "loadUrl(update): $url")
                webView.loadUrl(url)
            }
        }
    )
}