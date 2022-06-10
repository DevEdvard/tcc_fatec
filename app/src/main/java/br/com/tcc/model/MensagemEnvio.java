package br.com.tcc.model;

import java.io.Serializable;

public class MensagemEnvio implements Serializable {
    private Integer status;
    private String descricao;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
