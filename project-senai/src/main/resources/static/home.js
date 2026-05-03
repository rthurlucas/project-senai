/* ── DADOS (substituir por dados vindos do backend via Thymeleaf) ── */
const produtos = [
    { id:1,  nome:'X-Burguer',       desc:'Pão, carne, queijo e alface',      preco:6.50, emoji:'🍔', bg:'bg-rosa',    cat:'lanche',    disponivel:true  },
    { id:2,  nome:'Coxinha',          desc:'Recheio de frango cremoso',         preco:3.50, emoji:'🍗', bg:'bg-amarelo', cat:'lanche',    disponivel:true  },
    { id:3,  nome:'Pão na chapa',     desc:'Com manteiga e queijo',            preco:2.50, emoji:'🥪', bg:'bg-amarelo', cat:'lanche',    disponivel:true  },
    { id:4,  nome:'Pastel de queijo', desc:'Massa crocante, queijo derretido', preco:4.00, emoji:'🥐', bg:'bg-laranja', cat:'lanche',    disponivel:true  },
    { id:5,  nome:'Suco de laranja',  desc:'Natural, 300ml',                   preco:4.00, emoji:'🍊', bg:'bg-laranja', cat:'bebida',    disponivel:true  },
    { id:6,  nome:'Refrigerante',     desc:'Lata 350ml gelada',                preco:3.00, emoji:'🥤', bg:'bg-azul',    cat:'bebida',    disponivel:true  },
    { id:7,  nome:'Vitamina',         desc:'Banana com leite e mel',           preco:4.50, emoji:'🥛', bg:'bg-roxo',    cat:'bebida',    disponivel:true  },
    { id:8,  nome:'Água mineral',     desc:'Garrafa 500ml',                    preco:2.00, emoji:'💧', bg:'bg-azul',    cat:'bebida',    disponivel:false },
    { id:9,  nome:'Pudim',            desc:'Pudim de leite condensado',        preco:3.50, emoji:'🍮', bg:'bg-amarelo', cat:'sobremesa', disponivel:true  },
    { id:10, nome:'Bolo de cenoura',  desc:'Com cobertura de chocolate',       preco:4.00, emoji:'🍰', bg:'bg-rosa',    cat:'sobremesa', disponivel:true  },
];

let carrinho = {};
let catAtual = 'todos';

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

    const filtrados = produtos.filter(p =>
        (catAtual === 'todos' || p.cat === catAtual) &&
        p.nome.toLowerCase().includes(busca)
    );

    vazio.style.display = filtrados.length === 0 ? 'block' : 'none';

    filtrados.forEach(p => {
        const qtd  = carrinho[p.id] || 0;
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
                <p class="card-nome">${p.nome}</p>
                <p class="card-desc">${p.desc}</p>
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
        const p = produtos.find(x => x.id == id);
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