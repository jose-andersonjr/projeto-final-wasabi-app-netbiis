package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wasabisushi.model.Categoria;

public interface Categorias extends JpaRepository<Categoria, Integer> {
	
	Categoria findByIdCategoria(Integer idCategoria);

}
