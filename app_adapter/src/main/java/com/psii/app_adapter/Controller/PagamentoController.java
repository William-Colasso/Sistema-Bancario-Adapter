package com.psii.app_adapter.Controller;

import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.PagamentoBoleto;
import com.psii.app_adapter.Service.TransferenciaBancaria;
import com.psii.app_adapter.Service.AdapterPix; // Usando o Adapter como temporário para "Pix"
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
    private AdapterPix adapterPix;

    @Autowired
    private PagamentoBoleto pagamentoBoleto;

    @Autowired
    private TransferenciaBancaria transferenciaBancaria;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/processar")
    public ResponseEntity<Map<String, Object>> processarPagamento(
            @RequestParam double valor,
            @RequestParam String campoDinamico, // Email do cliente que vai receber o pagamento
            @RequestParam String tipoPagamento, // Tipo de pagamento: pix, boleto, transferência
            @RequestParam String idUsuario) { // Email do cliente que está fazendo o pagamento (não será mais
                                                // necessário)


                

        Map<String, Object> response = new HashMap<>();

        if(valor<=0){
            response.put("message", "Valor inválido!");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Busca o cliente de destino pelo email
        Optional<Cliente> clienteDestinoOptional = clienteService.findByEmail(campoDinamico);

        // Busca o cliente de origem pelo idUsuario (não usa mais o emailOrigem)
        Optional<Cliente> clienteOrigemOptional = clienteService.getClienteById(idUsuario); // Ajustado para buscar por
                                                                                      // idUsuario

        if (clienteDestinoOptional.isPresent() && clienteOrigemOptional.isPresent()) {
            Cliente clienteDestino = clienteDestinoOptional.get();
            Cliente clienteOrigem = clienteOrigemOptional.get();

            // Verificação se o cliente de origem tem saldo suficiente para o pagamento
            if (clienteOrigem.getSaldo() < valor) {
                response.put("success", false);
                response.put("message", "Saldo insuficiente para realizar o pagamento!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Verificação do tipo de pagamento
            if (tipoPagamento.equalsIgnoreCase("pix")) {
                // Processa pagamento via PIX
                adapterPix.processarPagamento(valor, clienteDestino.getEmail());
            } else if (tipoPagamento.equalsIgnoreCase("boleto")) {
                // Processa pagamento via Boleto
                pagamentoBoleto.processarPagamento(valor, clienteDestino.getEmail());
            } else if (tipoPagamento.equalsIgnoreCase("transferencia")) {
                // Processa pagamento via Transferência bancária
                transferenciaBancaria.processarPagamento(valor, clienteDestino.getEmail());
            } else {
                response.put("success", false);
                response.put("message", "Tipo de pagamento inválido!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // Atualizar os saldos:
            // Adiciona o valor no saldo do cliente de destino e desconta do saldo do
            // cliente de origem
            clienteDestino.setSaldo(clienteDestino.getSaldo() + valor);
            clienteOrigem.setSaldo(clienteOrigem.getSaldo() - valor);

            // Salvar as mudanças nos dois clientes no banco de dados
            clienteService.createCliente(clienteDestino); // Atualiza o saldo do cliente de destino
            clienteService.createCliente(clienteOrigem); // Atualiza o saldo do cliente de origem

            // Configuração da resposta de sucesso
            response.put("success", true);
            response.put("message",
                    String.format("Pagamento de R$ %.2f processado de '%s' para o cliente com o nome '%s'.",
                            valor, clienteOrigem.getNome(), clienteDestino.getNome()));
            response.put("clienteDestino", clienteDestino);
            response.put("clienteOrigem", clienteOrigem);

        } else {
            // Caso não encontre os clientes, retorna erro
            response.put("success", false);
            response.put("message", "Cliente(s) não encontrado(s)!");
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Cliente>> listAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAllClientes());
    }
}
