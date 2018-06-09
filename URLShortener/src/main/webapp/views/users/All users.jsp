<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h3 style="margin: 10px;">All users</h3>
<table class="table table-hover table-inverse users-table">
	<thead>
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Roles</th>
			<th>Phone number</th>
			<th>Delete</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${users}">
			<tr user-id="${user.id}">
				<td>${user.id}</td>
				<td><a href="/users/${user.id}/urls">${user.firstName} ${user.lastName}</a></td>
				<td>${user.roles}</td>
				<td>${user.phoneNumber}</td>
				<td><a href="" onclick="removeUser(${user.id});">[delete]</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script>
	function removeUser(id) {
		$.ajax({
			  url: '/users/' + id,
			  type: 'DELETE',
			  dataType: 'json',
			  success: function(data) {
				  if(data.toUpperCase().includes("error".toUpperCase())) {
					  alert(data);
				  } else {
					  $(".users-table tbody tr[user-id='" + id + "']").remove();
				  }
			  }
		});
	}
</script>
