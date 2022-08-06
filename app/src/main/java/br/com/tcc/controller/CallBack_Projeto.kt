package br.com.tcc.controller

interface CallBack_Projeto {
    fun onRetorno(aBoolean : Boolean, mensagem : String)
    fun onRetorno(aBoolean : Boolean)
    fun onRetorno(posicao : Int)
    fun onRetorno()
}