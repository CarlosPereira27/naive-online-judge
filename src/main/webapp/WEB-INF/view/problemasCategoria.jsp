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

<title>Problemas - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-2"></div>
		<div class="col-sm-2" align="center">
			<h2 style="text-align: center;">
				<b>Top 20</b>
			</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th><b>Nome</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="usuario" items="${usuarios}">
						<c:url var="usuarioLink" value="../usuario/estatistica">
							<c:param name="usuarioId">${usuario.id}</c:param>
						</c:url>
						<tr>
							<td><a href="${usuarioLink}">${usuario.nome}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="col-sm-6" align="center">
			<h2 style="text-align: center;">
				<b>${categoria.nome}</b>
			</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th><b>#</b></th>
						<th><b>Nome</b></th>
						<th><b>Assunto</b></th>
						<th><b>Resolvido</b></th>
						<th><b>Dificuldade</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="problema" items="${problemas}">
						<c:url var="problemaLink" value="../problema/get">
							<c:param name="problemaId">${problema.id}</c:param>
						</c:url>
						<tr>
							<td><a href="${problemaLink}">${problema.id}</a></td>
							<td><a href="${problemaLink}">${problema.titulo}</a></td>
							<td>${problema.topicos}</td>
							<td>${problema.estatisticas.qtdResolucoes}</td>
							<td>${problema.dificuldade}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>