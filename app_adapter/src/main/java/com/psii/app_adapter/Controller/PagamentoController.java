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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController // Garantir que todas as respostas são JSON
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

    @PostMapping("/processar")
    public ResponseEntity<Map<String, Object>> processarPagamento(
            @RequestParam double valor,
            @RequestParam String emailDestino,
            @RequestParam String tipoPagamento,
            @RequestParam String idUsuario) {

        Map<String, Object> response = new HashMap<>();

        // Busca o cliente pelo email
        Optional<Cliente> clienteOptional = clienteService.findByEmail(emailDestino);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();
            cliente.setSaldo(cliente.getSaldo() + valor);

            // Salvar o cliente atualizado
            clienteService.createCliente(cliente);

            // Configurando a resposta de sucesso
            response.put("success", true);
            response.put("message", "Pagamento processado com sucesso!");
            response.put("cliente", cliente);
        } else {
            // Configurando a resposta de erro
            response.put("success", false);
            response.put("message", "Cliente não encontrado!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List> listAllC() {

        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAllClientes());

    }

}
