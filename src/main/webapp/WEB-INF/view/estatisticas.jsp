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

<title>Estatísticas - Naive Online Judge</title>
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
			<c:choose>
				<c:when test="${usuarioEst == null || usuarioEst.id != usuario.id}">
					<h2 style="text-align: center;">
						<b>Estatísticas - ${usuarioEst.nome}</b>
					</h2>
				</c:when>
				<c:when test="${usuarioEst.id == usuario.id}">
					<h2 style="text-align: center;">
						<b>Minhas estatísticas</b>
					</h2>
				</c:when>
			</c:choose>
			<h4 style="text-align: center;">
				<b>Progresso geral</b>
			</h4>
			<div class="progress">
				<div class="progress-bar" style="width:${porcentagemGeral}%">
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
					<c:forEach var="progressoCat" items="${progressoCats}">
						<tr>
							<c:url var="categoriaLink" value="../problema/categoria">
								<c:param name="categoriaId" value="${progressoCat.categoria.id}" />
							</c:url>
							<td><a href="${categoriaLink}">${progressoCat.categoria.nome}</a></td>

							<td><div class="progress">
									<div class="progress-bar" style="width:${progressoCat.progresso}%">
										<span class="sr-only"></span>
									</div>
								</div></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>