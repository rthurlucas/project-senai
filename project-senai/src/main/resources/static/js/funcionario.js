/* ── VARIÁVEIS GLOBAIS ── */
let pedidos = [];
let statusFiltro = 'todos';
let estoqueEditandoId = null;

/* ── BUSCA DE PEDIDOS ── */
async function buscarPedidosDoServidor() {
    try {
        const res = await fetch('/api/funcionario/pedidos/listar');
        if (res.ok) {
            pedidos = await res.json();
            renderPedidos();
        }
    } catch (error) {
        console.error("Erro ao buscar pedidos:", error);
    }
}

function renderPedidos() {
    const buscaInput = document.getElementById('buscaPedido');
    const busca = buscaInput ? buscaInput.value.toLowerCase() : "";
    const lista = document.getElementById('listaPedidos');
    const vazio = document.getElementById('vazioPedidos');
    lista.innerHTML = '';

    const filtrados = pedidos.filter(p => {
        // Converte para String antes do toLowerCase para não quebrar com IDs numéricos
        const idStr = String(p.id || "").toLowerCase();
        const nomeUsuario = p.usuario?.nome ? p.usuario.nome.toLowerCase() : "cliente";

        const matchesStatus = (statusFiltro === 'todos' || p.status === statusFiltro);
        const matchesBusca = idStr.includes(busca) || nomeUsuario.includes(busca);

        return matchesStatus && matchesBusca;
    });

    // Atualiza contador de pendentes na aba
    const countEl = document.getElementById('countPedidos');
    if (countEl) {
        countEl.textContent = pedidos.filter(p => p.status === 'pendente').length;
    }

    vazio.style.display = filtrados.length === 0 ? 'block' : 'none';

    filtrados.forEach(p => {
        const card = document.createElement('div');
        const statusAtual = (p.status || 'pendente').toLowerCase();
        card.className = `pedido-card ${statusAtual}`;

        const statusLabel = { pendente: 'Pendente', pronto: 'Pronto', entregue: 'Entregue' }[statusAtual] || statusAtual;

        let botoes = '';
        if (statusAtual === 'pendente') {
            botoes = `<button class="btn-acao pronto" onclick="atualizarStatus(${p.id}, 'pronto')">Marcar como pronto</button>`;
        } else if (statusAtual === 'pronto') {
            botoes = `<button class="btn-acao entregar" onclick="atualizarStatus(${p.id}, 'entregue')">Confirmar entrega</button>`;
        } else {
            botoes = `<button class="btn-acao" disabled style="opacity:0.4">Entregue</button>`;
        }

        card.innerHTML = `
            <div class="pedido-head">
                <span class="pedido-num">#${p.id}</span>
                <span class="pedido-status status-${statusAtual}">${statusLabel}</span>
            </div>
            <div class="pedido-body">
                <div class="pedido-item"><strong>${p.usuario?.nome || 'Cliente'}</strong></div>
                <div class="pedido-item">
                    ${p.itens ? p.itens.map(i => `${i.produto?.nomeProduto || 'Produto'} x${i.quantidade}`).join('<br>') : 'Pedido sem itens'}
                </div>
            </div>
            <div class="pedido-footer">
                <span class="pedido-valor">R$ ${Number(p.valorTotal || 0).toFixed(2).replace('.',',')}</span>
            </div>
            <div class="pedido-acoes">${botoes}</div>`;
        lista.appendChild(card);
    });
}

/* ── STATUS ── */
async function atualizarStatus(id, novoStatus) {
    try {
        const res = await fetch(`/api/funcionario/pedidos/${id}/status`, {
            method: 'PATCH',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ status: novoStatus })
        });

        if (res.ok) {
            mostrarToast("Status atualizado!");
            await buscarPedidosDoServidor();
        }
    } catch (error) {
        mostrarToast("Erro ao conectar ao banco");
    }
}

/* ── ESTOQUE ── */
async function renderEstoque() {
    try {
        const res = await fetch('/api/funcionario/estoque');
        if (!res.ok) return;
        const estoqueData = await res.json();

        const tbody = document.getElementById('tabelaEstoque');
        const vazio = document.getElementById('vazioEstoque');
        tbody.innerHTML = '';

        vazio.style.display = estoqueData.length === 0 ? 'block' : 'none';

        estoqueData.forEach(e => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td><p class="estoque-nome">${e.nomeProduto || '-'}</p></td>
                <td><p class="estoque-desc">${e.descricaoProduto || '-'}</p></td>
                <td><span class="qtd-badge">${e.quantidade} un.</span></td>
                <td><button class="btn-editar" onclick="abrirModalEstoque(${e.idEstoque})">Editar</button></td>`;
            tbody.appendChild(tr);
        });
    } catch (e) { console.error("Erro no estoque:", e); }
}

/* ── AUXILIARES ── */
function filtrarStatus(status, el) {
    statusFiltro = status;
    document.querySelectorAll('.filtro-status').forEach(b => b.classList.remove('active'));
    el.classList.add('active');
    renderPedidos();
}

function mostrarTab(id, el) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.getElementById('tab-' + id).classList.add('active');
    el.classList.add('active');
}

function mostrarToast(msg) {
    const t = document.getElementById('toast');
    if(t) {
        t.textContent = msg;
        t.classList.add('show');
        setTimeout(() => t.classList.remove('show'), 3000);
    }
}

async function init() {
    await buscarPedidosDoServidor();
    await renderEstoque();
    setInterval(buscarPedidosDoServidor, 7000);
}

init();