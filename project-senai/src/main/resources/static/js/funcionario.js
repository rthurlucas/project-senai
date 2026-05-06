/* ── DADOS MOCKADOS (substituir por dados do backend via Thymeleaf) ── */
let pedidos = [
    { id: '#1042', usuario: 'Breno Augusto', produto: 'X-Burguer', quantidade: 2, valor: 13.00, hora: '08:42', status: 'pendente', pagamento: 'Pix' },
    { id: '#1043', usuario: 'Lucas Rthur',   produto: 'Coxinha',   quantidade: 3, valor: 10.50, hora: '08:55', status: 'pendente', pagamento: 'Cartão' },
    { id: '#1044', usuario: 'Ana Lima',       produto: 'Pudim',     quantidade: 1, valor: 3.50,  hora: '09:10', status: 'pronto',   pagamento: 'Dinheiro' },
    { id: '#1045', usuario: 'Carlos Souza',   produto: 'Vitamina',  quantidade: 2, valor: 9.00,  hora: '09:22', status: 'entregue', pagamento: 'Pix' },
    { id: '#1046', usuario: 'Mariana Costa',  produto: 'Suco de laranja', quantidade: 1, valor: 4.00, hora: '09:35', status: 'pendente', pagamento: 'Saldo SENAI' },
];

let estoque = [
    { id: 1, nome: 'X-Burguer',       desc: 'Pão, carne, queijo e alface',      quantidade: 15 },
    { id: 2, nome: 'Coxinha',          desc: 'Recheio de frango cremoso',         quantidade: 30 },
    { id: 3, nome: 'Pão na chapa',     desc: 'Com manteiga e queijo',            quantidade: 3  },
    { id: 4, nome: 'Pastel de queijo', desc: 'Massa crocante, queijo derretido', quantidade: 0  },
    { id: 5, nome: 'Suco de laranja',  desc: 'Natural, 300ml',                   quantidade: 20 },
    { id: 6, nome: 'Refrigerante',     desc: 'Lata 350ml gelada',                quantidade: 8  },
    { id: 7, nome: 'Vitamina',         desc: 'Banana com leite e mel',           quantidade: 5  },
    { id: 8, nome: 'Pudim',            desc: 'Pudim de leite condensado',        quantidade: 0  },
    { id: 9, nome: 'Bolo de cenoura',  desc: 'Com cobertura de chocolate',       quantidade: 12 },
];

let statusFiltro = 'todos';
let estoqueEditandoId = null;

/* ── TABS ── */
function mostrarTab(id, el) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.getElementById('tab-' + id).classList.add('active');
    el.classList.add('active');
}

/* ── PEDIDOS ── */
function filtrarStatus(status, el) {
    statusFiltro = status;
    document.querySelectorAll('.filtro-status').forEach(b => b.classList.remove('active'));
    el.classList.add('active');
    renderPedidos();
}

function renderPedidos() {
    const busca  = document.getElementById('buscaPedido').value.toLowerCase();
    const lista  = document.getElementById('listaPedidos');
    const vazio  = document.getElementById('vazioPedidos');
    lista.innerHTML = '';

    const filtrados = pedidos.filter(p =>
        (statusFiltro === 'todos' || p.status === statusFiltro) &&
        (p.id.toLowerCase().includes(busca) || p.usuario.toLowerCase().includes(busca))
    );

    document.getElementById('countPedidos').textContent =
        pedidos.filter(p => p.status === 'pendente').length;

    vazio.style.display = filtrados.length === 0 ? 'block' : 'none';

    filtrados.forEach(p => {
        const card = document.createElement('div');
        card.className = `pedido-card ${p.status}`;

        const statusLabel = { pendente: 'Pendente', pronto: 'Pronto', entregue: 'Entregue' }[p.status];
        const statusClass = `status-${p.status}`;

        let botoes = '';
        if (p.status === 'pendente') {
            botoes = `<button class="btn-acao pronto" onclick="atualizarStatus('${p.id}', 'pronto')">Marcar como pronto</button>`;
        } else if (p.status === 'pronto') {
            botoes = `<button class="btn-acao entregar" onclick="atualizarStatus('${p.id}', 'entregue')">Confirmar entrega</button>`;
        } else {
            botoes = `<button class="btn-acao" disabled style="opacity:0.4;cursor:default">Entregue</button>`;
        }

        card.innerHTML = `
            <div class="pedido-head">
                <span class="pedido-num">${p.id}</span>
                <span class="pedido-status ${statusClass}">${statusLabel}</span>
            </div>
            <div class="pedido-body">
                <div class="pedido-item">
                    <strong>${p.usuario}</strong>
                </div>
                <div class="pedido-item">
                    ${p.produto} x${p.quantidade}
                    <span>${p.pagamento}</span>
                </div>
            </div>
            <div class="pedido-footer">
                <span class="pedido-meta">🕐 ${p.hora}</span>
                <span class="pedido-valor">R$ ${p.valor.toFixed(2).replace('.',',')}</span>
            </div>
            <div class="pedido-acoes">${botoes}</div>`;
        lista.appendChild(card);
    });
}

function atualizarStatus(id, novoStatus) {
    const p = pedidos.find(x => x.id === id);
    if (p) {
        p.status = novoStatus;
        renderPedidos();
        mostrarToast(`Pedido ${id} atualizado para "${novoStatus}"`);
    }
}

/* ── ESTOQUE ── */
function renderEstoque() {
    const busca  = document.getElementById('buscaEstoque').value.toLowerCase();
    const tbody  = document.getElementById('tabelaEstoque');
    const vazio  = document.getElementById('vazioEstoque');
    tbody.innerHTML = '';

    const filtrados = estoque.filter(e => e.nome.toLowerCase().includes(busca));
    vazio.style.display = filtrados.length === 0 ? 'block' : 'none';

    filtrados.forEach(e => {
        let qtdClass = 'qtd-ok', badgeClass = 'badge-ok', badgeLabel = 'Normal';
        if (e.quantidade === 0)  { qtdClass = 'qtd-zero'; badgeClass = 'badge-zero'; badgeLabel = 'Esgotado'; }
        else if (e.quantidade <= 5) { qtdClass = 'qtd-low';  badgeClass = 'badge-low';  badgeLabel = 'Baixo'; }

        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><p class="estoque-nome">${e.nome}</p></td>
            <td><p class="estoque-desc">${e.desc}</p></td>
            <td><span class="qtd-badge ${qtdClass}">${e.quantidade} un.</span></td>
            <td><span class="status-badge ${badgeClass}">${badgeLabel}</span></td>
            <td><button class="btn-editar" onclick="abrirModalEstoque(${e.id})">Editar qtd.</button></td>`;
        tbody.appendChild(tr);
    });
}

/* ── MODAL ESTOQUE ── */
function abrirModalEstoque(id) {
    const item = estoque.find(e => e.id === id);
    if (!item) return;
    estoqueEditandoId = id;
    document.getElementById('modalProdutoNome').textContent = item.nome;
    document.getElementById('inputQtd').value = item.quantidade;
    document.getElementById('modalEstoque').classList.add('show');
}

function salvarEstoque() {
    const item = estoque.find(e => e.id === estoqueEditandoId);
    if (!item) return;
    item.quantidade = parseInt(document.getElementById('inputQtd').value) || 0;
    fecharModal('modalEstoque');
    renderEstoque();
    mostrarToast('Estoque atualizado!');
}

/* ── MODAL PRODUTO ── */
function abrirModalProduto() {
    document.getElementById('inputNomeProduto').value = '';
    document.getElementById('inputDescProduto').value = '';
    document.getElementById('inputQtdProduto').value = '';
    document.getElementById('modalProduto').classList.add('show');
}

function salvarProduto() {
    const nome = document.getElementById('inputNomeProduto').value.trim();
    const desc = document.getElementById('inputDescProduto').value.trim();
    const qtd  = parseInt(document.getElementById('inputQtdProduto').value) || 0;
    if (!nome) return;

    estoque.push({ id: Date.now(), nome, desc, quantidade: qtd });
    fecharModal('modalProduto');
    renderEstoque();
    mostrarToast('Produto adicionado!');
}

/* ── UTILS ── */
function fecharModal(id) {
    document.getElementById(id).classList.remove('show');
}

function mostrarToast(msg) {
    const t = document.getElementById('toast');
    t.textContent = msg;
    t.classList.add('show');
    setTimeout(() => t.classList.remove('show'), 3000);
}

/* Fechar modal clicando fora */
document.querySelectorAll('.modal-overlay').forEach(m => {
    m.addEventListener('click', function(e) {
        if (e.target === this) this.classList.remove('show');
    });
});

/* ── INIT ── */
renderPedidos();
renderEstoque();