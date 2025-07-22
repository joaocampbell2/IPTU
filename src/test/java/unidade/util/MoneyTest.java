package unidade.util;

import main.util.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MoneyTest {

//    	   *   72.0050 -> 72005 + 5 = 72010 -> 7201 -> 72.01
//         *   72.0049 -> 72004 + 5 = 72009 -> 7200 -> 72.00
//         *   72.9950 -> 72995 + 5 = 73000 -> 7300 -> 73.00
//         *   72.9949 -> 72994 + 5 = 72999 -> 7299 -> 72.99
//         *   72.5250 -> 72505 + 5 = 72510 -> 7253 -> 72.53
//         *   72.5749 -> 72504 + 5 = 72509 -> 7257 -> 72.57
//         *   72.4950 -> 72495 + 5 = 72500 -> 7250 -> 72.50
//         *   72.4849 -> 72494 + 5 = 72499 -> 7248 -> 72.48

    @ParameterizedTest(name = "round({0}) deve retornar {1}")
    @CsvSource({
            "72.0050f, 72.01f",
            "72.0049f, 72.00f",
            "72.9950f, 73.00f",
            "72.9949f, 72.99f",
            "72.5250f, 72.53f",
            "72.5749f, 72.57f",
            "72.4950f, 72.50f",
            "72.4849f, 72.48f"
    })
    void testArredondar(float entrada, float saida) {
        float result = Money.round(entrada);
        Assertions.assertEquals(saida, result);
    }


}

