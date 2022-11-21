package br.com.wasabisushi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wasabisushi.model.Categoria;
import br.com.wasabisushi.model.Produto;

public interface Produtos extends JpaRepository<Produto, Integer> {
	
	Produto findByIdProduto(Integer idProduto);
	
	List<Produto> findByCategoria(Categoria categoria);
	
//	@Query("SELECT p FROM wasabi_sushi.produto p")
//	List<Produto> findProdutos();

}
