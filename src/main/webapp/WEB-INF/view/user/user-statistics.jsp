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

<title>Estatísticas - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <div class="row">
    <div class="col-sm-2"></div>

    <%-- Top 20 Users --%>
    <div class="col-sm-2" align="center">
      <%@ include file="top-users.jsp"%>
    </div>
    
    <div class="col-sm-6" align="center">
      <c:choose>
        <c:when test="${userStatistics == null || userStatistics.id != user.id}">
          <h2 style="text-align: center;">
            <b>Estatísticas - ${userStatistics.nome}</b>
          </h2>
        </c:when>
        <c:when test="${userStatistics.id == user.id}">
          <h2 style="text-align: center;">
            <b>Minhas estatísticas</b>
          </h2>
        </c:when>
      </c:choose>
      <h4 style="text-align: center;">
        <b>Progresso geral</b>
      </h4>
      <div class="progress">
        <div class="progress-bar" style="width:${generalProgress}%">
          <span class="sr-only"></span>
        </div>
      </div>
      <table class="table table-hover">
        <thead>
          <tr>
            <th><b>Categoria</b></th>
            <th><b>Progresso</b></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="categoryProgress" items="${categoriesProgress}">
            <tr>
              <td><a href="/problem/category/${categoryProgress.category.id}">${categoryProgress.category.name}</a></td>
              <td><div class="progress">
                  <div class="progress-bar" style="width:${categoryProgress.progress}%">
                    <span class="sr-only"></span>
                  </div>
                </div></td>
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