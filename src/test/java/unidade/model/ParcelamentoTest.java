package unidade.model;


import main.model.Parcelamento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParcelamentoTest {

    @Test
    void testParcelamento0() {

        Parcelamento parcelamento = new Parcelamento(0,0);

        assertEquals(0,parcelamento.getNumParcelas());
        assertEquals(0,parcelamento.getValorParcela());
        assertEquals(0,parcelamento.getValorTotal());
    }

    @Test
    void testParcelamento300() {

        Parcelamento parcelamento = new Parcelamento(300,0);

        assertEquals(3,parcelamento.getNumParcelas());
        assertEquals(100,parcelamento.getValorParcela());
        assertEquals(300,parcelamento.getValorTotal());
    }

    @Test
    void testParcelamento1000() {

        Parcelamento parcelamento = new Parcelamento(1000,0);

        assertEquals(5,parcelamento.getNumParcelas());
        assertEquals(200,parcelamento.getValorParcela());
        assertEquals(1000,parcelamento.getValorTotal());
    }

    @Test
    void testParcelamento1001() {

        Parcelamento parcelamento = new Parcelamento(1001,0);

        assertEquals(10,parcelamento.getNumParcelas());
        assertEquals(100.1f,parcelamento.getValorParcela());
        assertEquals(1001,parcelamento.getValorTotal());
    }

    @Test
    void testParcelamentoValorNegativo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Parcelamento(-1, 0));
        assertEquals("Valor do IPTU inválido", exception.getMessage());

    }

    @Test
    void testParcelamentoJurosNegativo() {

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Parcelamento(0, -1));
        assertEquals("Juros do parcelamento inválido", exception.getMessage());

    }
}

