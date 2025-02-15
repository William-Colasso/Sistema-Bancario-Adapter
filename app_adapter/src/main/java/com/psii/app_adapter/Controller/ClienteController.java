package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String postClientes(Cliente cliente, RedirectAttributes redirectAttributes) {

        if (clienteService.clienteJaCadastrado(cliente)) {
            redirectAttributes.addFlashAttribute("mapCliente", clienteService.findByAny(cliente.getCpf(), cliente.getTelefone(), cliente.getNome(), cliente.getEmail()));
            System.out.println("AJSDASDNAJLDNILA");
            return "redirect:/cadastro";
        } else {

            clienteService.createCliente(cliente);

        }
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

    @GetMapping("/meuPerfil")
    public String getPerfil(Model model) {
        return "/banco/meuPerfil"; // A página de perfil do cliente
    }

    @GetMapping("/perfil/{id}")
    public ResponseEntity<Cliente> getCliente(@PathVariable String id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping("/perfil")
    public ResponseEntity<Cliente> criarCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> cliOptional = clienteService.getClienteById(cliente.getId());

        if (cliOptional.isPresent()) {
            // Aqui você vai verificar qual chave Pix foi selecionada
            // Por exemplo, se a chave Pix for um CPF, e você recebeu uma chave CPF, você
            // pode atualizar a chavePix do cliente
            if (cliente.getChavesPix().contains(cliente.getCpf())) {
                // Marca a chave CPF, no front-end
            }
            if (cliente.getChavesPix().contains(cliente.getEmail())) {
                // Marca a chave Email, no front-end
            }
            if (cliente.getChavesPix().contains(cliente.getTelefone())) {
                // Marca a chave Telefone, no front-end
            }

            cliente.setSaldo(cliOptional.get().getSaldo());
        }

        Cliente clienteSalvo = clienteService.createCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteSalvo);
    }

    @GetMapping("/pagamento/pagar")
    public String getPagamento(Model model) {
        return "/banco/pagar"; // Retorna a página do formulário de pagamento
    }
}
