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
    <div class="col-sm-3"></div>
    
    <div class="col-sm-6" align="center">
      <form:form action="/problem/register" modelAttribute="problem" enctype="multipart/form-data" method="POST" class="form-signin">

        <div class="form-group">
          <form:label for="title" path="title">Título:</form:label>
          <form:input type="text" class="form-control" id="title"
            placeholder="" name="title" path="title" required="false" />
        </div>

        <div class="form-group">
          <form:label for="limitTime" path="limitTime">Tempo limite (ms):</form:label>
          <form:input type="number" class="form-control" id="limitTime"
            placeholder="" name="limitTime" path="limitTime"
            required="false" />
        </div>
        <div class="form-group">
          <form:label for="description" path="description">Descrição:</form:label>
          <form:textarea rows="25" class="form-control" id="description"
            placeholder="" name="description" path="description" required="false" />
        </div>
        <div class="form-group">
          <form:label for="category" path="category">Categoria:</form:label>
          <form:select path="category" class="form-control">
            <form:options items="${categories}" itemLabel="name" />
          </form:select>
        </div>
        <div class="form-group">
          <form:label for="difficulty" path="difficulty">Dificuldade:</form:label>
          <form:input type="number" min="1" max="9" class="form-control"
            id="difficulty" placeholder="" name="difficulty"
            path="difficulty" required="false" />
        </div>
        <div class="form-group">
          <form:label for="subjects" path="subjects">Tópicos:</form:label>
          <form:input type="text" class="form-control" id="subjects"
            placeholder="" name="subjects" path="subjects" />
        </div>
        <div class="form-group">
          <form:label for="originContest" path="originContest">Contest origem:</form:label>
          <form:input type="text" class="form-control" id="originContest"
            placeholder="" name="originContest" path="originContest" />
        </div>
        <div class="form-group">
          <form:label for="implementationLanguage" path="judge.implementationLanguage">Linguagem de implementação:</form:label>
          <form:select path="judge.implementationLanguage"
            class="form-control">
            <form:options items="${languages}" itemLabel="name" />
          </form:select>
        </div>

        <div class="form-group">
          <label for="implementationFile">Arquivo implementação:</label> <input
            type="file" class="form-control" id="implementationFile" name="implementationFile" />
        </div>

        <div class="form-group">
          <label for="testFiles">Arquivos de teste:</label> <input
            type="file" class="form-control" id="testFiles" name="testFiles"
            multiple />
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