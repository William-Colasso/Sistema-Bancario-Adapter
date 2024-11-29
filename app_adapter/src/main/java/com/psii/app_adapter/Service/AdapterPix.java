package com.psii.app_adapter.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psii.app_adapter.Model.Pagamento;

@Service
public class AdapterPix implements Pagamento {

    private final SistemaPix sistemaPix;

    @Autowired
    public AdapterPix(SistemaPix sistemaPix) {
        this.sistemaPix = sistemaPix;  // Injetando SistemaPix gerenciado pelo Spring
    }
    
    @Override
    public String processarPagamento(double valor, String chavePix) {
        return sistemaPix.pagarPix(chavePix, valor);
    }
}