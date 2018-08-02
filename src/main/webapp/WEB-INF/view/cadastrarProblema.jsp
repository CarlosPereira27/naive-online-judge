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
		<div class="col-sm-3" /></div>
		<div class="col-sm-6" align="center">
			<form:form action="cadastrar" modelAttribute="problema"
				enctype="multipart/form-data" method="POST" class="form-signin">

				<div class="form-group">
					<form:label for="titulo" path="titulo">Título:</form:label>
					<form:input type="text" class="form-control" id="titulo"
						placeholder="" name="titulo" path="titulo" required="false" />
				</div>

				<div class="form-group">
					<form:label for="tempoLimite" path="tempoLimite">Tempo limite (ms):</form:label>
					<form:input type="number" class="form-control" id="tempoLimite"
						placeholder="" name="tempoLimite" path="tempoLimite"
						required="false" />
				</div>
				<div class="form-group">
					<form:label for="descricao" path="descricao">Descrição:</form:label>
					<form:textarea rows="25" class="form-control" id="descricao"
						placeholder="" name="descricao" path="descricao" required="false" />
				</div>
				<div class="form-group">
					<form:label for="categoria" path="categoria">Categoria:</form:label>
					<form:select path="categoria" class="form-control">
						<form:options items="${categorias}" itemLabel="nome" />
					</form:select>
				</div>
				<div class="form-group">
					<form:label for="dificuldade" path="dificuldade">Dificuldade:</form:label>
					<form:input type="number" min="1" max="9" class="form-control"
						id="dificuldade" placeholder="" name="dificuldade"
						path="dificuldade" required="false" />
				</div>
				<div class="form-group">
					<form:label for="topicos" path="topicos">Tópicos:</form:label>
					<form:input type="text" class="form-control" id="topicos"
						placeholder="" name="topicos" path="topicos" />
				</div>
				<div class="form-group">
					<form:label for="contestOrigem" path="contestOrigem">Contest origem:</form:label>
					<form:input type="text" class="form-control" id="contestOrigem"
						placeholder="" name="contestOrigem" path="contestOrigem" />
				</div>
				<div class="form-group">
					<form:label for="linguagem" path="avaliacao.linguagemImplementacao">Linguagem de implementação:</form:label>
					<form:select path="avaliacao.linguagemImplementacao"
						class="form-control">
						<form:options items="${linguagens}" itemLabel="nome" />
					</form:select>
				</div>

				<div class="form-group">
					<label for="arqImpl">Arquivo implementação:</label> <input
						type="file" class="form-control" id="arqImpl" name="arqImpl" />
				</div>

				<div class="form-group">
					<label for="arqTestes">Arquivos de teste:</label> <input
						type="file" class="form-control" id="arqTestes" name="arqTestes"
						multiple />
				</div>

				<button type="submit" class="btn btn-primary dropdown-toggle">Salvar</button>
			</form:form>
		</div>
	</div>
</body>
</html>