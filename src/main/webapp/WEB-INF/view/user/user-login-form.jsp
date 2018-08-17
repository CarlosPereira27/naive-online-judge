<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
  <form:form action="/user/login" modelAttribute="login" method="POST" class="form-signin">
    <h2 class="form-signin-heading">Login</h2>
    <form:label for="email" class="sr-only" path="email">Email</form:label>
    <form:input type="email" id="email" class="form-control"
      placeholder="Email address" required="true" autofocus=""
      path="email" />
    <form:label for="password" class="sr-only" path="email">Senha</form:label>
    <form:input type="password" id="password" class="form-control"
      placeholder="Senha" required="true" path="password" />
    <div class="checkbox">
      <label> <input type="checkbox" value="remember-me">
        Lembrar-me
      </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Logar</button>
  </form:form>
  <br>
  <div align="center">
    <a href="/user/register">Criar nova conta</a>
  </div>
</div>