<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- -->
<title>Universidade - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-3" /></div>
		<div class="col-sm-6" align="center">
			<form:form action="registrar" modelAttribute="universidade"
				method="POST" class="form-signin">
				<div class="form-group">
					<form:label for="sigla" path="id">Acrônimo:</form:label>
					<form:input type="text" class="form-control" id="sigla"
						placeholder="UFLA" name="sigla" path="sigla" required="true" />
				</div>
				<div class="form-group">
					<form:label for="nome" path="nome">Nome:</form:label>
					<form:input type="text" class="form-control" id="nome"
						placeholder="Universidade Federal de Lavras" name="nome"
						path="nome" required="true" />
				</div>
				<button type="submit" class="btn btn-primary dropdown-toggle">Salvar</button>
			</form:form>
		</div>
	</div>
</body>
</html>