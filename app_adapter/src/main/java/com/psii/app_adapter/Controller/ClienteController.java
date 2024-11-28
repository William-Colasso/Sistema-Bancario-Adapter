package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.psii.app_adapter.Model.Cliente;

import com.psii.app_adapter.Service.ClienteService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String getClientes(Model model) {
        return "geral";
    }

    @GetMapping("/home")
    public String getHome(Model model) {
        return "banco/paginaPrincipal";
    }

    @PostMapping("/clientes")
    public String postClientes(Cliente cliente) {

        clienteService.createCliente(cliente);
        return "redirect:/home";
    }

    @GetMapping("/cadastro")
    public String getCadastro(Model model) {

        Cliente cliente = new Cliente();

        model.addAttribute("cliente", cliente);

        return "conta/cadastro";
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestParam String identificador, @RequestParam String senha) {
    Cliente clienteResposta = null;

    List<Cliente> clientes = clienteService.getAllClientes();

    for (Cliente cliente : clientes) {
        if (cliente.getEmail().equals(identificador) && cliente.getSenha().equals(senha)) {
            clienteResposta = cliente;
            break;
        }
    }

    if (clienteResposta == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
    }

    return ResponseEntity.ok(clienteResposta);
}





    @GetMapping("/pagamento/pagar")
    public String getPagamento(Model model) {
        return "/banco/pagar"; // Retorna a página do formulário de pagamento
    }
}
