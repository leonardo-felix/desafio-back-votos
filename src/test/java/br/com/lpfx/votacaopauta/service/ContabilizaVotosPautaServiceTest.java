package br.com.lpfx.votacaopauta.service;

import br.com.lpfx.votacaopauta.ConfigAdicional;
import br.com.lpfx.votacaopauta.Mock;
import br.com.lpfx.votacaopauta.controller.PautaController;
import br.com.lpfx.votacaopauta.dto.RetornoValidacaoCPF;
import br.com.lpfx.votacaopauta.dto.VotoPautaDTO;
import br.com.lpfx.votacaopauta.kafka.KafkaSender;
import br.com.lpfx.votacaopauta.model.Pauta;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static br.com.lpfx.votacaopauta.Mock.pautaTeste;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@Import(ConfigAdicional.class)
@SpringBootTest
class ContabilizaVotosPautaServiceTest {

    @Autowired
    private ContabilizaVotosPautaService contabilizaVotosPautaService;

    @Autowired
    private PautaController pautaController;

    @Test
    public void testaContabilizacaoVotos(){
        Pauta pauta = pautaTeste();
        pauta.setDescricao("Teste contabilizacao");

        Pauta pautaCriada = pautaController.novaPauta(pauta);

        VotoPautaDTO votoPautaDTO = new VotoPautaDTO();
        votoPautaDTO.setCpf(Mock.geraCPF());
        votoPautaDTO.setVoto(true);
        votoPautaDTO.setIdPauta(pautaCriada.getId());

        pautaController.votarPauta(votoPautaDTO);

        votoPautaDTO.setVoto(false);
        votoPautaDTO.setCpf(Mock.geraCPF());

        pautaController.votarPauta(votoPautaDTO);

        pautaCriada.setFim(pautaCriada.getInicio());
        pautaController.atualizaPauta(pautaCriada, pautaCriada.getId());

        Pauta resultado = contabilizaVotosPautaService.contabilizaVotos(pautaCriada.getId());

        assertNotNull(resultado);
        assertEquals(resultado.getVotosSim(), 1);
        assertEquals(resultado.getVotosNao(), 1);

    }
}