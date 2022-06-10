package br.com.tcc.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eloi Brito de Jesus on 02/01/2018.
 */

public class Mensagem {

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
