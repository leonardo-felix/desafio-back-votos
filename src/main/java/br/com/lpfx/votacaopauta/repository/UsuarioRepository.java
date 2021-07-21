package br.com.lpfx.votacaopauta.repository;

import br.com.lpfx.votacaopauta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query("select u from Usuario u where u.cpf = :cpf")
    Optional<Usuario> porCPF(@Param("cpf") String cpf);

    default Usuario buscaOuCria(final String cpf){
        return porCPF(cpf).orElseGet(() -> {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setCpf(cpf);
            return save(novoUsuario);
        });
    }
}
