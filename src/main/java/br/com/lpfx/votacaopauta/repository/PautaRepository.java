package br.com.lpfx.votacaopauta.repository;

import br.com.lpfx.votacaopauta.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PautaRepository extends JpaRepository<Pauta, Long> {
    @Query("select p.id from Pauta p where p.fim > current_timestamp and p.contabilizadoVotos = false")
    List<Long> idsPautasFinalizadasParaContabilizar();
}
