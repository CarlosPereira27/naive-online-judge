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

<%-- Codemirror css --%>
<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/codemirror.min.css" />

<title>Submissão - Naive Online Judge</title>
</head>
<body>
  <%@ include file="../header.jsp"%>
  <form:form action="/problem/submit/${problem.id}" modelAttribute="submission" method="POST">
    <div class="row">
      <div class="col-sm-3"></div>
      <div class="col-sm-6" align="center">
        <div class="form-group">
          <form:label for="problem.id" path="problem.id">Problema:</form:label>
          <form:input type="text" class="form-control" id="problem"
            placeholder="" name="id" path="problem.id" required="true" />
        </div>
        <div class="form-group">
          <form:label for="language" path="language">Linguagem:</form:label>
          <form:select path="language" class="form-control" required="true">
            <form:options items="${languages}" itemLabel="name" />
          </form:select>
        </div>
        <div class="form-group">
          <form:label for="implementation" path="implementation">Código fonte:</form:label>
          <div align="left">
            <form:textarea  class="codemirror-textarea"
              id="implementation" name="implementation" path="implementation" />
          </div>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3" /></div>
      <div class="col-sm-6" align="right">
        <button type="submit" class="btn btn-primary dropdown-toggle">Submeter solução</button>
      </div>
    </div>
  </form:form>

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

  <!-- add basic CodeMirror functionality -->
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/codemirror.min.js"></script>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/addon/selection/active-line.min.js"></script>

  <!-- add Javascript-mode dependencies -->
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/javascript/javascript.min.js"></script>

  <!-- add PHP-mode dependencies (replace dependency loading by require.js!) -->
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/xml/xml.min.js"></script>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/htmlmixed/htmlmixed.min.js"></script>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/clike/clike.min.js"></script>
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/php/php.min.js"></script>

  <!-- add SPARQL-mode dependencies -->
  <script
    src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.39.2/mode/sparql/sparql.min.js"></script>

  <script>
    CodeMirror.fromTextArea(document.getElementById("implementation"), {
      lineNumbers : true,
      mode : "text/x-java",
      matchBrackets : true,
    });
  </script>
  
</body>
</html>