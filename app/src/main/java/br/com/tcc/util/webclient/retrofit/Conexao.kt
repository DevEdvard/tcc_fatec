package br.com.tcc.util.webclient.retrofit

import android.content.Context
import br.com.tcc.util.webclient.retrofit.interface_client.Mobile_Client
import br.com.tcc.model.DadosJson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call

class Conexao(val context: Context) : Retrofit_Client() {


    fun get(url: String, data: HashMap<String, String>, context: Context): Call<DadosJson> {
        val dados = getClient(Mobile_Client::class.java) as Mobile_Client
        return dados.post(url, data)
    }

    fun get(url: String, data: HashMap<String, String>): Call<DadosJson> {
        val dados = getClient(Mobile_Client::class.java) as Mobile_Client
        return dados.post(url, data)
    }

    fun get(url: String): Call<ResponseBody> {
        val dados = getClient(Mobile_Client::class.java) as Mobile_Client
        return dados.post(url)
    }

    fun file(url: String, body: RequestBody): Call<DadosJson> {
        val dados = getClient(Mobile_Client::class.java) as Mobile_Client
        return dados.sendFile(url, body)
    }

    fun download(url: String, nome_arquivo: String): Call<ResponseBody> {
        val dados = getClient(Mobile_Client::class.java) as Mobile_Client
        return dados.downloadFileUrl(url, nome_arquivo)
    }

    @Throws(Exception::class)
    fun sendFile(url: String?, body: RequestBody?): Call<DadosJson> {
        val dadosMobileClient: Mobile_Client = getClient(
            Mobile_Client::class.java
        ) as Mobile_Client
        return dadosMobileClient.sendFile(url!!, body!!)

    }

    @Throws(Exception::class)
    fun sendJson(url: String, json: String, context: Context): Call<DadosJson> {
        val dadosMobileClient = getClient(
            Mobile_Client::class.java
        ) as Mobile_Client
        return dadosMobileClient.sendJson(url, json)
    }


}