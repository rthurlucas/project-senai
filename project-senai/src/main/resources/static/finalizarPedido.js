let carrinho = JSON.parse(sessionStorage.getItem('carrinho') || '{}');
let produtos  = JSON.parse(sessionStorage.getItem('produtos') || '[]');

function fmt(v) {
    return 'R$ ' + v.toFixed(2).replace('.', ',');
}

/* ── RENDER ── */
function render() {
    const lista  = document.getElementById('listaItens');
    const resumo = document.getElementById('resumoLinhas');
    lista.innerHTML  = '';
    resumo.innerHTML = '';

    const ids = Object.keys(carrinho).filter(id => carrinho[id] > 0);

    if (ids.length === 0) {
        lista.innerHTML = '<p class="vazio-msg">Nenhum item no carrinho.</p>';
        document.getElementById('qtdBadge').textContent    = '0 itens';
        document.getElementById('totalValor').textContent  = 'R$ 0,00';
        document.getElementById('btnConfirmar').disabled   = true;
        return;
    }

    let total = 0, totalItens = 0;

    ids.forEach(id => {
        const p = produtos.find(x => x.id == id);
        if (!p) return;

        const qtd = carrinho[id];
        const sub = p.preco * qtd;
        total      += sub;
        totalItens += qtd;

        /* Linha no resumo */
        const rl = document.createElement('div');
        rl.className = 'resumo-linha';
        rl.innerHTML = `
            <span class="resumo-label">${p.nome} x${qtd}</span>
            <span class="resumo-valor">${fmt(sub)}</span>`;
        resumo.appendChild(rl);

        /* Item na lista */
        const row = document.createElement('div');
        row.className = 'item-row';
        row.innerHTML = `
            <div class="item-emoji">${p.emoji}</div>
            <div class="item-info">
                <p class="item-nome">${p.nome}</p>
                <p class="item-sub">${fmt(p.preco)} / unid.</p>
            </div>
            <div class="item-controles">
                <button class="ctrl-btn remover" onclick="alterar(${p.id}, -1)">−</button>
                <span class="item-qtd">${qtd}</span>
                <button class="ctrl-btn" onclick="alterar(${p.id}, +1)">+</button>
            </div>
            <span class="item-preco">${fmt(sub)}</span>`;
        lista.appendChild(row);
    });

    document.getElementById('qtdBadge').textContent   = totalItens + (totalItens === 1 ? ' item' : ' itens');
    document.getElementById('totalValor').textContent  = fmt(total);
    document.getElementById('btnConfirmar').disabled   = false;
}

/* ── ALTERAR QUANTIDADE ── */
function alterar(id, delta) {
    carrinho[id] = (carrinho[id] || 0) + delta;
    if (carrinho[id] <= 0) delete carrinho[id];
    sessionStorage.setItem('carrinho', JSON.stringify(carrinho));
    render();
}

/* ── PAGAMENTO ── */
function setPag(el) {
    document.querySelectorAll('.pag-btn').forEach(b => b.classList.remove('selected'));
    el.classList.add('selected');
}

/* ── CONFIRMAR ── */
function confirmar() {
    const num = '#' + String(Math.floor(Math.random() * 9000) + 1000);
    document.getElementById('numPedido').textContent = num;
    document.getElementById('modalSucesso').classList.add('show');
    sessionStorage.removeItem('carrinho');
}

/* ── INIT ── */
render();