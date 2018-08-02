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

<title>Rank - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>}
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
					<c:forEach var="usuario" items="${usuarios}">
						<c:url var="usuarioLink" value="../usuario/estatistica">
							<c:param name="usuarioId">${usuario.id}</c:param>
						</c:url>
						<tr>
							<td><c:out value="${count}" /></td>
							<td><a href="${usuarioLink}">${usuario.nome}</a></td>
							<td>${usuario.universidade.sigla}</td>
							<td>${usuario.estatistica.qtdProblemasResolvidos}</td>
							<td>${usuario.estatistica.qtdProblemasTentados}</td>
							<td>${usuario.estatistica.qtdSubmissoes}</td>
						</tr>
						<c:set var="count" value="${count + 1}" scope="page" />
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>