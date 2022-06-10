package br.com.tcc.controller

interface CallBack_Projeto {
    fun onRetorno(aBoolean : Boolean, mensagem : String)
//    fun onRetorno_2(aBoolean: Boolean, mensagem: String)
//    fun onRetorno_3(aBoolean: Boolean, mensagem: String)
//    fun onRetorno_Roteiro(lista : ArrayList<Roteiro>)
//    fun onRetorno(vararg params : Int)
    fun onRetorno(posicao : Int)
//    fun onRetorno(posicao : Int, produto: Sku)
//    fun onRetorno(boolean : Boolean, posicao : Int)
    fun onRetorno()
//
//    fun onRetorno(aBolean: Boolean, data: String?, mensagem: String?)
//    fun onRetorno(aBolean: Boolean, data: String?, mensagem: String?, nomeArquivo: String?)
//    fun onRetornoFotoGrupo(aBolean: Boolean, data: String?, mensagem: String?)
}