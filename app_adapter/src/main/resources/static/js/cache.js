/*// Verifica se o usuário está logado
function verificarLogin() {
    const usuarioLogado = localStorage.getItem('usuarioLogado');
    const dadosUsuario = localStorage.getItem('dadosUsuario');

    if (!usuarioLogado || !dadosUsuario) {
        // Se não existir, cria variáveis no localStorage
        localStorage.setItem('usuarioLogado', 'false');
        localStorage.setItem('dadosUsuario', JSON.stringify({}));

        // Redireciona para a página de criação de conta
        window.location.href = '/geral.html';
    } else {
        const logado = localStorage.getItem('usuarioLogado') === 'true';
        
        if (!logado) {
            // Caso não esteja logado, redireciona
            window.location.href = '/geral.html';
        }
    }
}

// Chama a função para verificar o estado do login
verificarLogin();*/
