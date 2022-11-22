package br.com.wasabisushi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.wasabisushi.model.Cliente;
import br.com.wasabisushi.model.Pedido;

public interface Pedidos extends JpaRepository<Pedido, Integer> {

	@Query("SELECT p FROM Pedido p WHERE p.data = :data or p.hora = :hora and p.cliente =:cliente")
	public Pedido getPedidoByDataAndHoraAndCliente(@Param("data") String data, @Param("hora") String hora,
			@Param("cliente") Cliente cliente);

}
