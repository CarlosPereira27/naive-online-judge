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

<title>Configurações - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <div class="row">
    <div class="col-sm-3" /></div>
    <div class="col-sm-6" align="center">
      <div>
        <h2 style="text-align: center;">
          <b>Dados</b>
        </h2>
      </div>
      <form:form action="/user/configurations" modelAttribute="user" method="POST" class="form-signin">
        <div class="form-group">
          <form:label for="id" path="id">Identificação:</form:label>
          <form:input type="text" class="form-control" id="id" placeholder=""
            name="id" path="id" readonly="true" required="true" />
        </div>
        <div class="form-group">
          <form:label for="name" path="name">Nome:</form:label>
          <form:input type="text" class="form-control" id="name"
            placeholder="" name="name" path="name" required="true" />
        </div>
        <div class="form-group">
          <form:label for="email" path="email">Email:</form:label>
          <form:input type="email" class="form-control" id="email"
            placeholder="Email" name="email" path="email" readonly="true"
            required="true" />
        </div>
        <div class="form-group">
          <form:label for="passwordHash" path="passwordHash">Senha:</form:label>
          <form:input type="password" class="form-control" id="passwordHash"
            placeholder="passwordHash" name="passwordHash" path="passwordHash" required="true" />
        </div>
        <div class="form-group">
          <form:label for="gender" path="gender">Gênero:</form:label>
          <form:select path="gender" class="form-control">
            <form:option value="" label="-" />
            <form:options items="${genders}" itemLabel="name" />
          </form:select>
        </div>
        <div class="form-group">
          <form:label for="university" path="university.id">Universidade:</form:label>
          <form:select path="university.id" class="form-control">
            <form:option value="" label="-" />
            <c:forEach var="university" items="${universities}">
              <form:option value="${university.id}" label="${university.acronym} - ${university.name}"/>
            </c:forEach>
          </form:select>
        </div>
        <button type="button" class="btn btn-info"
          onclick="location.href='/university/register'">Cadastrar nova universidade</button>
        <div class="form-group">
          <form:label for="country" path="country">País:</form:label>
          <form:select path="country" class="form-control">
            <form:option value="" label="-" />
            <form:options items="${countries}" />
          </form:select>
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