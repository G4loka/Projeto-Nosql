package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

import java.time.LocalDate;

public class Noticia {

    // Criamos o Enum aqui dentro, sem precisar de um arquivo novo
    public enum Status {
        RASCUNHO("Rascunho"),
        PUBLICADA("Publicada"),
        REVISAO("Em Revisão"),
        INATIVA("Inativa");

        private final String descricao;

        Status(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private String id;
    private LocalDate dataCriacao;
    private String titulo;
    private Autor autor;
    private String conteudo;
    private Status status;
    private Assunto assunto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Assunto getAssunto() {
        return assunto;
    }

    public void setAssunto(Assunto assunto) {
        this.assunto = assunto;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusDescricao() {
        return status != null ? status.getDescricao() : "";
    }
}