

async function realizarLogin() {
    // Captura os valores dos campos de input
    const identificador = document.getElementById('identificador').value;
    const senha = document.getElementById('senha-login').value;

    try {
        // Verificando se os dados estão sendo capturados corretamente
        console.log(identificador, senha);

        // Formata os dados como x-www-form-urlencoded
        const formData = new URLSearchParams();
        formData.append('identificador', identificador);
        formData.append('senha', senha);

        // Realiza a requisição com os parâmetros no corpo
        const response = await fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded', // Define o cabeçalho correto
            },
            body: formData.toString() // Converte para string
        });

        // Verifica a resposta do servidor
        if (response.ok) {
            const dadosUsuario = await response.json();
            localStorage.setItem('usuarioLogado', 'true');
            localStorage.setItem('dadosUsuario', JSON.stringify(dadosUsuario));

            alert('Login realizado com sucesso!');
            window.location.href = '/home'; // Redireciona para a página inicial
        } else {
            alert('Credenciais inválidas. Tente novamente.');
        }

    } catch (error) {
        console.error('Erro ao realizar login:', error);
        alert('Erro ao realizar login. Tente novamente mais tarde.');
    }
}





function realizarLogout() {
    localStorage.removeItem('usuarioLogado');
    localStorage.removeItem('dadosUsuario');
    alert('Você foi desconectado.');
    window.location.href = '/';
}
