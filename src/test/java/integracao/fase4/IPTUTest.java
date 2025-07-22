package integracao.fase4;

import main.db.DBConnection;
import main.factory.ImovelFactory;
import main.model.IPTU;
import main.model.Imovel;
import main.model.ValorIPTU;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IPTUTest {

    private static final String DRIVER = "org.sqlite.JDBC";
    private static final String CONEXAO = "jdbc:sqlite:C:\\Users\\jpcam\\Downloads\\PTS - 2025.1 - Trabalho 4\\IPTU\\iptu.db";

    @BeforeAll
    static void setUp(){
        DBConnection.set(DRIVER, CONEXAO);
    }

    @Test
    @DisplayName("Teste do cálculo do IPTU - Fase Final")

    void testIPTU(){
        //1, 12345678, "20200507", 1500000, 500, "A"
        Imovel imovel = ImovelFactory.getByID(1);

        IPTU iptu = new IPTU(imovel);

        ValorIPTU r = iptu.calculaValor(10,10);

        assertEquals(20006.25f,r.valor);
        assertEquals(18005.62f,r.valorAVista);
        assertEquals(2166.01f,r.parcelamento.getValorParcela());
        assertEquals(10,r.parcelamento.getNumParcelas());

    }


}
