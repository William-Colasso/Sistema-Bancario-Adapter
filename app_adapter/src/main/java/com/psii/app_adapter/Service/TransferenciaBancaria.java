package com.psii.app_adapter.Service;

import org.springframework.stereotype.Service;
import com.psii.app_adapter.Model.Pagamento;

// Serviço nativo do Banco
@Service
public class TransferenciaBancaria implements Pagamento {
    @Override
    public void processarPagamento(double valor, String detalhes) {
        /*System.out.println("Pagamento via Transaferência Bancária:");
        System.out.println("Valor: R$ " + valor +  ", Conta Destino: " + detalhes);*/
    }
}
