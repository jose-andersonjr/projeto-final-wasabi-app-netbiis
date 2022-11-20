package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wasabisushi.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Integer> {
	
	Usuario findByIdUsuario(Integer idUsuario);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
    public Usuario getUsuarioByEmail(@Param("email") String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email and u.senha = :senha")
    public Usuario getUsuarioByEmailAndSenha(@Param("email") String email, @Param("senha") String senha);

}
