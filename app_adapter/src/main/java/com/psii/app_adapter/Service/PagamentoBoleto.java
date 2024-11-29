package com.psii.app_adapter.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Model.Pagamento;

// Serviço Nativo do banco
@Service
public class PagamentoBoleto implements Pagamento {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private BoletoService boletoService;

    @Override
    public String processarPagamento(double valor, String codigo) {
        Optional<Boleto> boletoOptional = boletoService.getBoletoById(codigo);

        String erro = "";

        if (boletoOptional.isPresent()) {
            Boleto boleto = boletoOptional.get();

            Optional<Cliente> clienteDestinoOptional = clienteService.getClienteById(boleto.getIdCliente());

            if ((boleto.getValor() == valor && !boleto.isPago()) && clienteDestinoOptional.isPresent()) {
                boleto.setPago(true);
                Cliente clienteDestino = clienteDestinoOptional.get();

                clienteDestino.setSaldo(clienteDestino.getSaldo() + valor);
                return "GG";
            } else {

                if (boleto.getValor() != valor) {
                    erro += "{Valores não coincidem}";
                }

                if (boleto.isPago()) {
                    erro += "{O boleto já está pago}";
                }
                if (clienteDestinoOptional.isEmpty()) {
                    erro += "{O Cliente não existe}";
                }

                return "Houve um erro no pagamento do boleto, erro: " + erro;
            }
        }
        return "O boleto não existe!";
    }
}
