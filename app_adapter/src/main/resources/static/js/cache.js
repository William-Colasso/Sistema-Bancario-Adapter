function verificarLogin() {
    const usuarioLogado = localStorage.getItem('usuarioLogado');
    const dadosUsuario = JSON.parse(localStorage.getItem('dadosUsuario'));

    if (usuarioLogado === 'true' && dadosUsuario) {
        console.log('Usuário logado:', dadosUsuario);
        return true;
    } else {
        console.log('Usuário não está logado.');
        return false;
    }
}

window.onload = function () {
    if (!verificarLogin() && (window.location.pathname != '/cadastro' && window.location.pathname != '/')) {
        window.location.href = '/'; // Redireciona para o login
    }else if(verificarLogin() && (window.location.pathname == '/cadastro' || window.location.pathname == '/')){
        window.location.href = '/home'; // Redireciona para o login
    }
};
