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

<title>Problema - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<div class="row">
		<div class="col-sm-3" align="center"></div>
		<div class="col-sm-6" align="center">
			<div align="center">
				<i>${problema.id}</i>
			</div>
			<div>
				<h2 style="text-align: center;">
					<b>${problema.titulo}</b>
				</h2>
			</div>
			<div align="center">
				<b>Enviado por </b>${problema.autor.nome}</div>
			<div align="center" style="font-style: bold;">
				<b>Tempo limite:</b> ${problema.tempoLimite} ms
			</div>
			<div>
				<c:url var="categoriaLink" value="../problema/categoria">
					<c:param name="categoriaId" value="${problema.categoria.id}" />
				</c:url>
				<b>Categoria:</b> <a href="${categoriaLink}">${problema.categoria.nome}</a>
			</div>
			<hr>
			<div>${problema.descricao}</div>
		</div>
	</div>
	<c:url var="submissaoLink" value="../problema/submeter">
		<c:param name="problemaId">${problema.id}</c:param>
	</c:url>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-3" align="left">
			<i>${problema.contestOrigem}</i>
		</div>
		<div class="col-sm-3" align="right">
			<a href="${submissaoLink}"
				class="button btn btn-primary dropdown-toggle">Submeter solução</a>
		</div>
	</div>
</body>
</html>