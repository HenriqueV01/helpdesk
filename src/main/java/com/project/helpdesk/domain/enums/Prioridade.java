package com.project.helpdesk.domain.enums;

public enum Prioridade {

    BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

    private Integer codigo;
    private String descrição;

    Prioridade(Integer codigo, String descrição) {
        this.codigo = codigo;
        this.descrição = descrição;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getDescrição() {
        return descrição;
    }

    public static Prioridade toEnum(Integer cod){
        if(cod == null){
            return null;
        }

        for(Prioridade p: Prioridade.values()){
            if(cod.equals(p.getCodigo())){
                return p;
            }
        }

        throw new IllegalArgumentException("Prioridade inválido!");
    }
}
