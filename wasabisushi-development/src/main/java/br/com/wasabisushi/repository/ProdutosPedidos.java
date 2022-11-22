package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wasabisushi.model.ProdutoPedido;

public interface ProdutosPedidos extends JpaRepository<ProdutoPedido, Integer> {
	
	ProdutoPedido findByIdProdutoPedido(Integer idProdutoPedido);

}
