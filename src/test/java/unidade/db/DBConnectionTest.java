package unidade.db;

import main.db.DBConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DBConnectionTest {
    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String CONEXAO = "jdbc:sqlite:C:\\Users\\jpcam\\Downloads\\PTS - 2025.1 - Trabalho 4\\IPTU\\iptu.db";

    @BeforeEach
    public void preparaTest() {
        DBConnection.set(null,null);
    }

    @AfterEach
    public void finalizaTest() {
        if (DBConnection.isAlive())
            DBConnection.close();
    }

    @Test
    public void testDriverIndefinido() {
        DBConnection.set(null,CONEXAO);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão sem driver definido");
    }

    @Test
    public void testConnectionStringIndefinida() {
        DBConnection.set(DRIVER,null);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão sem connection string definida");
    }

    @Test
    public void testDriverInvalido() {
        DBConnection.set("ABC",CONEXAO);

        assertThrows(IllegalArgumentException.class,
                () -> DBConnection.get(),
                "Conexão com driver inválido");
    }

//    @Test
//    public void testFecharSemAbrir() {
//        DBConnection.set(DRIVER,CONEXAO);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                DBConnection.close()
//        );
//
//        assertEquals("Conexão com BD não existe", exception.getMessage());
//    }

    @Test
    public void testConexaoInvalida() {
        DBConnection.set(DRIVER,"ABC");

        Connection conn = DBConnection.get();

        assertTrue(conn == null && ! DBConnection.isAlive(), "Conectou com recurso inválido");
    }

    @Test
    public void testConexaoValida() {
        DBConnection.set(DRIVER,CONEXAO);

        Connection conn = DBConnection.get();

        assertTrue(conn != null && DBConnection.isAlive(), "Não conectou com recurso válido");
    }
}

