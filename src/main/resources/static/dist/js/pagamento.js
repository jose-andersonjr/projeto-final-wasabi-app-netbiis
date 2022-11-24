// =============================================== MODAL ======================================================== //

const modal = document.querySelector('.container-modal')

const switchModal = () => {
	const actualStyle = modal.style.display
	if (actualStyle == 'block') {
		modal.style.display = 'none'
	} else {
		modal.style.display = 'block'
	}
}


// =============================================== CHECAR RADIOBUTTON ======================================================== //

const areaDinheiro = document.querySelector('div#div-opcao-dinheiro')
areaDinheiro.addEventListener('click', function() {
	let radioBtnDinheiro = document.querySelector('input#opcao-dinheiro')
	radioBtnDinheiro.checked = true
	btnEnviarPedido.disabled = false
	divTroco.style.display = 'block'
})

const areaCredito = document.querySelector('div#div-opcao-credito')
areaCredito.addEventListener('click', function() {
	let radioBtnCredito = document.querySelector('input#opcao-credito')
	radioBtnCredito.checked = true
	btnEnviarPedido.disabled = false
	divTroco.style.display = 'none'
})

const areaDebito = document.querySelector('div#div-opcao-debito')
areaDebito.addEventListener('click', function() {
	let radioBtnDebito = document.querySelector('input#opcao-debito')
	radioBtnDebito.checked = true
	btnEnviarPedido.disabled = false
	divTroco.style.display = 'none'

})


// ================================================ GAVETA ============================================ //

const sideNav = document.getElementById('mySidenav')

function openNav() {
	sideNav.style.width = '18rem'
}

function closeNav() {
	sideNav.style.width = '0'
}

// ============================ ENVIAR PEDIDO ===========================================

const btnEnviarPedido = document.querySelector('#btn-enviar-pedido')
btnEnviarPedido.disabled = true
btnEnviarPedido.addEventListener('click', function() {
	switchModal()
})

// ========================== HABILITAR TROCO ================
const divTroco = document.querySelector('#div-pagamento-escolhido')
const inputTroco = document.querySelector('#input-troco')
divTroco.style.display = 'none'
inputTroco.addEventListener('keypress', (e) => {
	const numeros = /[0-9]/
	const key = String.fromCharCode(e.keyCode)

	if (!numeros.test(key)) {
		e.preventDefault()
		return
	}
})


// ===================== ATUALIZAR VALOR PEDIDO  ==================
function round(num) {
	return +(Math.round(num + 'e+2') + 'e-2')
}

function atualizarValorPedido() { //  Somando total da lista de pedido
	let valorTotalPedido = 10;
	var itemsLista = document.getElementsByClassName('container-produto-pedido')
	for (let item of itemsLista) {
		let valorSubtotal = item.querySelector('#itemsubtotal').textContent
		valorSubtotal = valorSubtotal.replace('Subtotal: R$ ', '')
		valorSubtotal = round(valorSubtotal)
		valorTotalPedido = valorTotalPedido + valorSubtotal
		valorSubtotal = 'Subtotal: R$ ' + valorSubtotal.toFixed(2).toString(10).replace('.', ',')
		item.querySelector('#itemsubtotal').innerText = valorSubtotal

	}
	valorTotalPedido = round(valorTotalPedido).toFixed(2).toString(10)
	valorTotalPedido = valorTotalPedido.replace('.', ',')
	let totalPedidoCarrinho = document.querySelector('#totalpedidocarrinho')
	totalPedidoCarrinho.innerText = valorTotalPedido

}
// =========================== gerar troco ==============================
function gerarTroco() {
	let inputTroco = document.querySelector('#input-troco').value
	let divTroco = document.createElement('div')
	divTroco.classList.add('novoTroco')
	let totalPedidoCarrinho = document.querySelector('#totalpedidocarrinho').textContent

	totalPedidoCarrinho = round(totalPedidoCarrinho.replace('R$', '').replace(',', '.'))

	if (inputTroco > totalPedidoCarrinho) {
		let pgmtEscolhido = document.querySelector('#div-pagamento-escolhido')
		pgmtEscolhido.innerHTML = ''
		valorTroco = inputTroco - totalPedidoCarrinho
		valorTroco = valorTroco.toFixed(2)
		divTroco.textContent = 'Troco para o cliente: R$' + valorTroco
		
		pgmtEscolhido.appendChild(divTroco)
	} else {
		alert('Favor insira um valor de pagamento maior que o custo do pedido')
	}

}
atualizarValorPedido()