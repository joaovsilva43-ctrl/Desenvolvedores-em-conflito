const tabela = document.querySelector('#tabela');
const contador = document.querySelector('#contador');
const feedback = document.querySelector('#feedback');
const botaoAtualizar = document.querySelector('#atualizar');

function escaparHTML(texto) {
  return String(texto).replaceAll('&', '&amp;').replaceAll('<', '&lt;')
    .replaceAll('>', '&gt;').replaceAll('"', '&quot;').replaceAll("'", '&#39;');
}

function classePrioridade(prioridade) {
  return prioridade === 'Alta' ? 'alta' : prioridade === 'Média' ? 'media' : 'baixa';
}

function renderizarChamados(chamados) {
  contador.textContent = `${chamados.length} chamado(s)`;
  if (!chamados.length) {
    tabela.innerHTML = '<tr><td colspan="4" class="vazio">Nenhum chamado encontrado.</td></tr>';
    return;
  }
  tabela.innerHTML = chamados.map(c => `
    <tr>
      <td><div class="id">#${c.id}</div><strong>${escaparHTML(c.titulo)}</strong></td>
      <td>${escaparHTML(c.setor)}</td>
      <td><span class="selo prioridade ${classePrioridade(c.prioridade)}">${escaparHTML(c.prioridade)}</span></td>
      <td><span class="selo estado">${escaparHTML(c.status)}</span></td>
    </tr>`).join('');
}

async function carregarChamados() {
  feedback.textContent = '';
  try {
    const status = document.querySelector('#filtro-status').value;
    const resposta = await fetch(`/api/chamados?status=${encodeURIComponent(status)}`);
    if (!resposta.ok) throw new Error(`HTTP ${resposta.status}`);
    const chamados = await resposta.json();
    renderizarChamados(chamados);
  } catch (erro) {
    feedback.textContent = `Não foi possível carregar os chamados: ${erro.message}`;
  }
}

botaoAtualizar.addEventListener('click', carregarChamados);
document.querySelector('#filtro-status').addEventListener('change', carregarChamados);
carregarChamados();
