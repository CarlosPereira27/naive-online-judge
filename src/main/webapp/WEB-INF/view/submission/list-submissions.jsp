<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<!-- Meta tags -->
<meta charset="utf-8">
<meta name="viewport"
  content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap css -->
<link rel="stylesheet"
  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

<!-- Toastr css -->
<link
  href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css"
  rel="stylesheet" />

<title>Submissões - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6" align="center">
      <h2 style="text-align: center;">
        <b>Submissões</b>
      </h2>
      <table class="table table-hover">
        <thead>
          <tr>
            <th><b>#</b></th>
            <th><b>Problema</b></th>
            <th><b>Resposta</b></th>
            <th><b>Linguagem</b></th>
            <th><b>Tempo</b></th>
            <th><b>Data</b></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="submission" items="${submissions}">
            <tr>
              <td><a href="/submission/${submission.id}">${submission.id}</a></td>
              <td><a href="/problem/${submission.problem.id}">${submission.problem.title}</a></td>
              <td><a href="/submission/${submission.id}">${submission.state.name}</a></td>
              <td>${submission.language.name}</td>
              <td>${submission.time}</td>
              <td>${submission.submissionDate}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>

  <!-- Javascript -->
  
  <!-- JQuery js -->
  <script
    src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  
  <!-- Bootstrap js -->
  <script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  
  <!-- Toastr js -->
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

</body>
</html>