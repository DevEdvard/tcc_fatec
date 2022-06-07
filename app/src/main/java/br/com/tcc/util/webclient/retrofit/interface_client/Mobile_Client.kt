package br.com.tcc.util.webclient.retrofit.interface_client

import br.com.tcc.model.DadosJson
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface Mobile_Client {
    @FormUrlEncoded
    @POST
    fun post(@Url url: String, @FieldMap data: HashMap<String, String>): Call<DadosJson>

    @POST
    @Streaming
    fun post(@Url url: String): Call<ResponseBody>

    @POST
    fun sendFile(
        @Url url: String, @Body files: RequestBody,
    ): Call<DadosJson>

    @FormUrlEncoded
    @POST
    fun downloadFileUrl(
        @Url url: String,
        @Field("nomeArquivo") nomeArquivo: String,
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST
    fun sendJson(@Url url: String, @Field("dadosJson") json: String): Call<DadosJson>
}