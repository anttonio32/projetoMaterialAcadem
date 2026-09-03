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
            <a class="navbar-brand" href="disciplina">Gestão de Materiais</a>
        </nav>
    </div>

    <%
        String etapaAtual = (String) request.getAttribute("etapaAtual");
        java.util.List<String> etapasDisponiveis =
            (java.util.List<String>) request.getAttribute("etapasDisponiveis");
        java.util.List<model.Disciplina> disciplinas =
            (java.util.List<model.Disciplina>) request.getAttribute("disciplinas");
    %>
    <div id="semestres">
        <div class="card-header-custom">
            <h5>Etapas (Semestres)</h5>
            <a href="semestre?acao=novo" class="btn btn-primary btn-sm">
                <i class="bi bi-plus-lg"></i> Novo Semestre
            </a>
        </div>
        <% if (etapasDisponiveis == null || etapasDisponiveis.isEmpty()) { %>
            <p class="text-muted py-2">Nenhum semestre cadastrado ainda. Clique em "Novo Semestre" para começar.</p>
        <% } else { %>
            <section id="etapas">
                <% for (String e : etapasDisponiveis) { %>
                    <a href="disciplina?etapa=<%= e %>"
                       class="tab-semestre <%= e.equals(etapaAtual) ? "active" : "" %>">
                        <%= e %>
                    </a>
                <% } %>
            </section>
        <% } %>
    </div>
    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>Disciplinas da Etapa <%= etapaAtual != null ? etapaAtual : "-" %></h5>
            <% if (etapaAtual != null) { %>
                <a href="disciplina?acao=novo&etapa=<%= etapaAtual %>" class="btn btn-primary btn-sm">
                    <i class="bi bi-plus-lg"></i> Nova Disciplina
                </a>
            <% } %>
        </div>
        <table class="table">
            <thead>
                <tr>
                    <th>Disciplina</th>
                    <th class="text-center">Ações</th>
                </tr>
            </thead>
            <tbody>
                <% if (disciplinas == null || disciplinas.isEmpty()) { %>
                    <tr><td colspan="2" class="text-center text-muted py-3">Nenhuma disciplina cadastrada</td></tr>
                <% } else {
                    for (model.Disciplina d : disciplinas) { %>
                        <tr>
                            <td>
                                <a href="material?idDisc=<%= d.getIdDisc() %>"><%= d.getNome() %></a>
                            </td>
                            <td class="text-center">
                                <a href="disciplina?acao=editar&id=<%= d.getIdDisc() %>" class="acao-btn editar"><i class="bi bi-pencil"></i></a>
                                <a href="disciplina?acao=excluir&id=<%= d.getIdDisc() %>&etapa=<%= etapaAtual %>" class="acao-btn excluir"><i class="bi bi-trash"></i></a>
                            </td>
                        </tr>
                <%  }
                } %>
            </tbody>
        </table>
        <p class="total-disciplinas">Total de disciplinas: <%= disciplinas != null ? disciplinas.size() : 0 %></p>
    </div>
</body>
</html>