package br.com.alura.leilao.util.builder;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeilaoBuilder {

    private String nome;
    private BigDecimal valorInicial;
    private LocalDate data;
    private Usuario usuario;

    public LeilaoBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public LeilaoBuilder withValorInicial(String valor) {
        this.valorInicial = new BigDecimal(valor);
        return this;
    }

    public LeilaoBuilder withData(LocalDate data) {
        this.data = data;
        return this;
    }

    public LeilaoBuilder withUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Leilao build() {
        return new Leilao(nome, valorInicial, data, usuario);
    }
}
