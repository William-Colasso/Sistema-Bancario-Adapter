package com.psii.app_adapter.Controller;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.PagamentoBoleto;
import com.psii.app_adapter.Service.TransferenciaBancaria;
import com.psii.app_adapter.Service.AdapterPix; // Usando o Adapter como temporário para "Pix"
import com.psii.app_adapter.Service.ClienteService;
import com.psii.app_adapter.Service.BoletoService;

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
    private BoletoService boletoService;

    @Autowired
    private PagamentoBoleto pagamentoBoleto;

    @Autowired
    private TransferenciaBancaria transferenciaBancaria;

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/processar")
    public ResponseEntity<Map<String, Object>> processarPagamento(
            @RequestParam double valor,
            @RequestParam String campoDinamico, // Email ou identificador do destinatário
            @RequestParam String tipoPagamento, // Tipo de pagamento: PIX, BOLETO, TRANSFERENCIA
            @RequestParam String idUsuario) { // ID ou email do cliente que realiza o pagamento

        Map<String, Object> response = new HashMap<>();

        Boleto boleto = new Boleto();

        if (tipoPagamento.equals("BOLETO"'') ) {
            boleto = boletoService.getBoletoById(campoDinamico).get();
            valor = boleto.getValor();
            System.out.println(valor);
        }

        // Validação do valor
        if (valor <= 0) {
            response.put("message", "Valor inválido!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Recuperar o cliente de origem
        Optional<Cliente> clienteOptional = clienteService.getClienteById(idUsuario);
        if (clienteOptional.isEmpty()) {
            response.put("message", "Usuário não encontrado.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Cliente clienteOrigem = clienteOptional.get();

        // Verificar saldo suficiente
        if (clienteOrigem.getSaldo() < valor) {
            response.put("message", "Saldo insuficiente.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Processar o pagamento com base no tipo
        String resultado;
        switch (tipoPagamento.toUpperCase()) {
            case "PIX":
                resultado = adapterPix.processarPagamento(valor, campoDinamico);
                response.put("message",
                        resultado.equals("GG") ? "Valor transferido com PIX!" : resultado);
                break;

            case "BOLETO":
                String codigoBoleto = campoDinamico.toString();
                resultado = pagamentoBoleto.processarPagamento(valor, codigoBoleto);
                response.put("message",
                        resultado.equals("GG") ? "Valor transferido com boleto!" : resultado);
                break;

            case "TRANSFERENCIA":
                resultado = transferenciaBancaria.processarPagamento(valor, campoDinamico);
                response.put("message", resultado.equals("GG") ? "Valor transferido via transferência bancária!"
                        : resultado);
                break;

            default:
                response.put("message", "Tipo de pagamento inválido.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Atualizar saldo do cliente após pagamento bem-sucedido

        if (resultado.equals("GG")) {
            clienteOrigem.setSaldo(clienteOrigem.getSaldo() - valor);
            clienteService.createCliente(clienteOrigem); // Presume-se que existe este método para salvar a atualização
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Cliente>> listAllClientes() {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.getAllClientes());
    }
}
