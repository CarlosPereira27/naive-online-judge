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

<title>Rank - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>}
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6" align="center">
      <h2 style="text-align: center;"><b>Rank</b></h2>
      <table class="table table-hover">
        <thead>
          <tr>
            <th><b>Posição</b></th>
            <th><b>Nome</b></th>
            <th><b>Universidade</b></th>
            <th><b>Resolvido</b></th>
            <th><b>Tentado</b></th>
            <th><b>Submissões</b></th>
          </tr>
        </thead>
        <tbody>
          <c:set var="count" value="1" scope="page" />
          <c:forEach var="user" items="${users}">
            <tr>
              <td><c:out value="${count}" /></td>
              <td><a href="/user/statistics/${user.id}">${user.name}</a></td>
              <td>${user.university.acronym}</td>
              <td>${user.statistics.qtyAcceptedProblems}</td>
              <td>${user.statistics.qtyTryProblems}</td>
              <td>${user.statistics.qtySubmissions}</td>
            </tr>
            <c:set var="count" value="${count + 1}" scope="page" />
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