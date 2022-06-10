package br.com.tcc.util;

import com.google.gson.annotations.SerializedName;

import br.com.tcc.model.Pesquisa;

public class CargaJson {

    @SerializedName("PESQUISA")
    Pesquisa pesquisa;

    public Pesquisa getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(Pesquisa pesquisa) {
        this.pesquisa = pesquisa;
    }
}
