$(document).ready(function () {

    var input = $('#cpf-email');
    var select = $('#cpf-email-select');



    // Aplica a máscara de CPF inicialmente
    input.mask('000.000.000-00', { placeholder: '_._._-__' });

    // Detecta mudança no select e aplica a máscara ou libera o input
    select.change(function () {
        var selectedOption = select.val();

        if (selectedOption === 'cpf') {
            input.mask('000.000.000-00');  // Aplica a máscara de CPF
            input.val('');  // Limpa o valor caso tenha sido digitado um e-mail
        } else {
            input.unmask();  // Remove a máscara
        }
    });

    // Detecta se o campo de input contém '@', e se for e-mail, remove a máscara
    input.on('input', function () {
        var value = input.val();

        if (value.includes('@')) {
            input.unmask();  // Remove a máscara para e-mail
        } else if (select.val() === 'cpf') {
            // Se for CPF, aplica a máscara de CPF
            input.mask('000.000.000-00');
        }
    });

    
});

