package application;
// pacote principal do programa

import java.sql.Connection;
// conexão com o banco

import java.sql.PreparedStatement;
// comando SQL preparado (seguro)

import java.sql.SQLException;
// erro de SQL

import db.DB;
// classe que gerencia a conexão com o banco

public class Program {

	public static void main(String[] args) {

		// objetos que representam conexão e comando SQL
		Connection conn = null;
		PreparedStatement st = null;

		try {
			// abre conexão com o banco
			conn = DB.getConnection();
	
			// PREPARE STATEMENT com comando UPDATE
			// ? = parâmetros que vamos preencher depois
			st = conn.prepareStatement(
					"UPDATE seller "
					+ "SET BaseSalary = BaseSalary + ? "
					+ "WHERE "
					+ "(DepartmentId = ?)");

			// primeiro ? → quanto será somado ao salário
			st.setDouble(1, 200.0);

			// segundo ? → qual departamento será afetado
			st.setInt(2, 2);
			
			// executa o UPDATE no banco
			int rowsAffected = st.executeUpdate();
			
			// mostra quantas linhas foram alteradas
			System.out.println("Done! Rows affected: " + rowsAffected);
		}

		// erro de SQL
		catch (SQLException e) {
			e.printStackTrace();
		} 

		// fecha recursos
		finally {
			DB.closeStatement(st);   // fecha o comando SQL
			DB.closeConnection();    // fecha a conexão com o banco
		}
	}
}
