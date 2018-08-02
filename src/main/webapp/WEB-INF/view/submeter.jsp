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

<!-- add basic CodeMirror functionality -->
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/lib/codemirror.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/addon/selection/active-line.js"
	type="text/javascript" charset="utf-8"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/codemirror-5.30.0/lib/codemirror.css" />

<!-- add Javascript-mode dependencies -->
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/javascript/javascript.js"
	type="text/javascript" charset="utf-8"></script>

<!-- add PHP-mode dependencies (replace dependency loading by require.js!) -->
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/xml/xml.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/htmlmixed/htmlmixed.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/clike/clike.js"
	type="text/javascript" charset="utf-8"></script>
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/php/php.js"
	type="text/javascript" charset="utf-8"></script>

<!-- add SPARQL-mode dependencies -->
<script
	src="${pageContext.request.contextPath}/resources/codemirror-5.30.0/mode/sparql/sparql.js"
	type="text/javascript" charset="utf-8"></script>

<title>Submissão - Naive Online Judge</title>
</head>
<body>
	<%@ include file="header.jsp"%>
	<form:form action="../problema/submeter" modelAttribute="submissao"
		method="POST">
		<div class="row">
			<div class="col-sm-3"></div>
			<div class="col-sm-6" align="center">
				<div class="form-group">
					<form:label for="problema.id" path="problema.id">Problema:</form:label>
					<form:input type="text" class="form-control" id="problema"
						placeholder="" name="titulo" path="problema.id" required="true" />
				</div>
				<div class="form-group">
					<form:label for="linguagem" path="linguagem">Linguagem:</form:label>
					<form:select path="linguagem" class="form-control" required="true">
						<form:options items="${linguagens}" itemLabel="nome" />
					</form:select>
				</div>
				<div class="form-group">
					<form:label for="implementacao" path="implementacao">Código fonte:</form:label>
					<div align="left">
						<form:textarea  class="codemirror-textarea"
							id="implementacao" name="implementacao" path="implementacao" />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3" /></div>
			<div class="col-sm-6" align="right">
				<button type="submit" class="btn btn-primary dropdown-toggle">Submeter
					solução</button>
			</div>
		</div>
	</form:form>
</body>
<script>
	CodeMirror.fromTextArea(document.getElementById("implementacao"), {
		lineNumbers : true,
		mode : "text/x-java",
		matchBrackets : true,
	});
</script>
</html>