package br.com.tcc.util.webclient.retrofit.interface_client;

import br.com.tcc.model.MensagemJson;

public interface OnCallBackJustificativa {

    @Deprecated
    void onRetorno();

    @Deprecated
    void onRetorno(boolean abolean, MensagemJson mMensagemJson);

}
