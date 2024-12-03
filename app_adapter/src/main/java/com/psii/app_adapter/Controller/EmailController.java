package com.psii.app_adapter.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psii.app_adapter.Service.ClienteService;
import com.psii.app_adapter.Service.EmailService;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    
    @Autowired
    ClienteService clienteService;
    @PostMapping("/enviarEmail")
    public String enviarEmail(String email) {

        String nome = clienteService.findByEmail(email).isPresent() ? clienteService.findByEmail(email).get().getNome() : "Cliente";

        emailService.enviarEmail(email, "Reset de Senha", "Olá "+nome+" localhost:8080/resetSenha " );
        return "redirect:/home";
    }


    @GetMapping("/resetSenha")
    public String resetSenha(){
        return"/conta/resetSenha";
    }
}
