package application;
// pacote principal do programa

import java.sql.Connection;
// conexão com o banco

import java.sql.SQLException;
// erro de SQL

import java.sql.Statement;
// comando SQL simples

import db.DB;
// classe que gerencia a conexão

import db.DbException;
// exceção personalizada do projeto

public class Program {

	public static void main(String[] args) {

		Connection conn = null;
		Statement st = null;

		try {
			// abre conexão
			conn = DB.getConnection();

			// DESLIGA o auto commit
			// agora o banco NÃO salva automaticamente
			conn.setAutoCommit(false);

			// cria comando SQL
			st = conn.createStatement();
			
			// PRIMEIRO UPDATE
			int rows1 = st.executeUpdate(
				"UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1"
			);

			// CÓDIGO DE TESTE PARA FORÇAR ERRO
			// se descomentar, simula falha
			//int x = 1;
			//if (x < 2) {
			//	throw new SQLException("Fake error");
			//}
			
			// SEGUNDO UPDATE
			int rows2 = st.executeUpdate(
				"UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2"
			);
			
			// CONFIRMA TODAS AS ALTERAÇÕES
			conn.commit();
			
			// mostra quantas linhas foram alteradas
			System.out.println("rows1 = " + rows1);
			System.out.println("rows2 = " + rows2);
		}

		// SE DER ERRO
		catch (SQLException e) {
			try {
				// DESFAZ tudo que foi feito
				conn.rollback();

				// lança exceção personalizada
				throw new DbException(
					"Transaction rolled back! Caused by: " + e.getMessage()
				);
			} 
			catch (SQLException e1) {
				throw new DbException(
					"Error trying to rollback! Caused by: " + e1.getMessage()
				);
			}
		} 

		// fecha recursos
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}
