@file:Suppress("OverrideDeprecatedMigration")

package br.com.tcc.util.webclient.tasks

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import br.com.tcc.R
import br.com.tcc.controller.CallBack_Projeto
import br.com.tcc.model.ColetaProduto
import br.com.tcc.model.MensagemEnvio
import br.com.tcc.model.Pesquisa
import br.com.tcc.util.APIError
import br.com.tcc.util.CargaJson
import br.com.tcc.util.Loading
import br.com.tcc.util.database.Database
import br.com.tcc.util.webclient.EndPoint
import br.com.tcc.util.webclient.retrofit.Conexao
import com.google.gson.Gson

class TaskEnviarDados(context: Context, pesquisa: Pesquisa) : AsyncTask<String?, Void?, Boolean>() {

    @SuppressLint("StaticFieldLeak")
    private val mContext = context
    private var mMensagemAlerta: String = ""
    private val mPesquisa: Pesquisa
    private var mMensagemEnvio: MensagemEnvio? = null
    private var db = Database.getInstance(mContext)
    private val callback: CallBack_Projeto
    var mensagem: String = ""

    @Deprecated("Deprecated in Java")
    override fun onPreExecute() {
        super.onPreExecute()
        Loading.hide()
        Loading.show(mContext, mContext.resources.getString(R.string.sincronizando))
    }

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg strings: String?): Boolean {
        try {
            Thread.sleep(200)
            val mGson = Gson()
            var mPesquisaJson: Pesquisa? = null
            val mCargaJson = CargaJson()
            manipulaLoading(mContext, 0, mContext.resources.getString(R.string.enviando_dados), 1)
            /**
             * ENVIO DO JSON
             */
            val daoPesquisaJson = db.roomPesquisaDao
            mPesquisaJson = daoPesquisaJson.selectId(mPesquisa.id!!)

            if (mPesquisaJson == null) throw java.lang.RuntimeException(mContext.resources.getString(
                R.string.erro_pesquisa_json))

            val lista = daoPesquisaJson.selectP(mPesquisaJson.id) as ArrayList<ColetaProduto?>

            mPesquisaJson.setmLColetaProdutoJson(lista)

            //TESTE COMENTARIO
            mCargaJson.setPesquisa(mPesquisaJson)
            val json = mGson.toJson(mCargaJson).toString()
            Log.i("JSON_PESQUISA", json)

            //TESTANDO DOIS
            val request = Conexao(mContext).sendJson(EndPoint.url_salva_dados, json, mContext, mPesquisa.codUsuario!!)

            val response = request.execute()

            if (!response.isSuccessful) {
                mensagem = APIError.getError(response.code(), mContext)
                return false
            }

            if (response.body()!!.mMensagemEnvio == null) throw RuntimeException(mContext.resources.getString(
                R.string.erro_pesquisa_json))

            mMensagemEnvio = response.body()!!.mMensagemEnvio
            if (mMensagemEnvio!!.status == 0) throw RuntimeException(mMensagemEnvio!!.descricao)

            return true
        } catch (e: Exception) {
            mMensagemAlerta = e.toString()
        } finally {
            db!!.close()
        }
        Loading.hide()
        return false
    }

    @Deprecated("Deprecated in Java")
    override fun onPostExecute(aBoolean: Boolean) {
        super.onPostExecute(aBoolean)
        Loading.hide()
        callback.onRetorno(aBoolean, mMensagemAlerta)
    }

    companion object {
        /**
         * Manipula progress bar
         *
         * @param context
         * @param mensagem
         * @param opcao    1 para alterar o texto
         * 2 para alterar o progresso da barra
         * 3 para alterar o maximo de progresso da barra
         */
        fun manipulaLoading(context: Context, valor: Int, mensagem: String, opcao: Int) {
            (context as Activity).runOnUiThread {
                when (opcao) {
                    1 -> {
                        Loading.setText(mensagem.trim { it <= ' ' })
                    }
                    2 -> {
                        Loading.setProgressValue(valor)
                    }
                    3 -> {
                        Loading.setProgressMax(valor)
                    }
                }
            }
        }
    }

    init {
        mPesquisa = pesquisa
        callback = mContext as CallBack_Projeto
    }
}