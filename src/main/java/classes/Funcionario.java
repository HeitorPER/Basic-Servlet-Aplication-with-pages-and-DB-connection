package classes;

import java.util.List;

public class Funcionario {

    private String nome;
    private String cargo;
    private List<String> tecnologias;

    public Funcionario(String nome, String cargo, List<String> tecnologias) {
        this.nome = nome;
        this.cargo = cargo;
        this.tecnologias = tecnologias;
    }

    public String getNome() { return nome; }
    public String getCargo() { return cargo; }
    public List<String> getTecnologias() { return tecnologias; }
}
