// Função para selecionar o perfil
function setTipo(valor) {
    document.getElementById('tipoUsuario').value = valor;
    const buttons = document.querySelectorAll('.btn-group button');
    buttons.forEach(btn => btn.classList.remove('active'));
    // Encontra o botão que foi clicado para dar o feedback visual
    event.target.classList.add('active');
}

// Lógica de Envio e Redirecionamento
document.querySelector('form').addEventListener('submit', function(e) {
    e.preventDefault(); // Impede o envio padrão

    const nome = document.querySelector('input[th\\:field="*{nome}"]')?.value ||
        document.querySelector('input[name="nome"]').value;

    // Criamos o objeto com os dados do formulário para o DTO
    const dados = {
        nome: nome,
        cpf: document.querySelector('input[name="cpf"]').value,
        email: document.querySelector('input[name="email"]').value,
        telefone: document.querySelector('input[name="telefone"]').value,
        tipoUsuario: document.getElementById('tipoUsuario').value
    };

    // Fazemos a chamada para o seu backend
    fetch('/api/usuarios/cadastrar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dados)
    })
        .then(response => {
            if (response.ok) {
                // SALVA O NOME NO NAVEGADOR
                sessionStorage.setItem('usuarioNome', dados.nome);

                // REDIRECIONA PARA A HOME
                window.location.href = '/home'; // Altere para a sua rota de home
            } else {
                alert("Erro ao cadastrar. Verifique os dados.");
            }
        })
        .catch(error => console.error('Erro:', error));
});