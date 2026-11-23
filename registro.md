 # Registro de integração

## Quem entregou o quê
| Branch | Funcionalidade | Responsável |
|---|---|---|
| feature/filtros | Filtro de chamados por status | Maria |
| feature/ordenacao | Ordenação por prioridade | Pedro |
| feature/resumo | Indicadores de atendimento | Ana |
| feature/setor | Filtro por setor | Joao Victor |

## Conflitos e resoluções
**Merge da feature/filtros:** sem conflito (só o README mesclou sozinho).

**Merge da feature/ordenacao:** conflito em `ChamadoService.java`, `web/app.js` e `web/index.html`. Mantive o filtro por status e a ordenação juntos, sem duplicar linhas.

**Merge da feature/resumo:** conflito em `web/app.js`. Mantive a função `carregarResumo()` e os listeners dos selects, cada um uma vez só.

**Merge da feature/setor:** sem conflito, pois a branch partiu da `main` já unificada.

## Testes realizados
Testei 7 cenários no painel (filtros isolados, combinados e ordenação). Os cards de resumo continuaram em 8, 4 e 2 em todos.