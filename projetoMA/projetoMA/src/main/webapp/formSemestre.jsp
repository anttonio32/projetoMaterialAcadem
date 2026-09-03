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
            <a class="navbar-brand" href="index.jsp">Gestão de Materiais</a>
        </nav>
    </div>

    <div id="disciplinas">
        <div class="card-header-custom">
            <h5>Novo Semestre</h5>
        </div>

        <form method="post" action="semestre">
            <div class="mb-3">
                <label class="form-label">Etapa</label>
                <select class="form-select" name="etapa" required>
                    <option value="I">I</option>
                    <option value="II">II</option>
                    <option value="III">III</option>
                    <option value="IV">IV</option>
                    <option value="V">V</option>
                    <option value="VI">VI</option>
                    <option value="VII">VII</option>
                    <option value="VIII">VIII</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Data de início</label>
                <input type="date" class="form-control" name="dataInicio" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Data de fim</label>
                <input type="date" class="form-control" name="dataFim" required>
            </div>

            <button type="submit" class="btn btn-primary">Salvar</button>
            <a href="disciplina?acao=novo" class="btn btn-secondary">Cancelar</a>
        </form>
    </div>

    <% if (request.getAttribute("erro") != null) { %>
        <script>
            alert("<%= request.getAttribute("erro") %>");
        </script>
    <% } %>
</body>
</html>