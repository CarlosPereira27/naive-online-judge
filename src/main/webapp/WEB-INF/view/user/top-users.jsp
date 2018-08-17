<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="row">
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
      <c:forEach var="user" items="${topUsers}">
        <tr>
          <td><a href="/user/statistics/${user.id}">${user.name}</a></td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>