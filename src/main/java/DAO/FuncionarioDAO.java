package DAO;

import classes.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    private static List<Funcionario> lista = new ArrayList<>();

    public void inserir(Funcionario f) {
        lista.add(f);
    }

    public List<Funcionario> listar() {
        return lista;
    }
}