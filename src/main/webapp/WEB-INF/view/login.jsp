<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<!-- saved from url=(0050)https://getbootstrap.com/docs/3.3/examples/signin/ -->
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="https://getbootstrap.com/docs/3.3/favicon.ico">

<title>Naive Online Judge</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link
	href="${pageContext.request.contextPath}/resources/css/ie10-viewport-bug-workaround.css"
	rel="stylesheet">

<!-- Custom styles for this template -->
<link href="${pageContext.request.contextPath}/resources/css/signin.css"
	rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script
	src="${pageContext.request.contextPath}/resources/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<div class="container">

		<h1 style="text-align: center;">
			<b>Naive Online Judge</b>
		</h1>
		<br> <br>

		<c:if test="${mensagem != null}">
			<div class="alert ${mensagem.tipoDeAlerta.nome} alert-dismissable">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
				${mensagem.conteudo}
			</div>
		</c:if>

		<form:form action="login" modelAttribute="login" method="POST"
			class="form-signin">
			<h2 class="form-signin-heading">Login</h2>
			<form:label for="inputEmail" class="sr-only" path="email">Email</form:label>
			<form:input type="email" id="inputEmail" class="form-control"
				placeholder="Email address" required="true" autofocus=""
				path="email" />
			<form:label for="inputPassword" class="sr-only" path="email">Password</form:label>
			<form:input type="password" id="inputPassword" class="form-control"
				placeholder="Senha" required="true" path="senha" />
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					Lembrar-me
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Logar</button>
		</form:form>
		<br>
		<c:url var="registrarLink" value="registrar" />
		<div align="center">
			<a href="${registrarLink}">Criar nova conta</a>
		</div>

	</div>
	<!-- /container -->



	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script
		src="${pageContext.request.contextPath}}/resources/js/ie10-viewport-bug-workaround.js"></script>


</body>
</html>