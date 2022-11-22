// ================================================== MODAL =========================================== //
const modal = document.querySelector('.container-modal')

const switchModal = () => {
	const actualStyle = modal.style.display
	if (actualStyle == 'block') { // LIMPANDO AS DIVS AO FECHAR O MODAL
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
	habilitarPagamento();
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
	cardsProdutos[i].addEventListener('click', function(event) {
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



function round(num) {
	return +(Math.round(num + 'e+2') + 'e-2')
}

// ====== somar subtotal do modal e valor total da lista pedido ===============

function alterarValorTotal(event) {//Somando subtotal modal
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



function atualizarValorPedido() { //  Somando total da lista de pedido
	let valorTotalPedido = 10;
	var itemsLista = document.getElementsByClassName('itembloco')
	for (let item of itemsLista) {
		let valorSubtotal = item.querySelector('.valor-unitario').textContent
		valorSubtotal = valorSubtotal.replace('Preço: R$ ', '')
		//let quantidade = item.querySelector('#itemquantidade').textContent
		//quantidade = quantidade.replace('Quantidade: ', '')
		//let somatorio = round(valorUnitario * quantidade)
		valorSubtotal = round(valorSubtotal)
		valorTotalPedido = valorTotalPedido + valorSubtotal
		valorSubtotal = valorSubtotal.toString(10)
		item.querySelector('.valor-unitario').innerText = 'Preço: R$ ' + valorSubtotal.replace('.', ',')

	}
	valorTotalPedido = round(valorTotalPedido).toFixed(2).toString(10)
	valorTotalPedido = valorTotalPedido.replace('.', ',')
	let totalPedidoCarrinho = document.querySelector('#totalpedidocarrinho')
	totalPedidoCarrinho.innerText = valorTotalPedido

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


habilitarPagamento()
atualizarValorPedido()
















