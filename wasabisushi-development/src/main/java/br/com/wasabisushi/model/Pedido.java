package br.com.wasabisushi.model;
// Generated 12 de nov. de 2022 22:48:09 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Pedido generated by hbm2java
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "pedido")
public class Pedido implements java.io.Serializable {

	private Integer idPedido;
	private Cliente cliente;
	private String data;
	private String hora;
	private String formaPagamento;
	private Float valorTotal;
	private Set<ProdutoPedido> produtoPedidos = new HashSet<ProdutoPedido>(0);

	public Pedido() {
	}

	public Pedido(Cliente cliente, String data, String hora, String formaPagamento, Float valorTotal,
			Set<ProdutoPedido> produtoPedidos) {
		this.cliente = cliente;
		this.data = data;
		this.hora = hora;
		this.formaPagamento = formaPagamento;
		this.valorTotal = valorTotal;
		this.produtoPedidos = produtoPedidos;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

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

	@Column(name = "data", length = 10)
	public String getData() {
		return this.data;
	}

	public void setData(String string) {
		this.data = string;
	}
	
	@Column(name = "hora", length = 8)
	public String getHora() {
		return this.hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
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
