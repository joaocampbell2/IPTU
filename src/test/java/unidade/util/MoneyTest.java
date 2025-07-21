package unidade.util;

import main.util.Money;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void testRound() {
        float result = Money.round(0f);
        Assertions.assertEquals(0f, result);
    }



}

