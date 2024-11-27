package com.psii.app_adapter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import com.psii.app_adapter.Model.Pagamento;
import com.psii.app_adapter.Service.AdapterCartaoCredito;
import com.psii.app_adapter.Service.PagamentoBoleto;
import com.psii.app_adapter.Service.TransferenciaBancaria;

@SpringBootApplication
public class AppAdapterApplication implements CommandLineRunner {

    @Autowired
    private TransferenciaBancaria transferenciaBancaria;
    @Autowired
    private PagamentoBoleto pagamentoBoleto;
    @Autowired
    private AdapterCartaoCredito adapterCartaoCredito;

    public static void main(String[] args) {
        SpringApplication.run(AppAdapterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Pagamento transferencia = transferenciaBancaria;
        transferencia.processarPagamento(100.00, "123456789");
        
        Pagamento boleto = pagamentoBoleto;
        boleto.processarPagamento(200.00, "1234-5678-9000");
        
        Pagamento cartao = adapterCartaoCredito;
        cartao.processarPagamento(300.00, "1234-5678-9012-3456");
    }
}