package com.psii.app_adapter.Service;

import org.springframework.stereotype.Service;
import com.psii.app_adapter.Model.Pagamento;

@Service
public class AdapterPix implements Pagamento {
    private final SistemaPix sistemaPix = new SistemaPix();
    
    @Override
    public void processarPagamento(double valor, String chavePix) {
        sistemaPix.pagarPix(chavePix, valor);
    }
}
