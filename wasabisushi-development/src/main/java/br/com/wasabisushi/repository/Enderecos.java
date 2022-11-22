package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wasabisushi.model.Cliente;
import br.com.wasabisushi.model.Endereco;

public interface Enderecos extends JpaRepository<Endereco, Integer> {
	
	Endereco findByIdEndereco(Integer idEndereco);
	
	@Query("SELECT e FROM Endereco e WHERE e.cep = :cep")
    public Endereco getEnderecoByCep(@Param("cep") String cep);

	Endereco findByCliente(Cliente cliente);

}
