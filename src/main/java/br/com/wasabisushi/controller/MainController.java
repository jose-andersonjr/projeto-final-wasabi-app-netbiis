package br.com.wasabisushi.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.wasabisushi.model.Categoria;
import br.com.wasabisushi.model.Cliente;
import br.com.wasabisushi.model.Endereco;
import br.com.wasabisushi.model.Pedido;
import br.com.wasabisushi.model.Produto;
import br.com.wasabisushi.model.ProdutoPedido;
import br.com.wasabisushi.model.Usuario;
import br.com.wasabisushi.repository.Categorias;
import br.com.wasabisushi.repository.Clientes;
import br.com.wasabisushi.repository.Enderecos;
import br.com.wasabisushi.repository.Pedidos;
import br.com.wasabisushi.repository.Produtos;
import br.com.wasabisushi.repository.ProdutosPedidos;
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

	@Autowired
	private Pedidos pedidos;

	@Autowired
	private ProdutosPedidos produtosPedidos;

	Usuario usuario;

	boolean logado;

	List<ProdutoPedido> produtosPedido = new ArrayList<ProdutoPedido>();

	SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat hf = new SimpleDateFormat("HH:mm:ss");
	SimpleDateFormat dfBR = new SimpleDateFormat("dd/MM/yyyy");

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
		this.produtosPedido.clear();
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
	public ModelAndView salvarAlteracao(@Valid Usuario usuario, @Valid Cliente cliente, @Valid Endereco endereco,
			final BindingResult result, Model model, RedirectAttributes redirectAttributes) {

		System.out.println("usuario " + usuario.getIdUsuario());
		System.out.println("cliente" + cliente.getIdCliente());
		System.out.println("endereco" + endereco.getCep());

		model.addAttribute("usuarios", usuario);
		model.addAttribute("clientes", cliente);
		model.addAttribute("endereco", endereco);

		this.usuarios.saveAndFlush(usuario);
		cliente.setUsuario(usuario);
		this.clientes.saveAndFlush(cliente);
		endereco.setCliente(cliente);
		this.enderecos.saveAndFlush(endereco);

		ModelAndView modelAndView = new ModelAndView("redirect:/home");

		return modelAndView;
	}

	@RequestMapping(value = { "/pagamento" }, method = RequestMethod.GET)
	public ModelAndView pagamento() {
		ModelAndView pagamento = new ModelAndView("base/pagamento");
		ModelAndView acesso_negado = new ModelAndView("base/acesso_negado");
		pagamento.addObject("usuario", usuario);
		Cliente cliente = clientes.findByUsuario(usuario);
		pagamento.addObject("cliente", cliente);
		Endereco endereco = enderecos.findByCliente(cliente);
		pagamento.addObject("endereco", endereco);
		pagamento.addObject("categorias", categorias.findAll());
		pagamento.addObject("carrinho", this.produtosPedido);
		pagamento.addObject("pedido", new Pedido());

		if (this.logado)
			return pagamento;

		return acesso_negado;
	}

	@RequestMapping(value = { "/salvarPedido" }, method = RequestMethod.POST)
	public ModelAndView salvarPedido(@Valid Cliente cliente, @Valid Pedido pedido, final BindingResult result,
			Model model, RedirectAttributes redirectAttributes) {

		model.addAttribute("cliente", cliente);
		model.addAttribute("pedidos", pedido);

		Float valorPedido = 0f;
		Float taxaEntrega = 10f;

		for (ProdutoPedido produtoPedido : this.produtosPedido) {
			valorPedido += produtoPedido.getSubtotal();
		}

		pedido.setValorTotal(taxaEntrega + valorPedido);
		pedido.setCliente(cliente);
		Date data = new Date();
		pedido.setData(this.fd.format(data));
		pedido.setHora(this.hf.format(data));
		this.pedidos.save(pedido);

		for (ProdutoPedido produtoPedido : this.produtosPedido) {
			produtoPedido.setPedido(pedido);
			produtosPedidos.save(produtoPedido);
		}

		produtosPedido.clear();

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
		home.addObject("carrinho", produtosPedido);

		if (this.logado)
			return home;

		return acesso_negado;
	}

	@RequestMapping(value = { "/alterarQuantidade/{id}/{acao}" }, method = RequestMethod.GET)
	public ModelAndView alterarQuantidade(@PathVariable Integer id, @PathVariable Integer acao) {

		for (ProdutoPedido pp : this.produtosPedido) {
			if (pp.getProduto().getIdProduto().equals(id)) {
				
				if (acao.equals(1)) {
					pp.setQuantidade(pp.getQuantidade() + 1);
				} else if (acao.equals(0)) {
					pp.setQuantidade(pp.getQuantidade() - 1);
				} 				
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/pagamento");

		return modelAndView;
	}

	
	@RequestMapping(value = { "/removerProduto/{id}" }, method = RequestMethod.GET)
	public ModelAndView removerProduto(@PathVariable Integer id) {

		for (ProdutoPedido pp : this.produtosPedido) {
			if (pp.getProduto().getIdProduto().equals(id)) {
				
				this.produtosPedido.remove(pp);
						
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/pagamento");

		return modelAndView;
	}
	
	
	@RequestMapping(value = { "/removerProdutoHome/{id}" }, method = RequestMethod.GET)
	public ModelAndView removerProdutoHome(@PathVariable Integer id) {

		for (ProdutoPedido pp : this.produtosPedido) {
			if (pp.getProduto().getIdProduto().equals(id)) {
				
				this.produtosPedido.remove(pp);
						
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView("redirect:/home");

		return modelAndView;
	}

	
	@RequestMapping(value = { "/adicionarProdutoCarrinho" }, method = RequestMethod.POST)
	public ModelAndView adicionar(Integer quantidade, Integer idProduto, Model model) {
		ProdutoPedido produtoPedido = new ProdutoPedido();
		Produto produto = produtos.findByIdProduto(idProduto);

		produtoPedido.setProduto(produto);
		produtoPedido.setQuantidade(quantidade);
		produtoPedido.setSubtotal(quantidade * produto.getPreco());

		this.produtosPedido.add(produtoPedido);

		ModelAndView modelAndView = new ModelAndView("redirect:/home");

		return modelAndView;
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
