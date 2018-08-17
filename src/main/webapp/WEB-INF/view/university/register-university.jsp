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

<title>Universidade - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <div class="row">
    <div class="col-sm-3"></div>
    <div class="col-sm-6" align="center">
      <form:form action="register" modelAttribute="university" method="POST" class="form-signin">
        <div class="form-group">
          <form:label for="acronym" path="acronym">Acrônimo:</form:label>
          <form:input type="text" class="form-control" id="acronym"
            placeholder="UFLA" name="acronym" path="acronym" required="true" />
        </div>

        <div class="form-group">
          <form:label for="name" path="name">Nome:</form:label>
          <form:input type="text" class="form-control" id="name"
            placeholder="Universidade Federal de Lavras" name="name" path="name" required="true" />
        </div>
        <button type="submit" class="btn btn-primary dropdown-toggle">Salvar</button>
      </form:form>
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