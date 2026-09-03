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
            <a class="navbar-brand" href="<c:url value='/index.jsp'/>">Gestão de Materiais</a>
        </nav>
    </div>

    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>Materiais de ${disciplina.nome}</h5>
        </div>

        <table class="table">
            <thead>
                <tr>
                    <th>Nome</th>
                    <th>Tipo</th>
                    <th class="text-center">Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty materiais}">
                        <tr><td colspan="3" class="text-center text-muted py-3">Nenhum material cadastrado</td></tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="m" items="${materiais}">
                            <tr>
                                <td>${m.nome}</td>
                                <td>${m.tipo}</td>
                                <td class="text-center">
                                    <a href="<c:url value='/material'><c:param name='acao' value='excluir'/><c:param name='id' value='${m.idMat}'/><c:param name='idDisc' value='${disciplina.idDisc}'/></c:url>" class="acao-btn excluir"><i class="bi bi-trash"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        <p class="total-disciplinas">Total de materiais: ${materiais.size()}</p>
    </div>

    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>Novo Material</h5>
        </div>

        <form method="post" action="<c:url value='/material'/>" enctype="multipart/form-data">
            <input type="hidden" name="idDisc" value="${disciplina.idDisc}">

            <div class="mb-3">
                <label class="form-label">Nome do material</label>
                <input type="text" class="form-control" name="nome" required maxlength="100">
            </div>

            <div class="mb-3">
                <label class="form-label">Tipo</label>
                <select class="form-select" name="tipo" required>
                    <option value="PDF">PDF</option>
                    <option value="DOCX">DOCX</option>
                    <option value="TXT">TXT</option>
                    <option value="IMAGEM">Imagem</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Arquivo</label>
                <input type="file" class="form-control" name="arquivo" required>
            </div>

            <button type="submit" class="btn btn-primary"><i class="bi bi-plus-lg"></i> Adicionar</button>
        </form>
    </div>
</body>
</html>