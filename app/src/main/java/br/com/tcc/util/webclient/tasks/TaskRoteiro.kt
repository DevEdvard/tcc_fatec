package br.com.tcc.util.webclient.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import br.com.tcc.R
import br.com.tcc.controller.CallBack_Projeto
import br.com.tcc.util.APIError
import br.com.tcc.util.FormataData
import br.com.tcc.util.Loading
import br.com.tcc.util.Util
import br.com.tcc.util.database.Database
import br.com.tcc.util.webclient.EndPoint
import br.com.tcc.util.webclient.retrofit.Conexao

class TaskRoteiro
constructor(context: Context) : AsyncTask<String, Void, Boolean>() {

    var callback: CallBack_Projeto
    var context: Context
    var mensagem: String = ""

    init {
        callback = context as CallBack_Projeto
        this.context = context
    }

    override fun onPreExecute() {
        super.onPreExecute()
        Loading.hide()
        Loading.show(context, context.resources.getString(R.string.login_loading_validando))
    }

    @SuppressLint("StringFormatInvalid")
    override fun doInBackground(vararg params: String?): Boolean {

        try {
            Thread.sleep(200)

            val request = Conexao(this.context).get(
                EndPoint.url_roteiro,
                Util.retornaHashJson(
                    "=",
                    String.format("login=%s", params[0]),
                    String.format("senha=%s", params[1]),
                    String.format("data_celular=%s",
                        FormataData.retornaDataFormat(FormataData.SQLData))
                )
            )

            val response = request.execute()

            if (!response.isSuccessful) {
                mensagem = APIError.getError(response.code(), context)
                return false
            }

            val mensagemJson = response.body()!!.mMensagem!!

            if (mensagemJson.status != 1) {
                mensagem = mensagemJson.mensagem!!
                return false
            }

            val login = response.body()!!.mLogin
            val roteiro = response.body()!!.mRoteiro
            val produtos = response.body()!!.mProduto

            val db = Database.getInstance(context)
            val daoLogin = db.roomUsuarioDao
            val daoRoteiro = db.roomRoteiroDao
            val daoProduto = db.roomSkuDao
            val daoPesquisa = db.roomPesquisaDao
            val daoColetaSku = db.roomColetaProdutoDao
            val daoColeta = db.roomColetaDao

            daoPesquisa.deleteAll()
            daoColetaSku.deleteAll()
            daoColeta.deleteAll()

            if (login != null) {
                daoLogin.deleteAll()
                daoLogin.insert(login)
            }

            if (roteiro.isNotEmpty()) {
                daoRoteiro.deleteAll()
                daoRoteiro.insert(roteiro)
            }

            if (produtos.isNotEmpty()) {
                daoProduto.deleteAll()
                daoProduto.insert(produtos)
            }

            db.close()

            return true
        } catch (err: Exception) {
            erro()
        }

        return false
    }

    override fun onPostExecute(result: Boolean) {
        super.onPostExecute(result)
        Loading.hide()
        callback.onRetorno(result, mensagem)
    }

    private fun erro() {
        try {
            val db = Database.getInstance(context)
            val daoLogin = db.roomUsuarioDao
            val daoRoteiro = db.roomRoteiroDao
            val daoProduto = db.roomSkuDao
            daoLogin.deleteAll()
            daoRoteiro.deleteAll()
            daoProduto.deleteAll()
            db.close()
        } catch (err: Exception) {

        }
    }
}