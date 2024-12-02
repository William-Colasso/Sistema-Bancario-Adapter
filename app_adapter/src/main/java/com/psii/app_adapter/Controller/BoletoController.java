package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Model.Cliente;
import com.psii.app_adapter.Service.BoletoService;
import com.psii.app_adapter.Service.ClienteService;
import java.util.Optional;
import java.util.List;

@Controller
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private ClienteService clienteService;
    // Lista todos os boletos
    @GetMapping("/boletos/{id}")
    public String listarBoletos(@PathVariable String id,Model model){

        Cliente cliente = clienteService.getClienteById(id).isPresent() ? clienteService.getClienteById(id).get() : new Cliente(); 


        List<Boleto> boletos = boletoService.findByIdCliente(cliente.getId());



        model.addAttribute("boletos", boletos);
        return "banco/boleto"; // Nome do arquivo Thymeleaf
    }

    // Cria um novo boleto
    @PostMapping("/boletos/novo")
    public String criarBoleto(@RequestParam double valor, @RequestParam String idCliente) {
        Boleto novoBoleto = new Boleto();
        novoBoleto.setValor(valor);
        novoBoleto.setIdCliente(idCliente);

        novoBoleto.setPago(false);
        boletoService.createBoleto(novoBoleto);

        StringBuilder response = new StringBuilder();

        response.append("redirect:/boletos/");
        response.append(idCliente);
        return response.toString(); // Redireciona de volta para a lista
    }


    @PostMapping("/boletos/deletar/")
    public String deletarBoleto(@RequestParam String id){
        
        String idCliente = boletoService.getBoletoById(id).get().getIdCliente();
        boletoService.deleteBoleto(id);


        return "redirect:/boletos/"+idCliente;
    }

}
