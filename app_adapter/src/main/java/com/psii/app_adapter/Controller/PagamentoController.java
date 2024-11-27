package com.psii.app_adapter.Controller;

import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.PagamentoBoleto;
import com.psii.app_adapter.Service.TransferenciaBancaria;
import com.psii.app_adapter.Service.AdapterCartaoCredito;
import com.psii.app_adapter.Service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private AdapterCartaoCredito adapterCartaoCredito;

    @Autowired
    private PagamentoBoleto pagamentoBoleto;

    @Autowired
    private TransferenciaBancaria transferenciaBancaria;

    @Autowired
    private ClienteService clienteService; // Repositório do MongoDB

    @GetMapping("/pagar")
    public String getPagamento(Model model) {
        return "/banco/pagar"; // Retorna a página do formulário de pagamento
    }

    @PostMapping("/processar")
    public ResponseEntity<String> processarPagamento(
            @RequestParam double valor,
            @RequestParam String emailDestino,
            @RequestParam String tipoPagamento,
            @RequestParam String numeroPagamento) {

        // Busca o cliente pelo email
        Optional<Cliente> clienteOptional = clienteService.findByEmail(emailDestino);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            // Verifica qual tipo de pagamento foi escolhido
            if (tipoPagamento.equals("cartao")) {
                adapterCartaoCredito.processarPagamento(valor, numeroPagamento);
            } else if (tipoPagamento.equals("boleto")) {
                pagamentoBoleto.processarPagamento(valor, numeroPagamento);
            } else if (tipoPagamento.equals("transferencia")) {
                transferenciaBancaria.processarPagamento(valor, numeroPagamento);
            }

            // Atualiza o saldo do cliente
            double novoSaldo = cliente.getSaldo() - valor;
            cliente.setSaldo(novoSaldo);
            clienteService.createCliente(cliente); // Salva as alterações no MongoDB

            // Retorna a resposta
            return new ResponseEntity<>("Pagamento de R$ " + valor + " realizado com sucesso. Saldo atualizado para R$ " + novoSaldo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cliente não encontrado com o email: " + emailDestino, HttpStatus.NOT_FOUND);
        }
    }
}
