package unidade.factory;

import main.dao.ImovelDAO;
import main.factory.ImovelFactory;
import main.model.Imovel;
import main.model.ImovelDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class ImovelFactoryTest {

    static final LocalDate DATA_VALIDA  = LocalDate.now().minusMonths(1);
    static final LocalDate DATA_INVALIDA = LocalDate.now().plusDays(1);
    static final int INSCRICAO_VALIDA = 50000000;
    static final int INSCRICAO_VALIDA_MINIMA = 10000000;
    static final int INSCRICAO_VALIDA_MAXIMA = 99999999;
    static final int INSCRICAO_INVALIDA_MENOR = 10000000 - 1;
    static final int INSCRICAO_INVALIDA_MAIOR = 99999999 + 1;
    static final int VALOR_VALIDO = 1000;
    static final int VALOR_INVALIDO_NEGATIVO = -10;
    static final int VALOR_INVALIDO_ZERO = -10;
    static final int AREA_VALIDA = 10;
    static final int AREA_INVALIDA_ZERO = 0;
    static final int AREA_INVALIDA_NEGATIVA = 0;
    static final char CATEGORIA_VALIDA = 'A';
    static final char CATEGORIA_INVALIDA_SIMBOLO = '#';
    static final char CATEGORIA_INVALIDA_MINUSCULA = 'a';



    @Test
    void testCreateSucessful() {
        Imovel result = ImovelFactory.create(INSCRICAO_VALIDA,DATA_VALIDA,VALOR_VALIDO,AREA_VALIDA,CATEGORIA_VALIDA);
        assertEquals(DATA_VALIDA,result.getDataLiberacao());
        assertEquals(VALOR_VALIDO,result.getValor());
        assertEquals(AREA_VALIDA,result.getArea());
        assertEquals(CATEGORIA_VALIDA,result.getCategoria());
        assertEquals(INSCRICAO_VALIDA,result.getInscricao());
    }

    @Test
    void testCreateSucessfulLimites() {
        assertDoesNotThrow(() -> ImovelFactory.create(INSCRICAO_VALIDA_MINIMA,DATA_VALIDA,VALOR_VALIDO,AREA_VALIDA,CATEGORIA_VALIDA));
        assertDoesNotThrow(() -> ImovelFactory.create(INSCRICAO_VALIDA_MAXIMA,DATA_VALIDA,VALOR_VALIDO,AREA_VALIDA,CATEGORIA_VALIDA));
    }

    @Test
    @DisplayName("Deve lançar exceção para inscrição menor que o limite")
    void deveLancarExcecaoParaInscricaoInvalidaMenor() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_INVALIDA_MENOR, DATA_VALIDA, VALOR_VALIDO, AREA_VALIDA, CATEGORIA_VALIDA)
        );
        assertEquals("Inscrição inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para inscrição maior que o limite")
    void deveLancarExcecaoParaInscricaoInvalidaMaior() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_INVALIDA_MAIOR, DATA_VALIDA, VALOR_VALIDO, AREA_VALIDA, CATEGORIA_VALIDA)
        );
        assertEquals("Inscrição inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para data de liberação no futuro")
    void deveLancarExcecaoParaDataLiberacaoFutura() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_INVALIDA, VALOR_VALIDO, AREA_VALIDA, CATEGORIA_VALIDA)
        );
        assertEquals("Data de liberação inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para valor igual a zero")
    void deveLancarExcecaoParaValorIgualAZero() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_INVALIDO_ZERO, AREA_VALIDA, CATEGORIA_VALIDA)
        );
        assertEquals("Valor inválido", exception.getMessage());
    }
    @Test
    @DisplayName("Deve lançar exceção para valor negativo")
    void deveLancarExcecaoParaValorNegativo() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_INVALIDO_NEGATIVO, AREA_VALIDA, CATEGORIA_VALIDA)
        );
        assertEquals("Valor inválido", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para área igual a zero")
    void deveLancarExcecaoParaAreaIgualAZero() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_VALIDO, AREA_INVALIDA_ZERO, CATEGORIA_VALIDA)
        );
        assertEquals("Área inválida", exception.getMessage());
    }
    @Test
    @DisplayName("Deve lançar exceção para área negativa")
    void deveLancarExcecaoParaAreaNegativa() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_VALIDO, AREA_INVALIDA_NEGATIVA, CATEGORIA_VALIDA)
        );
        assertEquals("Área inválida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção para categoria fora do intervalo A-Z")
    void deveLancarExcecaoParaCategoriaInvalida() {
        // Act & Assert
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_VALIDO, AREA_VALIDA, CATEGORIA_INVALIDA_MINUSCULA)
        );
        assertEquals("Categoria inválida", exception1.getMessage());

        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () ->
                ImovelFactory.create(INSCRICAO_VALIDA, DATA_VALIDA, VALOR_VALIDO, AREA_VALIDA, CATEGORIA_INVALIDA_SIMBOLO)
        );
        assertEquals("Categoria inválida", exception2.getMessage());
    }

    @Test
    void testGetByIDSucessful() {

        ImovelDTO dto = new ImovelDTO();

        dto.id = 1;
        dto.area = AREA_VALIDA;
        dto.categoria = CATEGORIA_VALIDA;
        dto.valor = VALOR_VALIDO;
        dto.inscricao = INSCRICAO_VALIDA;
        dto.dataLiberacao = DATA_VALIDA;

        try(MockedStatic<ImovelDAO> mock = mockStatic(ImovelDAO.class)) {
            when(ImovelDAO.getByID(anyInt())).thenReturn(dto);
            Imovel result = ImovelFactory.getByID(0);

            assertNotNull(result);
            assertEquals(dto.dataLiberacao,result.getDataLiberacao());
            assertEquals(dto.valor,result.getValor());
            assertEquals(dto.area,result.getArea());
            assertEquals(dto.categoria,result.getCategoria());
            assertEquals(dto.inscricao,result.getInscricao());
        }
    }

    @Test
    void testGetByIDUnSucessful() {

        ImovelDTO dto = new ImovelDTO();
        dto.id = 0;
        try(MockedStatic<ImovelDAO> mock = mockStatic(ImovelDAO.class)) {
            when(ImovelDAO.getByID(anyInt())).thenReturn(dto);
            Imovel result = ImovelFactory.getByID(0);

            assertNull(result);
        }
    }

    @Test
    void testGetByInscricao() {
        ImovelDTO dto = new ImovelDTO();

        dto.id = 1;
        dto.area = AREA_VALIDA;
        dto.categoria = CATEGORIA_VALIDA;
        dto.valor = VALOR_VALIDO;
        dto.inscricao = INSCRICAO_VALIDA;
        dto.dataLiberacao = DATA_VALIDA;

        try(MockedStatic<ImovelDAO> mock = mockStatic(ImovelDAO.class)) {
            when(ImovelDAO.getByInscricao(anyInt())).thenReturn(dto);
            Imovel result = ImovelFactory.getByInscricao(INSCRICAO_VALIDA);

            assertNotNull(result);
            assertEquals(dto.dataLiberacao,result.getDataLiberacao());
            assertEquals(dto.valor,result.getValor());
            assertEquals(dto.area,result.getArea());
            assertEquals(dto.categoria,result.getCategoria());
            assertEquals(dto.inscricao,result.getInscricao());
        }
    }
    @Test
    void testGetByInscricaoUnSucessful() {

        ImovelDTO dto = new ImovelDTO();
        dto.id = 0;
        try(MockedStatic<ImovelDAO> mock = mockStatic(ImovelDAO.class)) {
            when(ImovelDAO.getByInscricao(anyInt())).thenReturn(dto);
            Imovel result = ImovelFactory.getByInscricao(INSCRICAO_VALIDA);

            assertNull(result);
        }
    }
}

