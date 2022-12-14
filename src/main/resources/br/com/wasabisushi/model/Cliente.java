package br.com.wasabisushi.model;
// Generated 18 de nov. de 2022 12:00:04 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Cliente generated by hbm2java
 */
@Entity
@Table(name = "cliente", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
public class Cliente implements java.io.Serializable {

	private Integer idCliente;
	private Usuario usuario;
	private String cpf;
	private String nome;
	private String telefone;
	private Set<Endereco> enderecos = new HashSet<Endereco>(0);
	private Set<Pedido> pedidos = new HashSet<Pedido>(0);

	public Cliente() {
	}

	public Cliente(String cpf) {
		this.cpf = cpf;
	}

	public Cliente(Usuario usuario, String cpf, String nome, String telefone, Set<Endereco> enderecos,
			Set<Pedido> pedidos) {
		this.usuario = usuario;
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.enderecos = enderecos;
		this.pedidos = pedidos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id_cliente", unique = true, nullable = false)
	public Integer getIdCliente() {
		return this.idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "cpf", unique = true, nullable = false, length = 12)
	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "nome")
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "telefone", length = 15)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "endereco_usuario", joinColumns = {
			@JoinColumn(name = "cliente", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "endereco", nullable = false, updatable = false) })
	public Set<Endereco> getEnderecos() {
		return this.enderecos;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cliente")
	public Set<Pedido> getPedidos() {
		return this.pedidos;
	}

	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
