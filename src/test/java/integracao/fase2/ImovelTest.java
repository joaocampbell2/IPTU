package integracao.fase2;

import main.dao.ImovelDAO;
import main.db.DBConnection;
import main.factory.ImovelFactory;
import main.model.Imovel;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ImovelTest {

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String CONEXAO = "jdbc:sqlite:C:\\Users\\jpcam\\Downloads\\PTS - 2025.1 - Trabalho 4\\IPTU\\iptu.db";
    static final LocalDate DATA_VALIDA = LocalDate.now().minusYears(1);
    static final float VALOR = 10000;
    static final float VALOR2 = 20000;
    static final int AREA = 20;
    static final char CATEGORIA_A = 'A';
    private static int inscricao;
    private static int id;
    private static Imovel imovel;

    @BeforeAll
    static void setUp(){
        DBConnection.set(DRIVER, CONEXAO);
    }

    @Test
    @Order(10)
    void testeInserirImovel(){

        inscricao = new Random().nextInt(10000000,99999999);

        imovel = ImovelFactory.create(inscricao,DATA_VALIDA,VALOR,AREA,CATEGORIA_A);

        id = ImovelDAO.insert(imovel);

        assertTrue(id > 0);

    }

    @Test
    @Order(20)
    void testeGetByInscricao(){

        imovel = ImovelFactory.getByInscricao(inscricao);

        assertTrue(imovel.getID() >0);
    }

    @Test
    @Order(30)
    void testeGetById(){

        imovel = ImovelFactory.getByID(id);

        assertTrue(imovel.getID() >0);
    }

    @Test
    @Order(40)
    void testeUpdateImovel(){

        imovel.setValor(VALOR2);
        imovel.save();

        Imovel imovelAtualizado = ImovelFactory.getByID(id);

        assertEquals(VALOR2,imovelAtualizado.getValor());
    }

    @Test
    @Order(50)
    void testDeleteImovel(){
        imovel.delete();

        // Assert
        Imovel imovelDeletado = ImovelFactory.getByID(id);
        assertNull(imovelDeletado);
    }



}
