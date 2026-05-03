package classes;

import java.util.List;

public class Categoria {

    private int id;
    private String nome;
    private String prioridade;
    private List<String> assuntos;

    public Categoria() {}

    public Categoria(String nome, String prioridade, List<String> assuntos) {
        this.nome = nome;
        this.prioridade = prioridade;
        this.assuntos = assuntos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public List<String> getAssuntos() { return assuntos; }
    public void setAssuntos(List<String> assuntos) { this.assuntos = assuntos; }
}
