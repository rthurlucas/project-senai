/* ── DADOS (buscados do backend) ── */
let produtos = [];
let produtosMapeados = {};
let carrinho = {};
let catAtual = 'todos';

/* ── CARREGAR PRODUTOS DO BACKEND ── */
function carregarProdutos() {
    fetch('/api/produtos') // Certifique-se de que esta URL é a mesma do seu @RestController no Java
        .then(response => {
            if (!response.ok) throw new Error('Erro na rede ou endpoint não encontrado');
            return response.json();
        })
        .then(data => {
            produtosMapeados = {};
            data.forEach(p => {
                produtosMapeados[p.idProduto] = {
                    ...p,
                    emoji: '🍔', // Enfeite padrão, já que não temos imagem no banco
                    bg: 'bg-rosa',
                    cat: 'todos',
                    disponivel: true
                };
            });
            restaurarCarrinho();
            renderGrid();
        })
        .catch(error => {
            console.error('Falha ao buscar produtos do backend:', error);
            // Removi os dados falsos. Se ficar vazio, é porque o Java não retornou nada!
        });
}

/* ── RENDERIZAÇÃO DA TELA ── */
function renderGrid() {
    const busca = document.getElementById('busca').value.toLowerCase();
    const grid  = document.getElementById('grid');
    const vazio = document.getElementById('vazio');

    if (!grid) return;
    grid.innerHTML = '';

    const todosProdutos = Object.values(produtosMapeados);
    const filtrados = todosProdutos.filter(p =>
        (catAtual === 'todos' || p.cat === catAtual) &&
        p.nomeProduto.toLowerCase().includes(busca)
    );

    vazio.style.display = filtrados.length === 0 ? 'block' : 'none';

    filtrados.forEach(p => {
        const qtd  = carrinho[p.idProduto] || 0;
        const card = document.createElement('div');
        card.className = 'food-card' + (p.disponivel ? '' : ' esgotado');

        const controles = qtd > 0
            ? `<div class="card-controles">
                   <button class="btn-ctrl btn-menos" onclick="removeItem(${p.idProduto})">−</button>
                   <span class="card-qtd">${qtd}</span>
                   <button class="btn-ctrl btn-mais" onclick="addItem(${p.idProduto})">+</button>
               </div>`
            : `<button class="btn-add" onclick="addItem(${p.idProduto})" ${!p.disponivel ? 'disabled' : ''}>+</button>`;

        // O preço está de enfeite (R$ --) já que não existe no banco
        card.innerHTML = `
        <div class="card-img ${p.bg}">
            <span style="font-size:38px">${p.emoji}</span>
            <span class="badge ${p.disponivel ? 'badge-ok' : 'badge-nao'}">
                ${p.disponivel ? 'Disponível' : 'Esgotado'}
            </span>
        </div>
        <div class="card-body">
            <p class="card-nome">${p.nomeProduto}</p>
            <p class="card-desc">${p.descricaoProduto}</p>
            <div class="card-footer">
                <span class="card-preco">R$ --</span>
                ${controles}
            </div>
        </div>`;

        grid.appendChild(card);
    }); // Faltava fechar o forEach aqui no seu código original!
}

/* ── LÓGICA DO CARRINHO ── */
function addItem(id) {
    carrinho[id] = (carrinho[id] || 0) + 1;
    salvarCarrinho();
    atualizarCartBar();
    renderGrid();
}

function removeItem(id) {
    if (!carrinho[id]) return;
    carrinho[id]--;
    if (carrinho[id] === 0) delete carrinho[id];
    salvarCarrinho();
    atualizarCartBar();
    renderGrid();
}

function atualizarCartBar() {
    let qtdTotal = 0;

    // Conta apenas a quantidade de itens, sem tentar somar dinheiro inexistente
    Object.keys(carrinho).forEach(id => {
        qtdTotal += carrinho[id];
    });

    const cartBar = document.getElementById('cartBar');
    if (cartBar) {
        document.getElementById('cartQtd').textContent = qtdTotal;

        // Proteção para evitar erro caso não tenha o elemento cartTotal no HTML
        const elTotal = document.getElementById('cartTotal');
        if (elTotal) elTotal.textContent = 'R$ --';

        document.getElementById('cartLabel').textContent = qtdTotal === 1 ? 'item' : 'itens';
        cartBar.classList.toggle('hidden', qtdTotal === 0);

        // 👇 AQUI ESTÁ A MÁGICA DO REDIRECIONAMENTO 👇
        // Se a barra do carrinho estiver visível e o usuário clicar nela, vai para a outra página
        cartBar.onclick = function() {
            // Mude 'finalizar.html' para o nome exato do seu arquivo HTML de destino
            window.location.href = '/pedido/finalizar';
        };
        // Para garantir que o cursor vire uma mãozinha indicando que é clicável:
        cartBar.style.cursor = 'pointer';
    }
}

function salvarCarrinho() {
    sessionStorage.setItem('carrinho', JSON.stringify(carrinho));
}

function restaurarCarrinho() {
    const carrinhoSalvo = sessionStorage.getItem('carrinho');
    if (carrinhoSalvo) {
        carrinho = JSON.parse(carrinhoSalvo);
        atualizarCartBar();
    }
}

// Inicialização das datas
(function() {
    const dias   = ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'];
    const meses  = ['jan','fev','mar','abr','mai','jun','jul','ago','set','out','nov','dez'];
    const hoje   = new Date();
    const elData = document.getElementById('dataHoje');
    if (elData) {
        elData.textContent = dias[hoje.getDay()] + ', ' + hoje.getDate() + ' de ' + meses[hoje.getMonth()];
    }
})();

function setCategoria(cat, el) {
    catAtual = cat;
    document.querySelectorAll('.filtro-btn').forEach(b => b.classList.remove('active'));
    el.classList.add('active');
    renderGrid();
}

function filtrar() {
    renderGrid();
}

function carregarNomeUsuario() {
    const nomeSalvo = sessionStorage.getItem('usuarioNome');
    const elNome = document.getElementById('nomeUsuarioTopo');

    if (nomeSalvo && elNome) {
        // Pega apenas o primeiro nome para não ficar muito grande no topo
        const primeiroNome = nomeSalvo.split(' ')[0];
        elNome.textContent = primeiroNome;
    }
}

// Chame essa função assim que a página carregar
carregarNomeUsuario();

/* ── START ── */
carregarProdutos();

// Atualiza os produtos a cada 10 segundos
setInterval(carregarProdutos, 10000);