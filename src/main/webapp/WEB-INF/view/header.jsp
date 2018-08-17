<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
  <div class="col-sm-2"></div>

  <div class="col-sm-8" align="center">
    <nav class="navbar navbar-inverse">
      <div class="container-fluid">
        <div class="navbar-header">
          <a class="navbar-brand" href="/user/home">Naive Online Judge</a>
        </div>
        <ul class="nav navbar-nav">
          <li class="active">
            <a href="/user/home">Home</a>
          </li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Perfil<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="/user/configurations">Configurações</a></li>
              <c:if test="${user != null}">
                <li><a href="/user/statistics/${user.id}">Estatísticas</a></li>
              </c:if>
            </ul>
          </li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Problemas<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="/problem/problems">Listar</a></li>
              <c:forEach var="category" items="${categories}">
                <li><a href="/problem/category/${category.id}">${category.name}</a></li>
              </c:forEach>
              <li><a href="/problem/my-problems">Meus problemas</a></li>
              <li><a href="/problem/register">Cadastrar problema</a></li>
            </ul>
          </li>

          <li><a href="/submission/submissions">Submissões</a></li>

          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#">Ranks<span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="/user/rank">Rank principal</a></li>
              <li><a href="/university/rank">Universidades</a></li>
            </ul>
          </li>
          <c:if test="${user == null}">
            <li><a href="/user/login">Login</a></li>
          </c:if>
          <c:if test="${user != null}">
            <li><a href="/user/logout">Sair</a></li>
          </c:if>
        </ul>
      </div>
    </nav>
    <c:if test="${message != null}">
      <div class="alert ${message.alertType.name} alert-dismissable">
        <a href="#" class="close" data-dismiss="alert" aria-label="close">
          <span aria-hidden="true">&times;</span>
        </a>
        ${message.content}
      </div>
    </c:if>
  </div>
</div>

