package integracao.fase3;

import main.dao.ImovelDAO;
import main.factory.ImovelFactory;
import main.model.IPTU;
import main.model.Imovel;
import main.model.ImovelDTO;
import main.model.ValorIPTU;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IPTUTest {

    @ParameterizedTest(name="Data: {0}  Valor: {1}  Area: {2}, Categoria: {3}, IPTU: {4}")
    @CsvFileSource(resources="/dados.csv", delimiter=',')
    @DisplayName("Teste do cálculo do IPTU - Fase 3")
    void testIPTU(LocalDate data,float valor,int area, char categoria, float iptuEsperado) {

        int inscricao = 10000000;
        try(MockedStatic<ImovelDAO> dao = Mockito.mockStatic(ImovelDAO.class)) {

            ImovelDTO dto = new ImovelDTO();
            dto.id = 1;
            dto.dataLiberacao=data;
            dto.inscricao = inscricao;
            dto.valor = valor;
            dto.area = area;
            dto.categoria = categoria;

            dao.when(()-> ImovelDAO.getByInscricao(inscricao)).thenReturn(dto);

            Imovel imovel = ImovelFactory.getByInscricao(inscricao);


            IPTU iptu = new IPTU(imovel);

            ValorIPTU r = iptu.calculaValor(10,0);
            assertEquals(iptuEsperado,r.valor);

        }
    }

}
