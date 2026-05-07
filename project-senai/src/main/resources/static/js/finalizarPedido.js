let carrinho = JSON.parse(sessionStorage.getItem('carrinho') || '{}');
let produtosMapeados = {};

/* ── CARREGAR PRODUTOS DO BACKEND ── */
function carregarProdutos() {
    fetch('/api/produtos')
        .then(response => {
            if (!response.ok) throw new Error('Erro ao carregar produtos');
            return response.json();
        })
        .then(data => {
            produtosMapeados = {};
            data.forEach(p => {
                // Mapeia usando idProduto ou id (verifique seu DTO)
                produtosMapeados[p.idProduto || p.id] = p;
            });
            render();
        })
        .catch(error => {
            console.error('Erro na API, usando dados locais:', error);
            usarDadosLocais();
        });
}

function fmt(v) {
    return v ? 'R$ ' + v.toFixed(2).replace('.', ',') : 'R$ 0,00';
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
        document.getElementById('qtdBadge').textContent = '0 itens';
        document.getElementById('totalValor').textContent = 'R$ 0,00';
        document.getElementById('btnConfirmar').disabled = true;
        return;
    }

    let total = 0;
    let totalItens = 0;

    ids.forEach(id => {
        const p = produtosMapeados[id];
        if (!p) return;

        const qtd = carrinho[id];
        const sub = p.preco * qtd;
        total += sub;
        totalItens += qtd;

        // Resumo lateral
        resumo.innerHTML += `
            <div class="resumo-linha">
                <span class="resumo-label">${p.nome || p.nomeProduto} x${qtd}</span>
                <span class="resumo-valor">${fmt(sub)}</span>
            </div>`;

        // Lista principal
        lista.innerHTML += `
            <div class="item-row">
                <div class="item-info">
                    <p class="item-nome">${p.nome || p.nomeProduto}</p>
                    <p class="item-sub">${fmt(p.preco)} / unid.</p>
                </div>
                <div class="item-controles">
                    <button class="ctrl-btn" onclick="alterar(${id}, -1)">−</button>
                    <span class="item-qtd">${qtd}</span>
                    <button class="ctrl-btn" onclick="alterar(${id}, 1)">+</button>
                </div>
                <span class="item-preco">${fmt(sub)}</span>
            </div>`;
    });

    document.getElementById('qtdBadge').textContent = `${totalItens} ${totalItens === 1 ? 'item' : 'itens'}`;
    document.getElementById('totalValor').textContent = fmt(total);
    document.getElementById('btnConfirmar').disabled = false;
}

function alterar(id, delta) {
    carrinho[id] = (carrinho[id] || 0) + delta;
    if (carrinho[id] <= 0) delete carrinho[id];
    sessionStorage.setItem('carrinho', JSON.stringify(carrinho));
    render();
}

function setPag(el) {
    document.querySelectorAll('.pag-btn').forEach(b => b.classList.remove('selected'));
    el.classList.add('selected');
}

/* ── CONFIRMAR PEDIDO ── */
function confirmar() {
    const pagamento = document.querySelector('.pag-btn.selected .pag-label').textContent;
    const obs = document.querySelector('.obs-input').value;

    const itens = Object.keys(carrinho).map(id => ({
        produtoId: parseInt(id),
        quantidade: carrinho[id],
        pagamento: pagamento,
        observacao: obs
    }));

    // Envia o primeiro item (ou ajuste para enviar a lista toda se sua API suportar)
    fetch('/api/pedidos', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(itens[0])
    })
        .then(res => {
            if(!res.ok) throw new Error();
            document.getElementById('modalSucesso').classList.add('show');
            sessionStorage.removeItem('carrinho');
        })
        .catch(() => alert("Erro ao enviar pedido ao funcionário."));
}

// Inicialização
carregarProdutos();