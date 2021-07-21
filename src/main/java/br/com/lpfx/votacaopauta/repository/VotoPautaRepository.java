package br.com.lpfx.votacaopauta.repository;

import br.com.lpfx.votacaopauta.dto.ResultadoVotacaoDTO;
import br.com.lpfx.votacaopauta.model.VotoPauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VotoPautaRepository extends JpaRepository<VotoPauta, Long> {

    @Query("select " +
            "new br.com.lpfx.votacaopauta.dto.ResultadoVotacaoDTO(" +
                "v.pauta.id, " +
            "sum(case when v.voto = true then 1 else 0 end), " + // Votos sim
            "sum(case when v.voto = false then 1 else 0 end)) " + // Votos não
            "from VotoPauta v where v.pauta.id = :id")
    ResultadoVotacaoDTO obterResultado(@Param("id") Long idPauta);
}
