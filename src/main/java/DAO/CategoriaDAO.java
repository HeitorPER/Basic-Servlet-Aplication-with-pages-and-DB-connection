package DAO;

import classes.Categoria;
import classes.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CategoriaDAO {

    public void inserir(Categoria c) throws Exception {
        String sqlInserirCategoria = "INSERT INTO categoria (nome, prioridade) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection()) {
            garantirEstrutura(conn);
            conn.setAutoCommit(false);

            try {
                validarNomeUnico(conn, c.getNome(), null);

                int categoriaId;
                try (PreparedStatement stmt = conn.prepareStatement(sqlInserirCategoria, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setString(1, c.getNome());
                    stmt.setString(2, c.getPrioridade());
                    stmt.executeUpdate();

                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new Exception("Nao foi possivel obter o ID gerado.");
                        }
                        categoriaId = keys.getInt(1);
                    }
                }

                inserirAssuntos(conn, categoriaId, c.getAssuntos());
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public void atualizar(Categoria c) throws Exception {
        String sqlAtualizarCategoria = "UPDATE categoria SET nome = ?, prioridade = ? WHERE id = ?";
        String sqlDeletarAssuntos = "DELETE FROM categoria_assunto WHERE categoria_id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            garantirEstrutura(conn);
            conn.setAutoCommit(false);

            try {
                validarNomeUnico(conn, c.getNome(), c.getId());

                try (PreparedStatement stmt = conn.prepareStatement(sqlAtualizarCategoria)) {
                    stmt.setString(1, c.getNome());
                    stmt.setString(2, c.getPrioridade());
                    stmt.setInt(3, c.getId());

                    if (stmt.executeUpdate() == 0) {
                        throw new Exception("Categoria nao encontrada para atualizacao.");
                    }
                }

                try (PreparedStatement stmt = conn.prepareStatement(sqlDeletarAssuntos)) {
                    stmt.setInt(1, c.getId());
                    stmt.executeUpdate();
                }

                inserirAssuntos(conn, c.getId(), c.getAssuntos());
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        }
    }

    public List<Categoria> listar() {
        String sql = "SELECT c.id, c.nome, c.prioridade, ca.assunto " +
                "FROM categoria c " +
                "LEFT JOIN categoria_assunto ca ON ca.categoria_id = c.id " +
                "ORDER BY c.id, ca.id";

        Map<Integer, Categoria> mapa = new LinkedHashMap<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = prepararListagemComEstrutura(conn, sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Categoria categoria = mapa.get(id);

                if (categoria == null) {
                    categoria = new Categoria();
                    categoria.setId(id);
                    categoria.setNome(rs.getString("nome"));
                    categoria.setPrioridade(rs.getString("prioridade"));
                    categoria.setAssuntos(new ArrayList<>());
                    mapa.put(id, categoria);
                }

                String assunto = rs.getString("assunto");
                if (assunto != null) {
                    categoria.getAssuntos().add(assunto);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar categorias: " + mensagemRaiz(e), e);
        }

        return new ArrayList<>(mapa.values());
    }

    public Categoria buscarPorId(int id) {
        String sqlCategoria = "SELECT id, nome, prioridade FROM categoria WHERE id = ?";
        String sqlAssuntos = "SELECT assunto FROM categoria_assunto WHERE categoria_id = ? ORDER BY id";

        try (Connection conn = ConnectionFactory.getConnection()) {
            garantirEstrutura(conn);
            Categoria categoria;

            try (PreparedStatement stmt = conn.prepareStatement(sqlCategoria)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        return null;
                    }

                    categoria = new Categoria();
                    categoria.setId(rs.getInt("id"));
                    categoria.setNome(rs.getString("nome"));
                    categoria.setPrioridade(rs.getString("prioridade"));
                    categoria.setAssuntos(new ArrayList<>());
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlAssuntos)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        categoria.getAssuntos().add(rs.getString("assunto"));
                    }
                }
            }

            return categoria;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar categoria por ID: " + mensagemRaiz(e), e);
        }
    }

    public void deletar(int id) {
        String sqlDeletarAssuntos = "DELETE FROM categoria_assunto WHERE categoria_id = ?";
        String sqlDeletarCategoria = "DELETE FROM categoria WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {
            garantirEstrutura(conn);
            conn.setAutoCommit(false);

            try (PreparedStatement stmtAssuntos = conn.prepareStatement(sqlDeletarAssuntos);
                 PreparedStatement stmtCategoria = conn.prepareStatement(sqlDeletarCategoria)) {
                stmtAssuntos.setInt(1, id);
                stmtAssuntos.executeUpdate();

                stmtCategoria.setInt(1, id);
                stmtCategoria.executeUpdate();

                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar categoria: " + mensagemRaiz(e), e);
        }
    }

    private void inserirAssuntos(Connection conn, int categoriaId, List<String> assuntos) throws SQLException {
        if (assuntos == null || assuntos.isEmpty()) {
            return;
        }

        String sql = "INSERT INTO categoria_assunto (categoria_id, assunto) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            for (String assunto : assuntos) {
                stmt.setInt(1, categoriaId);
                stmt.setString(2, assunto);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }

    private void validarNomeUnico(Connection conn, String nome, Integer idIgnorado) throws Exception {
        String sqlSemId = "SELECT COUNT(*) FROM categoria WHERE LOWER(nome) = LOWER(?)";
        String sqlComId = "SELECT COUNT(*) FROM categoria WHERE LOWER(nome) = LOWER(?) AND id <> ?";

        try (PreparedStatement stmt = conn.prepareStatement(idIgnorado == null ? sqlSemId : sqlComId)) {
            stmt.setString(1, nome);
            if (idIgnorado != null) {
                stmt.setInt(2, idIgnorado);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new Exception("Nome ja existe!");
                }
            }
        }
    }

    private PreparedStatement prepararListagemComEstrutura(Connection conn, String sql) throws SQLException {
        garantirEstrutura(conn);
        return conn.prepareStatement(sql);
    }

    private void garantirEstrutura(Connection conn) throws SQLException {
        String sqlCategoria = "CREATE TABLE IF NOT EXISTS categoria (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "nome VARCHAR(100) NOT NULL UNIQUE, " +
                "prioridade VARCHAR(20) NOT NULL" +
                ")";

        String sqlCategoriaAssunto = "CREATE TABLE IF NOT EXISTS categoria_assunto (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "categoria_id INT NOT NULL, " +
                "assunto VARCHAR(50) NOT NULL, " +
                "CONSTRAINT fk_categoria_assunto " +
                "FOREIGN KEY (categoria_id) REFERENCES categoria(id) ON DELETE CASCADE" +
                ")";

        try (Statement st = conn.createStatement()) {
            st.execute(sqlCategoria);
            st.execute(sqlCategoriaAssunto);
        }
    }

    private String mensagemRaiz(Throwable e) {
        Throwable atual = e;
        while (atual.getCause() != null) {
            atual = atual.getCause();
        }
        return atual.getMessage();
    }
}
