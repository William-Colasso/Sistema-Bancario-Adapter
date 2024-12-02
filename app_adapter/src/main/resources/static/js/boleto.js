// Função para copiar o ID do boleto para a área de transferência

// Pega o ID do cliente do localStorage e preenche o campo oculto do formulário
document.addEventListener("DOMContentLoaded", function () {
  const dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));
  if (dadosUsuario) {
    // Preenche o campo 'idCliente' com o ID do cliente
    document.getElementById("idCliente").value = dadosUsuario.id;
  }
});

// Seleciona todos os botões com a classe "btn-copy"
var botoesCopy = document.querySelectorAll(".btn-copy");

// Itera sobre cada botão
botoesCopy.forEach((botao) => {
  botao.addEventListener("click", () => {
    // Copia o texto do botão (ou outro atributo, conforme necessário)
    navigator.clipboard
      .writeText(botao.value || botao.value)
      .then(() => {
        console.log("Texto copiado: " + botao.value);
        botao.style.backgroundColor = "#88EE88";
        botao.style.color = "#000000";
        botao.innerText = "Copiado!";
      })
      .catch((err) => {
        console.error("Erro ao copiar texto:", err);
      });
  });
});


setInterval(() => {
  botoesCopy.forEach((botao) => {
    botao.style.backgroundColor = "#E0A800";
    botao.style.color = "#FFF";
    botao.innerText = "Copiar";
    console.log("Aqui");
  });
}, 5000);