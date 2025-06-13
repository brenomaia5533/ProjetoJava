package br.edu.unicid.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.edu.unicid.model.Leitor;
import br.edu.unicid.util.ConnectionFactory;

public class LeitorDAO {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private Leitor leitor;

	public LeitorDAO() throws Exception {
		// Chama a classe ConnectionFactory e estabelece uma conexão
		try {
			this.conn = ConnectionFactory.getConnection();
		} catch (Exception e) {
			throw new Exception("Erro: " + e.getMessage());
		}
	}

	// Método de salvar
	public void salvar(Leitor leitor) throws Exception {
		if (leitor == null) {
			throw new Exception("O valor passado não pode ser nulo");
		}
		try {
			String SQL = "INSERT INTO tbLeitor (codLeitor, nomeLeitor, tipoLeitor) VALUES (?, ?, ?)";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, leitor.getCodLeitor());
			ps.setString(2, leitor.getNomeLeitor());
			ps.setString(3, leitor.getTipoLeitor());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao inserir dados: " + sqle.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// Método de atualizar
	public void alterar(Leitor leitor) throws Exception {
		if (leitor == null) {
			throw new Exception("O valor passado não pode ser nulo");
		}
		try {
			String SQL = "UPDATE tbLeitor SET nomeLeitor = ?, tipoLeitor = ? WHERE codLeitor = ?";
			ps = conn.prepareStatement(SQL);
			ps.setString(1, leitor.getNomeLeitor());
			ps.setString(2, leitor.getTipoLeitor());
			ps.setInt(3, leitor.getCodLeitor());
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao alterar dados: " + sqle.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// Método de excluir
	public void excluir(int codLeitor) throws Exception {
		try {
			String SQL = "DELETE FROM tbLeitor WHERE codLeitor = ?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, codLeitor);
			ps.executeUpdate();
		} catch (SQLException sqle) {
			throw new Exception("Erro ao excluir dados: " + sqle.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, ps);
		}
	}

	// Consultar Leitor
	public Leitor consultar(int codLeitor) throws Exception {
		try {
			String SQL = "SELECT * FROM tbLeitor WHERE codLeitor = ?";
			ps = conn.prepareStatement(SQL);
			ps.setInt(1, codLeitor);
			rs = ps.executeQuery();
			if (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				String tipo = rs.getString(3);
				leitor = new Leitor(codigo, nome, tipo);
			}
			return leitor;
		} catch (SQLException sqle) {
			throw new Exception("Erro ao consultar dados: " + sqle.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}

	// Listar todos
	public List<Leitor> listarTodos() throws Exception {
		try {
			ps = conn.prepareStatement("SELECT * FROM tbLeitor");
			rs = ps.executeQuery();
			List<Leitor> list = new ArrayList<>();
			while (rs.next()) {
				int codLeitor = rs.getInt("codLeitor");
				String nomeLeitor = rs.getString("nomeLeitor");
				String tipoLeitor = rs.getString("tipoLeitor");
				list.add(new Leitor(codLeitor, nomeLeitor, tipoLeitor));
			}
			return list;
		} catch (SQLException sqle) {
			throw new Exception("Erro ao listar dados: " + sqle.getMessage());
		} finally {
			ConnectionFactory.closeConnection(conn, ps, rs);
		}
	}
}