
<div class="row">
  <table class="table table-hover">
    <thead>
      <tr>
        <th><b>#</b></th>
        <th><b>Nome</b></th>
        <th><b>Assunto</b></th>
        <th><b>Resolvido</b></th>
        <th><b>Dificuldade</b></th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="problem" items="${problems}">
        <tr>
          <td><a href="/problem/${problem.id}">${problem.id}</a></td>
          <td><a href="/problem/${problem.id}">${problem.title}</a></td>
          <td>${problem.subjects}</td>
          <td>${problem.statistics.qtyAccepted}</td>
          <td>${problem.difficulty}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>