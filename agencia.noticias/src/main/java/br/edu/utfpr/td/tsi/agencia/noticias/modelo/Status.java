package br.edu.utfpr.td.tsi.agencia.noticias.modelo;

public enum Status {
    PD("Em produção"),
    PB("Publicada"),
    C("Cancelada");

    private final String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Status fromCodigo(String codigo) {
        for (Status s : values()) {
            if (s.name().equalsIgnoreCase(codigo)) {
                return s;
            }
        }
        return null;
    }
}
