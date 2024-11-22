package com.psii.app_adapter.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Document(collection = "transacoes")
public class Transacao {
    @Id
    private String id;

    private LocalDateTime dataHora;

    private TipoTransacao tipo;

    private double valor;

    @DBRef
    private Conta conta;

    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public TipoTransacao getTipo() { return tipo; }
    public void setTipo(TipoTransacao tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
}
