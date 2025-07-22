package integracao.fase1;

import main.dao.ImovelDAO;
import main.db.DBConnection;
import main.model.Imovel;
import main.model.ImovelDTO;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImovelDAOTest {

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String CONEXAO = "jdbc:sqlite:C:\\Users\\jpcam\\Downloads\\PTS - 2025.1 - Trabalho 4\\IPTU\\iptu.db";
    static final LocalDate DATA_VALIDA = LocalDate.now().minusYears(1);
    static final float VALOR = 10000;
    static final int AREA = 20;
    static final char CATEGORIA_A = 'A';
    private static int inscricao;
    private static int id;

    @BeforeAll
    public static void setUp(){
        DBConnection.set(DRIVER, CONEXAO);
    }

    @Test
    @Order(10)
    public void testInserirImovel(){
        Imovel imovel = Mockito.mock(Imovel.class);

        inscricao = new Random().nextInt(10000000,99999999);

        when(imovel.getInscricao()).thenReturn(inscricao);
        when(imovel.getValor()).thenReturn(VALOR);
        when(imovel.getArea()).thenReturn(AREA);
        when(imovel.getCategoria()).thenReturn(CATEGORIA_A);
        when(imovel.getDataLiberacao()).thenReturn(DATA_VALIDA);

        id =  ImovelDAO.insert(imovel);

        verify(imovel).getInscricao();
        verify(imovel).getValor();
        verify(imovel).getArea();
        verify(imovel).getCategoria();
        verify(imovel).getDataLiberacao();

        assertNotEquals(0,id,"Erro ao salvar!");
    }
    @Test
    @Order(20)
    public void testGetByInscricao(){
        ImovelDTO imovel = ImovelDAO.getByInscricao(inscricao);

        assertEquals(inscricao,imovel.inscricao);
        assertEquals(VALOR,imovel.valor);
        assertEquals(AREA,imovel.area);
        assertEquals(CATEGORIA_A,imovel.categoria);
        assertEquals(DATA_VALIDA,imovel.dataLiberacao);

    }
        @Test
    @Order(25)
    public void testGetByInscricaoInvalida(){

        ImovelDTO r = ImovelDAO.getByInscricao(5);

        assertEquals(r.inscricao, 0 );
        assertEquals(r.id, 0 );

    }

    @Test
    @Order(30)
    public void testGetByID(){
        ImovelDTO imovel = ImovelDAO.getByID(id);
        assertEquals(inscricao,imovel.inscricao);
        assertEquals(VALOR,imovel.valor);
        assertEquals(AREA,imovel.area);
        assertEquals(CATEGORIA_A,imovel.categoria);
        assertEquals(DATA_VALIDA,imovel.dataLiberacao);
    }

    @Test
    @Order(35)
    public void testGetByIdInvalido(){
        ImovelDTO r = ImovelDAO.getByInscricao(-5);

        assertEquals(r.inscricao, 0 );
        assertEquals(r.id, 0 );
    }

    @Test
    @Order(40)
    public void testUpdate(){
        Imovel imovel = Mockito.mock(Imovel.class);

        int inscricaoAtualizada = new Random().nextInt(10000000,99999999);

        when(imovel.getInscricao()).thenReturn(inscricaoAtualizada);
        when(imovel.getValor()).thenReturn(1000001f);
        when(imovel.getArea()).thenReturn(21);
        when(imovel.getCategoria()).thenReturn('C');
        when(imovel.getDataLiberacao()).thenReturn(LocalDate.now().minusYears(5));
        when(imovel.getID()).thenReturn(id);

        boolean update = ImovelDAO.update(imovel);

        verify(imovel).getInscricao();
        verify(imovel).getValor();
        verify(imovel).getArea();
        verify(imovel).getCategoria();
        verify(imovel).getDataLiberacao();


        ImovelDTO imovelDTO = ImovelDAO.getByID(id);


        assertTrue(update,"Erro ao salvar!");
        assertEquals(imovelDTO.dataLiberacao,imovel.getDataLiberacao(),"Erro ao atualizar!");
        assertEquals(imovelDTO.categoria,imovel.getCategoria(),"Erro ao atualizar!");
        assertEquals(id,imovel.getID(),"Erro ao atualizar!");
        assertEquals(imovelDTO.area,imovel.getArea(),"Erro ao atualizar!");
        assertEquals(imovelDTO.valor,imovel.getValor(),"Erro ao atualizar!");
        assertEquals(imovelDTO.inscricao,imovel.getInscricao(),"Erro ao atualizar!");

    }

    @Test
    @Order(50)
    public void testDelete(){
        Imovel imovel = Mockito.mock(Imovel.class);

        when(imovel.getID()).thenReturn(id);

        boolean delete = ImovelDAO.delete(imovel);

        assertTrue(delete,"Erro ao deletar!");

    }
}
