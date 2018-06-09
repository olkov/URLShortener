<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="url">${req.requestURL}</c:set>
<c:set var="domain" value="${fn:substring(url,0,fn:length(url)-fn:length(req.requestURI))}${req.contextPath}" />
	<h2 class="h2-title">${name} links</h2>
	<table class="table table-bordered url-table">
		<thead>
			<tr>
				<th style="width: 300px;">
			    	<span class="column-title">Short link</span>
			    </th>
			    <th>
			    	<span class="column-title">
				    	Full link
				  		<i class="fa fa-plus show-hide-url-form-button" title="Click here to create a short link"></i>
			  		</span>
			  		<form class="url-form" action="javascript:submitForm();">
				  		<input type="hidden" id="urlId">
				  		<input pattern="(https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9][a-zA-Z0-9-]+[a-zA-Z0-9]\.[^\s]{2,}|https?:\/\/(?:www\.|(?!www))[a-zA-Z0-9]\.[^\s]{2,}|www\.[a-zA-Z0-9]\.[^\s]{2,})" type="text" name="url" class="form-control url-input" autocomplete="off" placeholder="Input link" required>
				  		<input type="submit" class="btn btn-primary submit-button" value="Create">
			  		</form>
			  	</th> 
			</tr>
		</thead>
		<tbody>
			<tr class="no-data<c:if test="${URLs.isEmpty()}"> show</c:if>">
				<td colspan="2" class="no-links">No links</td>
			</tr>
			<c:forEach var="url" items="${URLs}">
				<tr url-id="${url.id}">
					<td><a target="_blank" href="/<c:out value="${url.shortURL}" />">${domain}/<c:out value="${url.shortURL}" /></a></td>
					<td>
						<a target="_blank" class="full-url" href="<c:out value="${url.URL}" />"><c:out value="${url.URL}" /></a>
						<div class="edit-delete">
							<i class="fa fa-pencil" title="Edit" onclick="editLink(this);"></i>
							<i class="fa fa-times" title="Delete" onclick="deleteLink(${url.id});"></i>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
<script>
	var flag = false;
	
	$(".show-hide-url-form-button").on("click", function() {
		$(".url-form .url-input").val("");
		$(".url-form .submit-button").val("Create");
		$("#urlId").val("");
		show_hide();
	});
	
	function show_hide() {
		var showHideButtonElem = $(".show-hide-url-form-button");
		var urlInputElem = $(".url-form .url-input");
		if(!flag) {
			showHideButtonElem.addClass("fa-minus", 2000);
			$(".url-form").css('display', 'inline-block');
			flag = true;
			urlInputElem.focus();
		} else {
			showHideButtonElem.removeClass("fa-minus", 2000);
			$(".url-form").hide();
			flag = false;
		}
	}
	
	function submitForm() {
		var action = $(".url-form .submit-button").val();
		var urlElem = $(".url-form .url-input");
		if(action == "Create") {
			$.ajax({
				method: "POST",
				url: "/users/${user.id}/urls/add",
				data: {url : urlElem.val()},
				success: function(data) {
					if(data != null && data != undefined) {
						data = JSON.parse(data);
						var newRow = 
							'<tr url-id="' + data.id + '">' +
								'<td>' +
									'<a target="_blank" href="/' + data.shortURL + '">${domain}/' + data.shortURL + '</a>' +
								'</td>' +
								'<td>' +
									'<a target="_blank" class="full-url" href="' + data.URL + '">' + data.URL + '</a>' +
									'<div class="edit-delete">' +
										'<i class="fa fa-pencil" title="Edit" onclick="editLink(this);"></i>' +
										'<i class="fa fa-times" style="margin-left: 10px;" title="Delete" onclick="deleteLink(' + data.id + ');"></i>' +
									'</div>' +
								'</td>' +
							'</tr>';
						$(".no-data").removeClass("show");
						show_hide();
						$(".url-table tbody").append(newRow);
					} else {
						alert("Error!");
					}
				}
			});
		} else {
			$.ajax({
				method: "POST",
				url: "/urls/" + $("#urlId").val() + "/edit",
				data: {url : urlElem.val()},
				success: function(data) {
					if(data != null && data != undefined) {
						data = JSON.parse(data);
						var elemForEdit = $(".url-table tbody tr[url-id='" + data.id + "'] .full-url");
						elemForEdit.attr("href", data.URL);
						elemForEdit.text(data.URL);
						show_hide();
					} else {
						alert("Error!");
					}
				}
			});
		}
	}
	
	function editLink(elem) {
		var urlId = $(elem).parent().parent().parent().attr("url-id");
		var url = $(elem).parent().parent().find("a").attr("href");
		$("#urlId").val(urlId);
		$(".url-form .submit-button").val("Edit");
		$(".url-form .url-input").val(url);
		if(flag == false) {
			show_hide();
		}
	}
	
	function deleteLink(id) {
		$.ajax({
			method: "GET",
			url: "/urls/" + id + "/delete/",
			success: function(data) {
				if(data == "success") {
					$(".url-table tbody tr[url-id='" + id + "']").remove();
					if($(".url-table tbody tr").length <= 1) {
						$(".no-data").addClass("show");
					}
				} else {
					alert(data);
				}
			}
		});
	}
</script>