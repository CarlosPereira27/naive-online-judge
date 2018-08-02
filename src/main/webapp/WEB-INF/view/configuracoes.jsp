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

<title>Configura��es - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-3" /></div>
		<div class="col-sm-6" align="center">
			<div>
				<h2 style="text-align: center;">
					<b>Dados</b>
				</h2>
			</div>
			<form:form action="configuracoes" modelAttribute="usuario"
				method="POST" class="form-signin">
				<div class="form-group">
					<form:label for="id" path="id">Identifica��o:</form:label>
					<form:input type="text" class="form-control" id="id" placeholder=""
						name="id" path="id" readonly="true" required="true" />
				</div>
				<div class="form-group">
					<form:label for="nome" path="nome">Nome:</form:label>
					<form:input type="text" class="form-control" id="nome"
						placeholder="" name="nome" path="nome" required="true" />
				</div>
				<div class="form-group">
					<form:label for="email" path="email">Email:</form:label>
					<form:input type="email" class="form-control" id="email"
						placeholder="Email" name="email" path="email" readonly="true"
						required="true" />
				</div>
				<div class="form-group">
					<form:label for="senha" path="senha">Senha:</form:label>
					<form:input type="password" class="form-control" id="senha"
						placeholder="Senha" name="senha" path="senha" required="true" />
				</div>
				<div class="form-group">
					<form:label for="genero" path="genero">G�nero:</form:label>
					<form:select path="genero" class="form-control">
						<form:option value="" label="-" />
						<form:options items="${generos}" itemLabel="nome" />
					</form:select>
				</div>
				<div class="form-group">
					<form:label for="universidade" path="universidade.id">Universidade:</form:label>
					<form:select path="universidade.id" class="form-control">
						<form:option value="" label="-" />
						<c:forEach var="univers" items="${universidades}">
							<form:option value="${univers.id}"
								label="${univers.sigla} - ${univers.nome}" />
						</c:forEach>
					</form:select>
				</div>
				<c:url var="registrarUniversidadeLink"
					value="/universidade/registrar"></c:url>
				<button type="button" class="btn btn-info"
					onclick="location.href='${registrarUniversidadeLink}'">Cadastrar
					nova universidade</button>
				<div class="form-group">
					<form:label for="pais" path="pais">Pa�s:</form:label>
					<form:select path="pais" class="form-control">
						<form:option value="" label="-" />
						<form:options items="${paises}" />
					</form:select>
				</div>
				<button type="submit" class="btn btn-primary dropdown-toggle">Salvar</button>
			</form:form>
		</div>
	</div>
</body>
</html>