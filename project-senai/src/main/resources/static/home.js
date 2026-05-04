/* ── DADOS (buscados do backend) ── */
let produtos = [];
let produtosMapeados = {}; // Será preenchido com mapeamento de ID para dados UI

let carrinho = {};
let catAtual = 'todos';

/* ── MAPEAMENTO ESTÁTICO DE DADOS UI ── */
const dadosUI = {
    1: { emoji: '🍔', cat: 'lanche', bg: 'bg-rosa' },
    2: { emoji: '🍗', cat: 'lanche', bg: 'bg-amarelo' },
    3: { emoji: '🥪', cat: 'lanche', bg: 'bg-amarelo' },
    4: { emoji: '🥐', cat: 'lanche', bg: 'bg-laranja' },
    5: { emoji: '🍊', cat: 'bebida', bg: 'bg-laranja' },
    6: { emoji: '🥤', cat: 'bebida', bg: 'bg-azul' },
    7: { emoji: '🥛', cat: 'bebida', bg: 'bg-roxo' },
    8: { emoji: '💧', cat: 'bebida', bg: 'bg-azul' },
    9: { emoji: '🍮', cat: 'sobremesa', bg: 'bg-amarelo' },
    10: { emoji: '🍰', cat: 'sobremesa', bg: 'bg-rosa' }
};

/* ── CARREGAR PRODUTOS DO BACKEND ── */
function carregarProdutos() {
    fetch('/api/produtos')
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar produtos');
            return response.json();
        })
        .then(data => {
            produtos = data;
            // Mesclar produtos com dados UI
            produtosMapeados = {};
            produtos.forEach(p => {
                const ui = dadosUI[p.idProduto] || { emoji: '🍔', cat: 'lanche', bg: 'bg-rosa' };
                produtosMapeados[p.idProduto] = { ...p, ...ui, disponivel: true };
            });
            renderGrid();
            restaurarCarrinho();
        })
        .catch(error => {
            console.error('Erro:', error);
            // Fallback com dados locais em caso de erro
            usarDadosLocais();
        });
}

/* ── DADOS DE FALLBACK ── */
function usarDadosLocais() {
    const produtosLocais = [
        { idProduto:1,  nomeProduto:'X-Burguer',       descricaoProduto:'Pão, carne, queijo e alface',      preco:6.50, emoji:'🍔', bg:'bg-rosa',    cat:'lanche',    disponivel:true  },
        { idProduto:2,  nomeProduto:'Coxinha',          descricaoProduto:'Recheio de frango cremoso',         preco:3.50, emoji:'🍗', bg:'bg-amarelo', cat:'lanche',    disponivel:true  },
        { idProduto:3,  nomeProduto:'Pão na chapa',     descricaoProduto:'Com manteiga e queijo',            preco:2.50, emoji:'🥪', bg:'bg-amarelo', cat:'lanche',    disponivel:true  },
        { idProduto:4,  nomeProduto:'Pastel de queijo', descricaoProduto:'Massa crocante, queijo derretido', preco:4.00, emoji:'🥐', bg:'bg-laranja', cat:'lanche',    disponivel:true  },
        { idProduto:5,  nomeProduto:'Suco de laranja',  descricaoProduto:'Natural, 300ml',                   preco:4.00, emoji:'🍊', bg:'bg-laranja', cat:'bebida',    disponivel:true  },
        { idProduto:6,  nomeProduto:'Refrigerante',     descricaoProduto:'Lata 350ml gelada',                preco:3.00, emoji:'🥤', bg:'bg-azul',    cat:'bebida',    disponivel:true  },
        { idProduto:7,  nomeProduto:'Vitamina',         descricaoProduto:'Banana com leite e mel',           preco:4.50, emoji:'🥛', bg:'bg-roxo',    cat:'bebida',    disponivel:true  },
        { idProduto:8,  nomeProduto:'Água mineral',     descricaoProduto:'Garrafa 500ml',                    preco:2.00, emoji:'💧', bg:'bg-azul',    cat:'bebida',    disponivel:false },
        { idProduto:9,  nomeProduto:'Pudim',            descricaoProduto:'Pudim de leite condensado',        preco:3.50, emoji:'🍮', bg:'bg-amarelo', cat:'sobremesa', disponivel:true  },
        { idProduto:10, nomeProduto:'Bolo de cenoura',  descricaoProduto:'Com cobertura de chocolate',       preco:4.00, emoji:'🍰', bg:'bg-rosa',    cat:'sobremesa', disponivel:true  },
    ];
    produtosMapeados = {};
    produtosLocais.forEach(p => {
        produtosMapeados[p.idProduto] = p;
    });
    renderGrid();
}

/* ── RESTAURAR CARRINHO ── */
function restaurarCarrinho() {
    const carrinhoSalvo = sessionStorage.getItem('carrinho');
    if (carrinhoSalvo) {
        carrinho = JSON.parse(carrinhoSalvo);
        atualizarCartBar();
    }
}

/* ── DATA ── */
(function() {
    const dias   = ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'];
    const meses  = ['jan','fev','mar','abr','mai','jun','jul','ago','set','out','nov','dez'];
    const hoje   = new Date();
    document.getElementById('dataHoje').textContent =
        dias[hoje.getDay()] + ', ' + hoje.getDate() + ' de ' + meses[hoje.getMonth()];
})();

/* ── FILTROS ── */
function setCategoria(cat, el) {
    catAtual = cat;
    document.querySelectorAll('.filtro-btn').forEach(b => b.classList.remove('active'));
    el.classList.add('active');
    renderGrid();
}

function filtrar() {
    renderGrid();
}

/* ── RENDER ── */
function renderGrid() {
    const busca = document.getElementById('busca').value.toLowerCase();
    const grid  = document.getElementById('grid');
    const vazio = document.getElementById('vazio');
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
                    <span class="card-preco">R$ ${p.preco.toFixed(2).replace('.',',')}</span>
                    <button class="btn-add${qtd > 0 ? ' tem-qtd' : ''}"
                            onclick="addItem(${p.id})"
                            ${!p.disponivel ? 'disabled' : ''}>
                        ${qtd > 0 ? qtd + 'x' : '+'}
                    </button>
                </div>
            </div>`;
        grid.appendChild(card);
    });
}

/* ── CARRINHO ── */
function addItem(id) {
    carrinho[id] = (carrinho[id] || 0) + 1;
    salvarCarrinho();
    atualizarCartBar();
    renderGrid();
}

function atualizarCartBar() {
    let qtdTotal = 0, total = 0;
    Object.keys(carrinho).forEach(id => {
        const p = produtosMapeados[id];
        if (p) { qtdTotal += carrinho[id]; total += p.preco * carrinho[id]; }
    });

    document.getElementById('cartQtd').textContent   = qtdTotal;
    document.getElementById('cartTotal').textContent = 'R$ ' + total.toFixed(2).replace('.',',');
    document.getElementById('cartLabel').textContent  = qtdTotal === 1 ? 'item' : 'itens';
    document.getElementById('cartBar').classList.toggle('hidden', qtdTotal === 0);
}

function salvarCarrinho() {
    sessionStorage.setItem('carrinho', JSON.stringify(carrinho));
    sessionStorage.setItem('produtos', JSON.stringify(produtos));
}

/* ── INIT ── */
renderGrid();
