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
areaDinheiro.addEventListener('click', function () {
  let radioBtnDinheiro = document.querySelector('input#opcao-dinheiro')
  radioBtnDinheiro.checked = true
  btnEnviarPedido.disabled=false
  divTroco.style.display = 'block'
})

const areaCredito = document.querySelector('div#div-opcao-credito')
areaCredito.addEventListener('click', function () {
  let radioBtnCredito = document.querySelector('input#opcao-credito')
  radioBtnCredito.checked = true
  btnEnviarPedido.disabled=false
  divTroco.style.display = 'none'
})

const areaDebito = document.querySelector('div#div-opcao-debito')
areaDebito.addEventListener('click', function () {
  let radioBtnDebito = document.querySelector('input#opcao-debito')
  radioBtnDebito.checked = true
  btnEnviarPedido.disabled=false
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
btnEnviarPedido.disabled=true
btnEnviarPedido.addEventListener('click', function () {
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


// ===================== TOAST ==================
const formularioPagamento = document.querySelector('#formulario-pagamento')
formularioPagamento.addEventListener('submit', switchModal())