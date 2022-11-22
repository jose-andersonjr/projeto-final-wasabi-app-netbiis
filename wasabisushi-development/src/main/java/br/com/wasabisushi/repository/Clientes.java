package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wasabisushi.model.Cliente;
import br.com.wasabisushi.model.Usuario;

public interface Clientes extends JpaRepository<Cliente, Integer> {
	
	Cliente findByIdCliente(Integer idCliente);
	
	Cliente findByUsuario(Usuario usuario);

	Cliente findByCpf(String cpf);

}
