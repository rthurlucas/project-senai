let carrinho = JSON.parse(sessionStorage.getItem('carrinho') || '{}');
let produtos  = [];
let produtosMapeados = {};
let idPedidoCriado = null;

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
            render();
        })
        .catch(error => {
            console.error('Erro ao carregar produtos:', error);
            // Tentar usar dados locais como fallback
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
    render();
}

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
        const p = produtosMapeados[id];
        if (!p) return;

        const qtd = carrinho[id];
        const sub = p.preco * qtd;
        total      += sub;
        totalItens += qtd;

        /* Linha no resumo */
        const rl = document.createElement('div');
        rl.className = 'resumo-linha';
        rl.innerHTML = `
            <span class="resumo-label">${p.nomeProduto} x${qtd}</span>
            <span class="resumo-valor">${fmt(sub)}</span>`;
        resumo.appendChild(rl);

        /* Item na lista */
        const row = document.createElement('div');
        row.className = 'item-row';
        row.innerHTML = `
            <div class="item-emoji">${p.emoji}</div>
            <div class="item-info">
                <p class="item-nome">${p.nomeProduto}</p>
                <p class="item-sub">${fmt(p.preco)} / unid.</p>
            </div>
            <div class="item-controles">
                <button class="ctrl-btn remover" onclick="alterar(${p.idProduto}, -1)">−</button>
                <span class="item-qtd">${qtd}</span>
                <button class="ctrl-btn" onclick="alterar(${p.idProduto}, +1)">+</button>
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
    const pagamentoSelecionado = document.querySelector('.pag-btn.selected .pag-label').textContent;
    const observacoes = document.querySelector('.obs-input').value;

    // Montar dados dos itens do pedido
    const itens = Object.keys(carrinho)
        .filter(id => carrinho[id] > 0)
        .map(id => ({
            idProduto: parseInt(id),
            quantidade: carrinho[id]
        }));

    // Criar múltiplos pedidos, um para cada item
    let pedidosEnviados = 0;
    let erros = 0;

    itens.forEach(item => {
        const dadosPedido = {
            idProduto: item.idProduto,
            quantidade: item.quantidade
        };

        fetch('/api/pedidos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(dadosPedido)
        })
        .then(response => {
            if (!response.ok) throw new Error('Erro ao criar pedido');
            return response.json();
        })
        .then(data => {
            pedidosEnviados++;
            idPedidoCriado = data.idPedido;

            if (pedidosEnviados === itens.length) {
                // Todos os pedidos foram enviados com sucesso
                const num = '#' + String(Math.floor(Math.random() * 9000) + 1000);
                document.getElementById('numPedido').textContent = num;
                document.getElementById('modalSucesso').classList.add('show');
                sessionStorage.removeItem('carrinho');
            }
        })
        .catch(error => {
            console.error('Erro ao criar pedido:', error);
            erros++;
            if (erros + pedidosEnviados === itens.length) {
                alert('Erro ao processar alguns pedidos. Tente novamente.');
            }
        });
    });
}

/* ── INIT ── */
carregarProdutos();
