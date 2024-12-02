function verificarLogin() {
  const usuarioLogado = localStorage.getItem("usuarioLogado");
  const dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));

  if (usuarioLogado === "true" && dadosUsuario) {
    console.log("Usuário logado:", dadosUsuario);
    return true;
  } else {
    console.log("Usuário não está logado.");
    return false;
  }
}

window.onload = function () {
  if (
    !verificarLogin() &&
    window.location.pathname != "/cadastro" &&
    window.location.pathname != "/"
  ) {
    window.location.href = "/"; // Redireciona para o login
  } else if (
    verificarLogin() &&
    (window.location.pathname == "/cadastro" || window.location.pathname == "/")
  ) {
    window.location.href = "/home"; // Redireciona para o login
  }
};



async function atualizarSaldo() {
  var dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));

  console.log(dadosUsuario)
  var body;

  if (dadosUsuario.id) {
     body = {
      id: dadosUsuario.id,
    };
  }

  var saldos = document.querySelectorAll(".saldo");

  var saldosF = document.querySelector(".saldo");

  if (saldosF) {
    try {
      // Envia os dados atualizados para a rota POST
      const response = await fetch(`/pagamento/saldo`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
      });

      // Verifica se a resposta foi bem-sucedida
      const data = await response.json();

      if (!response.ok) {
        // Exibe a mensagem de erro retornada pela API
        alert(data.message || "Erro ao buscar saldo");
        return;
      }

      // Atualiza o saldo no DOM
      const saldoAtualizado = data.saldo;
      saldos.forEach((saldo) => {
        saldo.innerText = `Saldo R$${saldoAtualizado.toFixed(2)}`;
      });

    } catch (error) {
      console.error(error);
      
    }
  }
}



atualizarSaldo()
setInterval(atualizarSaldo, 2000);
