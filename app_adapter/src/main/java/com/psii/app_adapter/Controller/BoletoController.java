package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.psii.app_adapter.Model.Boleto;
import com.psii.app_adapter.Service.BoletoService;

import java.util.List;

@Controller
public class BoletoController {

    @Autowired
    private BoletoService boletoService;

    // Lista todos os boletos
    @GetMapping("/boletos")
    public String listarBoletos(Model model){
        List<Boleto> boletos = boletoService.getAllBoletos();
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
        return "redirect:/boletos"; // Redireciona de volta para a lista
    }


    @PostMapping("/boletos/deletar/{id}")
    public String deletarBoleto(@PathVariable String id){


        boletoService.deleteBoleto(id);


        return "redirect:/boletos";
    }

}
