# Registro de integração

## Integrantes
João Victor, Leanderson, Danilo, Ana Beatriz e Fernando.

## Quem entregou o quê (Desafio 1)
| Branch | O que entregou | Arquivos alterados | Responsável |
|---|---|---|---|
| feature/filtros | Filtro de chamados por status | ChamadoService.java, web/app.js, web/index.html | Danilo |
| feature/ordenacao | Ordenação por prioridade (Alta, Média, Baixa) | ChamadoService.java, web/app.js, web/index.html | Fernando e Ana Beatriz |
| feature/resumo | Indicadores de total, abertos e concluídos | Servidor.java (rota /api/resumo), web/app.js, web/index.html | Leanderson |
| feature/setor | Filtro por setor (criada na versão integrada) | ChamadoService.java, web/app.js, web/index.html | João Victor |

As branches filtros e ordenacao alteraram os mesmos três arquivos, o que explica os conflitos. A resumo foi a única a alterar o Servidor.java.

## Conflitos e resoluções (Desafio 2)
Todos os merges usaram `--no-ff`, para preservar o histórico das branches.

| Merge | Conflito | Como foi resolvido |
|---|---|---|
| feature/filtros | Nenhum | Merge automático (só o README mesclou sozinho) |
| feature/ordenacao | ChamadoService.java, web/app.js e web/index.html | Mantivemos o filtro por status e a ordenação, juntos e sem duplicar linhas |
| feature/resumo | web/app.js | Mantivemos a função carregarResumo() e os listeners dos selects, cada um uma vez só |
| feature/setor | Nenhum | A branch partiu da main já integrada |

Nenhuma implementação foi apagada para resolver conflito.

## Revisão (quem ficou responsável por cada parte)
Cada integrante revisou a parte que desenvolveu.

| Parte | Revisor |
|---|---|
| Filtro por status (feature/filtros) | Danilo |
| Ordenação por prioridade (feature/ordenacao) | Fernando e Ana Beatriz |
| Indicadores (feature/resumo) | Leanderson |
| Filtro por setor (feature/setor) | João Victor |

## Testes realizados (Desafio 4)
Testamos no painel (http://localhost:8766): sem filtros (8 chamados), status Aberto, status Em andamento, ordenação por prioridade, status + ordenação, setor Sistemas, setor + ordenação e os indicadores (8, 4 e 2, sem mudar ao filtrar). Todos passaram.