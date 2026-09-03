<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="styles/styles.css">
    <title>Gestão de Materiais</title>
</head>
<body>
    <div id="navbar">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <a class="navbar-brand" href="<c:url value='/index.jsp'/>">Gestão de Materiais</a>
        </nav>
    </div>

    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>${empty disciplina ? 'Nova Disciplina' : 'Editar Disciplina'}</h5>
        </div>

        <form method="post" action="<c:url value='/disciplina'/>">

            <c:if test="${not empty disciplina}">
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="idDisc" value="${disciplina.idDisc}">
            </c:if>

            <input type="hidden" name="etapa" value="${etapaAtual}">

            <div class="mb-3">
                <label class="form-label">Nome da disciplina</label>
                <input type="text" class="form-control" name="nome"
                       value="${disciplina.nome}" required maxlength="50">
            </div>

            <div class="mb-3">
                <label class="form-label">Semestre</label>
                <select class="form-select" name="idSem" required>
                    <option value="">Selecione...</option>
                    <c:forEach var="s" items="${semestres}">
                        <option value="${s.idSem}" ${disciplina.idSem == s.idSem ? 'selected' : ''}>
                            Etapa ${s.etapa} (${s.dataInicio} a ${s.dataFim})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Salvar</button>
            <a href="<c:url value='/disciplina'><c:param name='etapa' value='${etapaAtual}'/></c:url>" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>