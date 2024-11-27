package com.psii.app_adapter.Service;

import org.springframework.stereotype.Service;
import com.psii.app_adapter.Model.Pagamento;

@Service
public class AdapterCartaoCredito implements Pagamento {
    private final SistemaCartaoCredito sistemaCartao = new SistemaCartaoCredito();
    
    @Override
    public void processarPagamento(double valor, String numeroCartao) {
        // sistemaCartao.pagarComCartao(numeroCartao, valor);
    }
}

class SistemaCartaoCredito {
    public void pagarComCartao(String numeroCartao, double valor) {
        // System.out.println("Pagamento de R$ " + valor + " realizado com o cartão: " + numeroCartao);
    }
}
