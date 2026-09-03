<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="./styles/styles.css">
    <title>Gestão de Materiais</title>
</head>
<body>
    <div id="navbar">
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <a class="navbar-brand" href="disciplina">Gestão de Materiais</a>
        </nav>
    </div>
    <%
        model.Disciplina disciplina = (model.Disciplina) request.getAttribute("disciplina");
        java.util.List<model.Semestre> semestres =
            (java.util.List<model.Semestre>) request.getAttribute("semestres");
        String etapaAtual = (String) request.getAttribute("etapaAtual");
        if (etapaAtual == null) etapaAtual = "I";
    %>
    <div id="disciplinas">
        <div class="card-header-custom">
            <h5><%= disciplina == null ? "Nova Disciplina" : "Editar Disciplina" %></h5>
        </div>
        <form method="post" action="disciplina">
            <% if (disciplina != null) { %>
                <input type="hidden" name="acao" value="atualizar">
                <input type="hidden" name="idDisc" value="<%= disciplina.getIdDisc() %>">
            <% } %>
            <input type="hidden" name="etapa" value="<%= etapaAtual %>">
            <div class="mb-3">
                <label class="form-label">Nome da disciplina</label>
                <input type="text" class="form-control" name="nome"
                       value="<%= disciplina != null ? disciplina.getNome() : "" %>"
                       required maxlength="50">
            </div>
            <div class="mb-3">
                <label class="form-label">Semestre</label>
                <select class="form-select" name="idSem" required>
                    <option value="">Selecione...</option>
                    <% if (semestres != null) {
                        for (model.Semestre s : semestres) {
                            boolean selecionado = disciplina != null && disciplina.getIdSem() == s.getIdSem();
                    %>
                        <option value="<%= s.getIdSem() %>" <%= selecionado ? "selected" : "" %>>
                            Etapa <%= s.getEtapa() %> (<%= s.getDataInicio() %> a <%= s.getDataFim() %>)
                        </option>
                    <%  }
                    } %>
                </select>
                <a href="semestre?acao=novo" class="small">+ Cadastrar novo semestre</a>
            </div>
            <button type="submit" class="btn btn-primary">Salvar</button>
            <a href="disciplina?etapa=<%= etapaAtual %>" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>
</body>
</html>