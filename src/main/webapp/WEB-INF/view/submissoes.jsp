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

<title>Submissões - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6" align="center">
			<h2 style="text-align: center;">
				<b>Submissões</b>
			</h2>
			<table class="table table-hover">
				<thead>
					<tr>
						<th><b>#</b></th>
						<th><b>Problema</b></th>
						<th><b>Resposta</b></th>
						<th><b>Linguagem</b></th>
						<th><b>Tempo</b></th>
						<th><b>Data</b></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="submissao" items="${submissoes}">
						<c:url var="problemaLink" value="../problema/get">
							<c:param name="problemaId">${submissao.problema.id}</c:param>
						</c:url>
						<c:url var="submissaoLink" value="../submissao/get">
							<c:param name="submissaoId">${submissao.id}</c:param>
						</c:url>
						<tr>
							<td><a href="${submissaoLink}">${submissao.id}</a></td>
							<td><a href="${problemaLink}">${submissao.problema.titulo}</a></td>
							<td><a href="${submissaoLink}">${submissao.estado.nome}</a></td>
							<td>${submissao.linguagem.nome}</td>
							<td>${submissao.tempo}</td>
							<td>${submissao.dataEnvio}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>