function atualizarCampo() {
    const tipoPagamento = document.querySelector(
      'input[name="tipoPagamento"]:checked'
    ).value;
    const campoCondicional = document.getElementById("campoCondicional");
    const campoLabel = document.getElementById("campoLabel");
    const campoDinamico = document.getElementById("campoDinamico");
    const valorContainer = document.getElementById("valorContainer");

    if (tipoPagamento === "PIX") {
      campoLabel.textContent = "Chave PIX";
      campoDinamico.placeholder = "Digite a chave";
      valorContainer.style.display = "block";
    } else if (tipoPagamento === "BOLETO") {
      campoLabel.textContent = "Código do Boleto";
      campoDinamico.placeholder = "Digite o código do boleto";
      valorContainer.style.display = "none";
    } else if (tipoPagamento === "TRANSFERENCIA") {
      campoLabel.textContent = "Código da Conta";
      campoDinamico.placeholder = "Digite o código da conta";
      valorContainer.style.display = "block";
    }

    campoCondicional.classList.add("active");
  }

  function confirmarPagamento() {
    const dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));
    if (!dadosUsuario || !dadosUsuario.id) {
      alert("Usuário não identificado. Faça login novamente.");
      return;
    }

    const idUsuario = dadosUsuario.id;
    const tipoPagamento = document.querySelector(
      'input[name="tipoPagamento"]:checked'
    ).value;
    const valor = document.getElementById("valor").value;
    const campoDinamico = document.getElementById("campoDinamico").value;

    if ((tipoPagamento !== "BOLETO" && !valor) || !campoDinamico) {
      alert("Por favor, preencha todos os campos.");
      return;
    }

    const payload = new URLSearchParams({
      idUsuario,
      tipoPagamento,
      valor: tipoPagamento === "BOLETO" ? 1 : valor,
      campoDinamico,
    });

    fetch("/pagamento/processar", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: payload.toString(),
    })
      .then((response) => response.json())
      .then((data) => {
        if (data.success) {
          alert(data.message);
          // Limpar os campos após o pagamento ser realizado
          document.getElementById("valor").value = "";
          document.getElementById("campoDinamico").value = "";
        } else {
          alert(`Erro: ${data.message}`);
        }
      })
      .catch((error) => console.error("Erro:", error));
  }