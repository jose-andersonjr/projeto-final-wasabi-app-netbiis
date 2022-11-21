package br.com.wasabisushi.model;
// Generated 18 de nov. de 2022 12:00:04 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Pedido generated by hbm2java
 */
@Entity
@Table(name = "pedido")
public class Pedido implements java.io.Serializable {

	private Integer idPedido;
	private Cliente cliente;
	private Date data;
	private String formaPagamento;
	private Float valorTotal;
	private Set<ProdutoPedido> produtoPedidos = new HashSet<ProdutoPedido>(0);

	public Pedido() {
	}

	public Pedido(Cliente cliente, Date data, String formaPagamento, Float valorTotal,
			Set<ProdutoPedido> produtoPedidos) {
		this.cliente = cliente;
		this.data = data;
		this.formaPagamento = formaPagamento;
		this.valorTotal = valorTotal;
		this.produtoPedidos = produtoPedidos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_pedido", unique = true, nullable = false)
	public Integer getIdPedido() {
		return this.idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente")
	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data", length = 10)
	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Column(name = "forma_pagamento", length = 100)
	public String getFormaPagamento() {
		return this.formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	@Column(name = "valor_total", precision = 12, scale = 0)
	public Float getValorTotal() {
		return this.valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pedido")
	public Set<ProdutoPedido> getProdutoPedidos() {
		return this.produtoPedidos;
	}

	public void setProdutoPedidos(Set<ProdutoPedido> produtoPedidos) {
		this.produtoPedidos = produtoPedidos;
	}

}