package application;

// Imports das classes JDBC
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// Classe que você criou pra gerenciar conexão
import db.DB;

public class Program {

	public static void main(String[] args) {

		// Objetos começam como null porque ainda não existem
		Connection conn = null; // conexão com o banco
		Statement st = null;    // executa comandos SQL
		ResultSet rs = null;    // guarda resultado do SELECT

		try {
			// abre conexão com o banco de dados
			conn = DB.getConnection();
	
			// cria um "executor" de SQL
			st = conn.createStatement();
			
			// executa o SELECT e guarda o resultado no rs
			rs = st.executeQuery("SELECT * FROM tabela");
			
			// enquanto tiver linha no resultado
			while (rs.next()) {
				// pega o valor da coluna Id e Name e imprime
				System.out.println(
					rs.getInt("Id") + ", " + rs.getString("Name")
				);
			}
		}
		catch (SQLException e) {
			// se der erro de SQL, mostra o erro no console
			e.printStackTrace();
		}
		finally {
			// SEMPRE fecha tudo no final
			// evita vazamento de memória e conexão presa

			DB.closeResultSet(rs); // fecha resultado
			DB.closeStatement(st); // fecha executor SQL
			DB.closeConnection();  // fecha conexão com banco
		}
	}
}
