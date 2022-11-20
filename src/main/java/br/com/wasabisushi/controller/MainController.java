package br.com.wasabisushi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.wasabisushi.model.Categoria;
import br.com.wasabisushi.model.Cliente;
import br.com.wasabisushi.model.Endereco;
import br.com.wasabisushi.model.Produto;
import br.com.wasabisushi.model.Usuario;
import br.com.wasabisushi.repository.Categorias;
import br.com.wasabisushi.repository.Clientes;
import br.com.wasabisushi.repository.Enderecos;
import br.com.wasabisushi.repository.Produtos;
import br.com.wasabisushi.repository.Usuarios;

@Controller
@RequestMapping("/")
public class MainController {

	@Autowired
	Usuarios usuarios;

	@Autowired
	Clientes clientes;

	@Autowired
	Enderecos enderecos;

	Usuario usuario;

	boolean logado;

	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView("usuario/login");
		return mv;
	}

	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String logar(String email, String senha) {

		usuario = usuarios.getUsuarioByEmailAndSenha(email, senha);

		if (usuario != null) {
			this.logado = true;
			return "redirect:/home";
		}
		return "redirect:/login";
	}

	@RequestMapping(value = { "/logout" }, method = RequestMethod.GET)
	public String logout(String email, String senha) {

		usuario = null;
		this.logado = false;
		return "redirect:/login";
	}

	@RequestMapping(value = "/acesso_negado", method = RequestMethod.GET)
	public String acessoNegado() {

		return "base/acesso_negado";
	}

	@RequestMapping(value = { "/cadastro" }, method = RequestMethod.GET)
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("usuario/registrar");
		mv.addObject("usuarios", usuarios.findAll());
		mv.addObject("usuario", new Usuario());
		mv.addObject("cliente", new Cliente());
		mv.addObject("endereco", new Endereco());

		return mv;
	}

	@RequestMapping(value = { "/cadastro" }, method = RequestMethod.POST)
	public String salvar(Cliente cliente, Endereco endereco, Usuario usuario) {

		this.usuarios.save(usuario);

		cliente.setUsuario(usuarios.getUsuarioByEmail(usuario.getEmail()));

		String cpf = cliente.getCpf();

		cliente.setCpf(cpf.substring(0, 3) + cpf.substring(4, 7) + cpf.substring(8, 11) + cpf.substring(12, 14));

		String telefone = cliente.getTelefone();

		cliente.setTelefone(telefone.substring(1, 3) + telefone.substring(5, 10) + telefone.substring(11, 15));

		this.clientes.save(cliente);

		endereco.setCliente(clientes.findByCpf(cliente.getCpf()));

		this.enderecos.save(endereco);

		return "redirect:/login";
	}

	@RequestMapping(value = { "/alterarCadastro" }, method = RequestMethod.GET)
	public ModelAndView editar(@RequestParam("idUsuario") Integer idUsuario, Model model) {

		Usuario usuario = this.usuarios.findByIdUsuario(idUsuario);

		Cliente cliente = this.clientes.findByUsuario(usuario);

		Endereco endereco = this.enderecos.findByCliente(cliente);

		model.addAttribute("usuarios", usuario);
		model.addAttribute("clientes", cliente);
		model.addAttribute("enderecos", endereco);

		if (this.logado) {
			return new ModelAndView("usuario/alterar-cadastro");
		}
		return new ModelAndView("base/acesso_negado");
	}

	@RequestMapping(value = { "/salvarAlteracao" }, method = RequestMethod.POST)
	public ModelAndView salvarAlteracao(Usuario usuario, Cliente cliente, Endereco endereco) {

		System.out.println("usuario "+usuario.getIdUsuario());

		this.usuarios.saveAndFlush(usuario);
		this.clientes.saveAndFlush(cliente);
		this.enderecos.saveAndFlush(endereco);

		ModelAndView modelAndView = new ModelAndView("redirect:/home");

		return modelAndView;
	}

	@Autowired
	Produtos produtos;

	@Autowired
	Categorias categorias;

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView home = new ModelAndView("base/home");
		ModelAndView acesso_negado = new ModelAndView("base/acesso_negado");
		home.addObject("usuario", usuario);
		home.addObject("cliente", clientes.findByUsuario(usuario));
		home.addObject("categorias", categorias.findAll());
		Categoria categoria = categorias.findByIdCategoria(1);
		home.addObject("promocoes", produtos.findByCategoria(categoria));

		if (this.logado)
			return home;

		return acesso_negado;
	}

	@GetMapping("/produtos/{idCategoria}")
	@ResponseBody
	public List<Produto> listaProdutos(Model model, @PathVariable("idCategoria") Integer idCategoria) {

		Categoria categoria = categorias.findByIdCategoria(idCategoria);
		List<Produto> produtos = this.produtos.findByCategoria(categoria);
		System.out.println(produtos);

		return produtos;

	}

	@GetMapping("/imagem/{idProduto}")
	@ResponseBody
	public byte[] exibirImagem(Model model, @PathVariable("idProduto") Integer idProduto) {
		Produto produto = this.produtos.findByIdProduto(idProduto);
		return produto.getFoto();

	}

}
