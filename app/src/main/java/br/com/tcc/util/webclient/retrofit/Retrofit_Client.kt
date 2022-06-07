package br.com.tcc.util.webclient.retrofit

import br.com.tcc.util.webclient.EndPoint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

open class Retrofit_Client {

    private var mConexao: Retrofit? = null

    fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(50, TimeUnit.SECONDS)
            .writeTimeout(50, TimeUnit.SECONDS)
            .build()
    }

    fun getClient(service: Class<*>): Any {
        if (mConexao == null)
            mConexao = Retrofit.Builder()
                .baseUrl(EndPoint.url)
                .client(httpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return mConexao!!.create(service)
    }
}