package org.mixitconf.api.interceptor

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.mixitconf.App
import org.mixitconf.HttpStatus

class NetworkConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (!isInternetAvailable(App.instance)) {
            return Response.Builder()
                .request(request)
                .code(HttpStatus.NO_INTERNET)
                .protocol(Protocol.HTTP_2)
                .body("No internet connection".toResponseBody())
                .message("No internet connection").build()
        }
        return chain.proceed(request)
    }

    private fun isInternetAvailable(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        return cm?.activeNetworkInfo?.isConnected == true
    }
}