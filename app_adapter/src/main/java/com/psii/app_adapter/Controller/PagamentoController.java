package com.psii.app_adapter.Controller;

import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.PagamentoBoleto;
import com.psii.app_adapter.Service.TransferenciaBancaria;
import com.psii.app_adapter.Service.AdapterPix;  // Usando o Adapter como temporário para "Pix"
import com.psii.app_adapter.Service.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private AdapterPix adapterPix; // Adapter para Cartão (atualmente substituindo o Pix)

    @Autowired
    private PagamentoBoleto pagamentoBoleto;

    @Autowired
    private TransferenciaBancaria transferenciaBancaria;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/processar")
    public ResponseEntity<Map<String, Object>> processarPagamento(
            @RequestParam double valor,
            @RequestParam String emailDestino,
            @RequestParam String tipoPagamento) {

        Map<String, Object> response = new HashMap<>();

        // Busca o cliente pelo email
        Optional<Cliente> clienteOptional = clienteService.findByEmail(emailDestino);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            // Verificação do tipo de pagamento
            if (tipoPagamento.equalsIgnoreCase("pix")) {
                // Chamar o método sem esperar retorno
                adapterPix.processarPagamento(valor, cliente.getEmail());
            } else if (tipoPagamento.equalsIgnoreCase("boleto")) {
                pagamentoBoleto.processarPagamento(valor, cliente.getEmail());
            } else if (tipoPagamento.equalsIgnoreCase("transferencia")) {
                transferenciaBancaria.processarPagamento(valor, cliente.getEmail());
            } else {
                response.put("success", false);
                response.put("message", "Tipo de pagamento inválido!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Atualizar o saldo do cliente após o processamento
            cliente.setSaldo(cliente.getSaldo() + valor);
            clienteService.createCliente(cliente);

            // Configurando a resposta de sucesso
            response.put("success", true);
            response.put("message", String.format("Pagamento de R$ %.2f processado para o cliente com email '%s'.", valor, emailDestino));
            response.put("cliente", cliente);

        } else {
            // Configurando a resposta de erro caso o cliente não seja encontrado
            response.put("success", false);
            response.put("message", "Cliente não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/getAll")
    public ResponseEntity<List<Cliente>> listAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAllClientes());
    }
}
