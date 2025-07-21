package unidade.model;

import main.model.IPTU;
import main.model.Imovel;
import main.model.ValorIPTU;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class IPTUTest {
    @Mock
    Imovel imovel;
    @InjectMocks
    IPTU iPTU;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    static final float PER_DESCONTO_NEGATIVO =  -1;
    static final float PER_DESCONTO =  10;
    static final float PER_DESCONTO_EXCESSIVO = 101;
    static final float PER_JUROS_NEGATIVO = -1;
    static final float PER_JUROS =  10;
    static final char CATEGORIA_Z = 'Z';
    static final char CATEGORIA_A = 'A';
    static final char CATEGORIA_B = 'B';
    static final char CATEGORIA_C = 'C';
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

//    @Test
//    void testCalculaValorCategoriaA(){
//        when(imovel.getCategoria()).thenReturn(CATEGORIA_A);
//        when(imovel.getDataLiberacao()).thenReturn(DATA_VALIDA);
//        when(imovel.getValor()).thenReturn(VALOR);
//        when(imovel.getArea()).thenReturn(AREA);
//
//        ValorIPTU result = iPTU.calculaValor(PER_DESCONTO,PER_JUROS);
//
//        float valorEsperado;
//
////        area = 20
////        valor = 10000
//
//
//
//        assertEquals(valorEsperado,result.valor);
//        assertEquals(valorEsperado,result.valorAVista);
//        assertEquals(valorEsperado,result.parcelamento);
//    }



//    @Test
//    void testCalculaValor() {
//        when(imovel.getDataLiberacao()).thenReturn(LocalDate.of(2025, Month.JULY, 19));
//        when(imovel.getValor()).thenReturn(0f);
//        when(imovel.getArea()).thenReturn(0);
//        when(imovel.getCategoria()).thenReturn('a');
//
//        ValorIPTU result = iPTU.calculaValor(0f, 0f);
//        Assertions.assertEquals(new ValorIPTU(), result);
//    }
}

