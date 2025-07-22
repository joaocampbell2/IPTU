package unidade.db;

import main.db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String CONEXAO = "jdbc:sqlite:C:\\Users\\jpcam\\Downloads\\PTS - 2025.1 - Trabalho 4\\IPTU\\iptu.db";

    @BeforeEach
    void preparaTest() {
        DBConnection.set(null,null);
    }

    @AfterEach
    void finalizaTest() {
        if (DBConnection.isAlive())
            DBConnection.close();
    }

    @Test
    void testDriverIndefinido() {
        DBConnection.set(null,CONEXAO);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão sem driver definido");
    }

    @Test
    void testConnectionStringIndefinida() {
        DBConnection.set(DRIVER,null);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão sem connection string definida");
    }

    @Test
    void testDriverInvalido() {
        DBConnection.set("ABC",CONEXAO);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão com driver inválido");
    }

    @Test
    void testConexaoInvalida() {
        DBConnection.set(DRIVER,"ABC");

        Connection conn = DBConnection.get();

        assertTrue(conn == null && ! DBConnection.isAlive(), "Conectou com recurso inválido");
    }

    @Test
    void testConexaoValida() {
        DBConnection.set(DRIVER,CONEXAO);

        Connection conn = DBConnection.get();

        assertTrue(conn != null && DBConnection.isAlive(), "Não conectou com recurso válido");
    }
}

