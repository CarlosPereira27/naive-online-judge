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

<title>Problema - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <div class="row">
    <div class="col-sm-3" align="center"></div>
    <div class="col-sm-6" align="center">
      <div align="center">
        <i>${problem.id}</i>
      </div>
      <div>
        <h2 style="text-align: center;">
          <b>${problem.title}</b>
        </h2>
      </div>
      <div align="center">
        <b>Enviado por </b>${problem.author.name}</div>
      <div align="center" style="font-style: bold;">
        <b>Tempo limite:</b> ${problem.limitTime} ms
      </div>
      <div>
        <b>Categoria:</b> <a href="/problem/category/${problem.category.id}">${problem.category.name}</a>
      </div>
      <hr>
      <div>${problem.description}</div>
    </div>
  </div>
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-3" align="left">
      <i>${problem.originContest}</i>
    </div>
    <div class="col-sm-3" align="right">
      <a href="/submission/submit/${problem.id}" class="button btn btn-primary dropdown-toggle">Submeter solução</a>
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