<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link rel="stylesheet" href="./styles/styles.css">
    <title>Gestão de Materiais</title>
</head>
<body>
    <div id="navbar">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <a class="navbar-brand" href="<c:url value='/index.html'/>">Gestão de Materiais</a>
        </nav>
    </div>

    <div id="semestres">
        <div class="card-header-custom">
            <h5>Etapas (Semestres)</h5>
        </div>
        <section id="etapas">
            <c:forEach var="e" items="${['I','II','III','IV','V','VI','VII','VIII']}">
                <a href="<c:url value='/disciplina'><c:param name='etapa' value='${e}'/></c:url>"
                   class="tab-semestre ${etapaAtual == e ? 'active' : ''}">
                    ${e}
                </a>
            </c:forEach>
        </section>
    </div>

    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>Disciplinas da Etapa ${etapaAtual}</h5>
            <a href="<c:url value='/disciplina'><c:param name='acao' value='novo'/><c:param name='etapa' value='${etapaAtual}'/></c:url>"
               class="btn btn-primary btn-sm">
                <i class="bi bi-plus-lg"></i> Nova Disciplina
            </a>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Disciplina</th>
                    <th class="text-center">Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty disciplinas}">
                        <tr><td colspan="2" class="text-center text-muted py-3">Nenhuma disciplina cadastrada</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="d" items="${disciplinas}">
                            <tr>
                                <td>
                                    <a href="<c:url value='/material'><c:param name='idDisc' value='${d.idDisc}'/></c:url>">
                                        ${d.nome}
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a href="<c:url value='/disciplina'><c:param name='acao' value='editar'/><c:param name='id' value='${d.idDisc}'/></c:url>" class="acao-btn editar"><i class="bi bi-pencil"></i></a>
                                    <a href="<c:url value='/disciplina'><c:param name='acao' value='excluir'/><c:param name='id' value='${d.idDisc}'/><c:param name='etapa' value='${etapaAtual}'/></c:url>" class="acao-btn excluir"><i class="bi bi-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <p class="total-disciplinas">Total de disciplinas: ${disciplinas.size()}</p>
    </div>
</body>
</html>