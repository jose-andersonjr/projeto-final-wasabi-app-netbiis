// ================================================== MODAL =========================================== //
const modal = document.querySelector('.container-modal')

const switchModal = () => {
	const actualStyle = modal.style.display
	if (actualStyle == 'block') {
		modal.style.display = 'none'
		let imagemModal = document.querySelector('.container-imagem-modal')
		let nomeModal = document.querySelector('.container-nome-modal')
		let descricaoModal = document.querySelector('.container-descricao-modal')
		let valorUnitModal = document.querySelector('#valor-unit-modal')
		let valorTotalModal = document.querySelector('#valor-total-modal span')
		let formularioSpring = document.querySelector('.container-form-modal')

		while (formularioSpring.firstChild) {
			formularioSpring.removeChild(formularioSpring.firstChild)
		}
		
		while (valorTotalModal.firstChild) {
			valorTotalModal.removeChild(valorTotalModal.firstChild)
		}
		
		while (valorUnitModal.firstChild) {
			valorUnitModal.removeChild(valorUnitModal.firstChild)
		}
		
		while (descricaoModal.firstChild) {
			descricaoModal.removeChild(descricaoModal.firstChild)
		}
		
		while (nomeModal.firstChild) {
			nomeModal.removeChild(nomeModal.firstChild)
		}
		
		while (imagemModal.firstChild) {
			imagemModal.removeChild(imagemModal.firstChild)
		}


	} else {
		modal.style.display = 'block'
	}
}

window.onclick = function(event) {
	let btnVoltar = document.querySelector('#btn-voltar')
	if (event.target == modal || event.target == btnVoltar) {
		switchModal()
	}
}

// =================================================== GERAR O CONTEUDO DO MODAL ==============================================================

var cardsProdutos = document.getElementsByClassName('card-produto')

for (let i = 0; i < cardsProdutos.length; i++) {
	cardsProdutos[i].addEventListener('click',function(event) {
			let cardProduto = event.currentTarget
			let imagemDoCard = cardProduto.querySelector('.imagem-produto img')
			let nomeDoCard = cardProduto.querySelector('.texto-produto h5')
			let descricaoDoCard = cardProduto.querySelector('.descricao-produto')
			let valorUnitDoCard = cardProduto.querySelector('.preco-produto')
			let imagemDoCardClone = imagemDoCard.cloneNode(true)
			let nomeDoCardClone = nomeDoCard.cloneNode(true)
			let valorUnitDoCardClone = valorUnitDoCard.cloneNode(true)
			let descricaoDoCardClone = descricaoDoCard.cloneNode(true)
			let containerImagemModal = document.querySelector('.container-imagem-modal')
			let containerNomeModal = document.querySelector('.container-nome-modal')
			let containerDescricaoModal = document.querySelector('.container-descricao-modal') 
			let containerValorUnitModal = document.querySelector('#valor-unit-modal')
			let containerValorTotal = document.querySelector('#valor-total-modal span')


			// ================================= PASSANDO O FORM (HIDDEN) DO CARD PARA O MODAL ==================================================
			const springForm = cardProduto.querySelector('.formulario-spring')
			const springFormClone = springForm.cloneNode(true)
			springFormClone.classList.remove("hidden")
			const containerInformacoesModal = document.querySelector('.container-form-modal')
			containerInformacoesModal.appendChild(springFormClone)
			containerImagemModal.appendChild(imagemDoCardClone)
			containerNomeModal.appendChild(nomeDoCardClone)
			containerDescricaoModal.appendChild(descricaoDoCardClone)
			containerValorUnitModal.appendChild(valorUnitDoCardClone)

			containerValorTotal.innerText = valorUnitDoCardClone.textContent

			switchModal()
			// alterarValorTotal(cardProduto)
		},
		false
	)
}


// ======================= PASSAGEM DE DADOS MODAL -> LISTA DE PEDIDO ===================================== //
const listaPedido = document.querySelector('div.listapedido')
const btnAdicionar = document.querySelector('#btn-adicionar')

btnAdicionar.addEventListener('click', () => {
	const modalAberto = document.querySelector('.conteudo-modal')
	/// ======================= CRIANDO A DIV DE CADA PRODUTO =========================
	let divItemBloco = document.createElement('div')
	divItemBloco.className = 'itembloco'
	let liItemTitle = document.createElement('li')
	liItemTitle.className = 'itemtitle'
	let divItemInfo = document.createElement('div')
	divItemInfo.className = 'iteminfo'
	let liItemQuantidade = document.createElement('li')
	liItemQuantidade.id = 'itemquantidade'
	let liItemPreco = document.createElement('li')
	liItemPreco.id = 'itempreco'
	let divItemBtnSet = document.createElement('div')
	divItemBtnSet.className = 'itembtnset'
	let btnRemover = document.createElement('button')
	btnRemover.className = 'itembtn'
	btnRemover.innerHTML = '<span>Remover</span>'

	divItemBtnSet.appendChild(btnRemover)
	divItemInfo.appendChild(liItemQuantidade)
	divItemInfo.appendChild(liItemPreco)
	divItemBloco.appendChild(liItemTitle)
	divItemBloco.appendChild(divItemInfo)
	divItemBloco.appendChild(divItemBtnSet)

	listaPedido.appendChild(divItemBloco)

	// pegando o valor unitário do produto e convertendo ele para numero
	let precoProdutoModal =
		modalAberto.querySelector('.preco-produto').textContent
	precoProdutoModal = precoProdutoModal.replace('R$', '').replace(',', '.')

	precoProdutoModal = round(precoProdutoModal)

	// PEGANDO O NOME DO PRODUTO
	let nomeProduto = modalAberto.querySelector(
		'.container-nome-modal h5'
	).textContent
	liItemTitle.innerText = nomeProduto

	// colocando um valor no Valortotal no modal

	let quantidadeModal = modalAberto.querySelector('#quantidade')
	let quantidade = quantidadeModal.value
	liItemQuantidade.innerText = 'Quantidade: ' + quantidade

	let precoTotal = precoProdutoModal * quantidade
	precoTotal = round(precoTotal)
	liItemPreco.innerText = 'Preço: R$' + precoTotal

	// ADICIONANDO FUNCAO DE REMOVER O PROPRIO BOTAO
	btnRemover.addEventListener('click', (event) => {
		let cardProdutoCarrinho = divItemBloco
		somarTotalPedido(Math.abs(precoTotal) * -1)
		cardProdutoCarrinho.remove()
		habilitarPagamento()
	})

	somarTotalPedido(precoTotal)
	switchModal()
	habilitarPagamento()
})

let valorTotalPedido = 10
function somarTotalPedido(precoProduto) {
	precoProduto = round(precoProduto)
	valorTotalPedido = round(valorTotalPedido + precoProduto)
	document.querySelector('#totalpedidocarrinho').innerText = valorTotalPedido
}

function round(num) {
	return +(Math.round(num + 'e+2') + 'e-2')
}

function alterarValorTotal(event) {
	// console.log(event)
	let qtdProduto = event.value
	//pegar o valor do produto no modal
	let precoProdutoModal = document.querySelector('.preco-produto').textContent
	//selecionar a div que vai recerber o valor total
	precoProdutoModal = precoProdutoModal.replace('R$', '').replace(',', '.')
	let containerValorTotal = document.querySelector('#valor-total-modal span')
	let valorTotal = precoProdutoModal * qtdProduto
	valorTotal = round(valorTotal).toFixed(2)
	valorTotal = valorTotal.toString(10)
	containerValorTotal.innerText = 'R$ ' + valorTotal.replace('.', ',')
}

// =============================================================== GAVETA ========================================================

const pagina = document.querySelector('#container-principal')
const sideNav = document.getElementById('mySidenav')

function openNav() {
	sideNav.style.width = '18rem'
}

function closeNav() {
	sideNav.style.width = '0'
}

// ============================================================ BOTAO PAGAMENTO DISABLED ================================================== //

const btnPagamento = document.querySelector('#pagamento-btn')
btnPagamento.disabled = true

function habilitarPagamento() {
	let itemBloco = document.querySelector('.itembloco')

	if (itemBloco) {
		btnPagamento.disabled = false
	} else {
		btnPagamento.disabled = true
	}
}


