<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
	<div class="col-sm-2" /></div>
	<div class="col-sm-8" align="center">
		<nav class="navbar navbar-inverse">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="../usuario/home">Naive Online
						Judge</a>
				</div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="../usuario/home">Home</a></li>
					<c:url var="perfilConfLink" value="../usuario/configuracoes" />
					<c:url var="perfilEstLink" value="../usuario/estatistica">
						<c:param name="usuarioId">${usuario.id}</c:param>
					</c:url>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Perfil<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${perfilConfLink}">Configurações</a></li>
							<li><a href="${perfilEstLink}">Estatísticas</a></li>
						</ul></li>
					<c:url var="problemasBuscaLink" value="../problema/listar" />
					<c:url var="meusProblemasLink" value="../problemas/usuario" />
					<c:url var="cadastrarProblemaLink" value="../problema/cadastrar" />
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Problemas<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${problemasBuscaLink}">Listar</a></li>
							<c:forEach var="categoria" items="${categoriasProblemas}">
								<c:url var="categoriaLink" value="../problema/categoria">
									<c:param name="categoriaId" value="${categoria.id}" />
								</c:url>
								<li><a href="${categoriaLink}">${categoria.nome}</a></li>
							</c:forEach>
							<li><a href="${meusproblemasLink}">Meus problemas</a></li>
							<li><a href="${cadastrarProblemaLink}">Cadastrar
									problema</a></li>
						</ul></li>
					<c:url var="submissoesLink" value="../submissao/listar" />
					<li><a href="${submissoesLink}">Submissões</a></li>
					<c:url var="rankLink" value="../usuario/rank" />
					<c:url var="universidadesLink" value="../universidade/rank" />
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">Ranks<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li><a href="${rankLink}">Rank principal</a></li>
							<li><a href="${universidadesLink}">Universidades</a></li>
						</ul></li>
					<c:url var="logoutLink" value="../usuario/logout" />
					<li><a href="${logoutLink}">Sair</a></li>
				</ul>
			</div>
		</nav>
		<c:if test="${mensagem != null}">
			<div class="alert ${mensagem.tipoDeAlerta.nome} alert-dismissable">
				<a href="#" class="close" data-dismiss="alert" aria-label="close">×</a>
				${mensagem.conteudo}
			</div>
		</c:if>
	</div>
</div>

