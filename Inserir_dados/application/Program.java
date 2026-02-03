package application;
// pacote principal onde roda o programa

import java.sql.Connection;
// conexão com o banco

import java.sql.PreparedStatement;
// comando SQL seguro (evita SQL Injection)

import java.sql.ResultSet;
// resultado retornado pelo banco

import java.sql.SQLException;
// erro de SQL

import java.sql.Statement;
// usado aqui só para RETURN_GENERATED_KEYS

import java.text.ParseException;
// erro ao converter data

import java.text.SimpleDateFormat;
// formato de data (dia/mês/ano)

import db.DB;
// classe de conexão com o banco

public class Program {

    public static void main(String[] args) {

        // formato da data que vamos usar
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // objetos de conexão
        Connection conn = null;
        PreparedStatement st = null;

        try {
            // abre conexão com o banco
            conn = DB.getConnection();

            // PREPARE STATEMENT = comando SQL preparado
            // ? são parâmetros que vamos preencher depois
            st = conn.prepareStatement(
                    "INSERT INTO tabela "
                            + "(Nome, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            // RETURN_GENERATED_KEYS = permite pegar o ID criado automaticamente

            // define valores para os "?"
            st.setString(1, "Carl Purple"); // Nome
            st.setString(2, "carl@gmail.com"); // Email

            // converte texto para data SQL
            st.setDate(3, new java.sql.Date(sdf.parse("22/04/1985").getTime()));

            st.setDouble(4, 3000.0); // salário
            st.setInt(5, 4); // id do departamento

            // executa o INSERT no banco
            int rowsAffected = st.executeUpdate();

            // se inseriu algo
            if (rowsAffected > 0) {

                // pega o ID gerado automaticamente
                ResultSet rs = st.getGeneratedKeys();

                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Id: " + id);
                }
            }
            else {
                System.out.println("No rows affected!");
            }
        }

        // erro de SQL
        catch (SQLException e) {
            e.printStackTrace();
        }

        // erro ao converter data
        catch (ParseException e) {
            e.printStackTrace();
        }

        // fecha recursos
        finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
