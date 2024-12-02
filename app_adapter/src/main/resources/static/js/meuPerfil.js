// Função para aplicar máscara no campo CPF
document.getElementById("cpf").addEventListener("input", function (e) {
    let value = e.target.value.replace(/\D/g, ""); // Remove tudo que não é número
    if (value.length > 11) value = value.slice(0, 11); // Limita o tamanho a 11 caracteres

    // Adiciona os pontos e o traço na formatação
    const formatted = value
      .replace(/(\d{3})(\d)/, "$1.$2") // Primeiro ponto
      .replace(/(\d{3})(\d)/, "$1.$2") // Segundo ponto
      .replace(/(\d{3})(\d{1,2})$/, "$1-$2"); // Traço

    e.target.value = formatted;
  });

  let firstTime = false;

  // Função para alternar a visibilidade da senha
  document
    .getElementById("eye-icon")
    .addEventListener("click", function () {
      const senhaInput = document.getElementById("senha");
      const eyeIcon = document.getElementById("eye-icon");

      // Se a senha estiver oculta, mostra e altera o ícone
      if (senhaInput.type === "password") {
        senhaInput.type = "text";
        eyeIcon.textContent = "🙈"; // Altera para "olho fechado"
      } else {
        senhaInput.type = "password";
        eyeIcon.textContent = "👁️"; // Altera para "olho aberto"
      }
    });


    
  async function getPerfil() {
    const dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));
    if (!dadosUsuario) {
      console.error("Usuário não encontrado no localStorage");
      return;
    }

    const id = dadosUsuario.id; // Pegando o id do cliente do localStorage

    try {
      // Faz a requisição GET para obter os dados do cliente passando o id como parâmetro na URL
      const response = await fetch(`/perfil/${id}`);

      if (!response.ok) {
        throw new Error("Erro ao carregar dados do perfil");
      }

      const data = await response.json();

      // Atualiza os dados no localStorage
      localStorage.setItem("dadosUsuario", JSON.stringify(data));

      // Preenche os campos do formulário com os dados recebidos

      if (!firstTime) {
        document.getElementById("nome").value = data.nome;
        document.getElementById("cpf").value = data.cpf;
        document.getElementById("email").value = data.email;
        document.getElementById("telefone").value = data.telefone;
        document.getElementById("senha").value = data.senha;
        document.getElementById("numeroConta").textContent = data.id;
      }
      firstTime = true;
      document.getElementById("saldo").textContent = `R$ ${data.saldo}`;

      // Marcar as checkboxes com base nas chaves Pix
      if (data.chavesPix.includes(data.cpf)) {
        document.getElementById("chaveCpf").checked = true;
      }
      if (data.chavesPix.includes(data.email)) {
        document.getElementById("chaveEmail").checked = true;
      }
      if (data.chavesPix.includes(data.telefone)) {
        document.getElementById("chaveTelefone").checked = true;
      }
    } catch (error) {
      console.error(error);
    }
  }

  // Função para atualizar os dados do perfil
  async function atualizarPerfil(event) {
    event.preventDefault(); // Previne o comportamento padrão de envio do formulário

    const dadosUsuario = JSON.parse(localStorage.getItem("dadosUsuario"));
    if (!dadosUsuario) {
      console.error("Usuário não encontrado no localStorage");
      return;
    }

    const id = dadosUsuario.id; // Pegando o id do cliente do localStorage

    // Obtém os novos valores do formulário
    const nome = document.getElementById("nome").value;
    const cpf = document.getElementById("cpf").value;
    const email = document.getElementById("email").value;
    const senha = document.getElementById("senha").value;
    const telefone = document.getElementById("telefone").value;

    // Verifica qual checkbox está selecionado para definir a chave Pix
    let chavePix = [];
    if (document.getElementById("chaveCpf").checked) chavePix.push(cpf);
    if (document.getElementById("chaveEmail").checked) chavePix.push(email);
    if (document.getElementById("chaveTelefone").checked)
      chavePix.push(telefone);

    // Cria o objeto com os dados atualizados
    const dadosAtualizados = {
      id,
      nome,
      cpf,
      email,
      senha,
      telefone,
      chavesPix: chavePix, // Adiciona a lista de chaves Pix
    };

    try {
      // Envia os dados atualizados para a rota POST
      const response = await fetch("/perfil", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(dadosAtualizados),
      });

      if (!response.ok) {
        throw new Error("Erro ao atualizar perfil");
      }

      // Exibe uma mensagem de sucesso
      alert("Perfil atualizado com sucesso!");
    } catch (error) {
      console.error(error);
      alert("Erro ao atualizar perfil");
    }
  }

  // Chama a função getPerfil para carregar os dados do perfil
  getPerfil();

  // Adiciona o evento de envio do formulário
  document
    .getElementById("perfilForm")
    .addEventListener("submit", atualizarPerfil);