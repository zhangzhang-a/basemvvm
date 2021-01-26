package com.example.mykotlin.net


import android.util.Log
import com.example.mykotlin.app.Constants
import com.example.mykotlin.utlis.MyMmkv
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitFactory {
    /***
     * 伴生对象  单例
     */

    companion object {
        val instant: RetrofitFactory by lazy {
            RetrofitFactory()
        }
    }

    private val interceptor: Interceptor
    private val retrofit: Retrofit


    //初始化
    init {
        //通用拦截
        interceptor = Interceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("charset", "UTF-8")
                .addHeader("token", MyMmkv.getString(Constants.token))
                .build()
            chain.proceed(request)
        }

        //retrofit实例化
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(initClient())
            .build()
    }

    /**
     * okHttp创建
     */
    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(LoggingInterceptor())
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    /**
     *日志拦截器
     */
    class LoggingInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            var response = chain.proceed(request)
            var responseBody = response.peekBody(Long.MAX_VALUE)
            Log.e("tag", "拦截-->" + responseBody.string())
            Log.e("tag", "拦截到的url:-->" + request.url())

            return response
        }
    }


    /**
     * 具体服务实例化
     */

    fun <T> create(service: Class<T>): T {
        return retrofit.create(service)
    }

}