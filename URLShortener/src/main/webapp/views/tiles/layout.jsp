<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<link rel="stylesheet" href=<c:url value="/resources/bootstrap-4.0.0/bootstrap.min.css" />>
		<link rel="stylesheet" href=<c:url value="/resources/css/styles.css" />>
		<link rel="stylesheet" href=<c:url value="/resources/font-awesome-4.7.0/css/font-awesome.min.css"></c:url>>
		<script type="text/javascript" src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src=<c:url value="/resources/bootstrap-4.0.0/bootstrap.min.js" />></script>
		<script type="text/javascript" src=<c:url value="/resources/js/lib.js" />></script>
		<link rel="shortcut icon" type="image/png" href=<c:url value="/resources/images/icon-website-64.png" />>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><tiles:getAsString name="title" ignore="true"></tiles:getAsString></title>
	</head>
	<body>
		<tiles:insertAttribute name="header"></tiles:insertAttribute>
		<div class="content">
			<tiles:insertAttribute name="body"></tiles:insertAttribute>
		</div>
	</body>
</html>