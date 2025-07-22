package unidade.model;

import main.model.IPTU;
import main.model.Imovel;
import main.model.ValorIPTU;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class IPTUTest {
    @Mock
    Imovel imovel;
    @InjectMocks
    IPTU iPTU;



    static final float PER_DESCONTO_NEGATIVO =  -1;
    static final float PER_DESCONTO =  10;
    static final float PER_DESCONTO_EXCESSIVO = 101;
    static final float PER_JUROS_NEGATIVO = -1;
    static final float PER_JUROS =  10;
    static final char CATEGORIA_Z = 'Z';
    static final LocalDate DATA_170_ANOS = LocalDate.now().minusYears(170);
    static final LocalDate DATA_VALIDA = LocalDate.now().minusYears(1);
    static final float VALOR = 10000;
    static final int AREA = 20;

    @Test
    void testDescontoNegativo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                iPTU.calculaValor(PER_DESCONTO_NEGATIVO,PER_JUROS)
        );

        assertEquals("Desconto para pagamento à vista inválido", exception.getMessage());
    }
    @Test
    void testDescontoExcessivo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                iPTU.calculaValor(PER_DESCONTO_EXCESSIVO,PER_JUROS)
        );

        assertEquals("Desconto para pagamento à vista inválido", exception.getMessage());
    }

    @Test
    void testJurosNegativo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                iPTU.calculaValor(PER_DESCONTO,PER_JUROS_NEGATIVO)
        );

        assertEquals("Juros do parcelamento inválido", exception.getMessage());
    }

    @Test
    void testIsencaoIdade(){
        when(imovel.getDataLiberacao()).thenReturn(DATA_170_ANOS);
        when(imovel.getValor()).thenReturn(VALOR);
        when(imovel.getArea()).thenReturn(AREA);

        ValorIPTU result = iPTU.calculaValor(PER_DESCONTO,PER_JUROS);

        assertEquals(0,result.valor);
    }

    @Test
    void testIsencaoZ(){
        when(imovel.getCategoria()).thenReturn(CATEGORIA_Z);
        when(imovel.getDataLiberacao()).thenReturn(DATA_VALIDA);
        when(imovel.getValor()).thenReturn(VALOR);
        when(imovel.getArea()).thenReturn(AREA);

        ValorIPTU result = iPTU.calculaValor(PER_DESCONTO,PER_JUROS);

        assertEquals(0,result.valor);
    }

    @ParameterizedTest(name="Data: {0}  Valor: {1}  Area: {2}, Categoria: {3}, IPTU: {4}")
    @CsvFileSource(resources="/dados_semIsencao.csv", delimiter=',')
    @DisplayName("Teste do cálculo do IPTU - Unidade")
    void testCalcularIPTU(LocalDate data,float valor,int area, char categoria, float iptuEsperado) {
        // Inicializacao
        // Criação do objeto em teste e dos mocks via anotações

        // Configuracao
        when(imovel.getCategoria()).thenReturn(categoria);
        when(imovel.getDataLiberacao()).thenReturn(data);
        when(imovel.getValor()).thenReturn(valor);
        when(imovel.getArea()).thenReturn(area);

        // Acao

        ValorIPTU r = iPTU.calculaValor(10,10);


        // Verificacao
        verify(imovel,atMost(4)).getCategoria();
        verify(imovel).getDataLiberacao();
        verify(imovel).getValor();
        verify(imovel,times(2)).getArea();


        assertEquals(iptuEsperado, r.valor, 0, "Erro no IPTU");

    }
}

