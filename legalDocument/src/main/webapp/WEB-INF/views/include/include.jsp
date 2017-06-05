<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}" /> --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<link rel="stylesheet" href="<%=basePath %>static/bootstrap-table-examples-master/assets/bootstrap/css/bootstrap.min.css">
    <script src="<%=basePath %>static/bootstrap-table-examples-master/assets/jquery.min.js"></script>
    <script src="<%=basePath %>static/bootstrap-table-examples-master/assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=basePath %>static/bootstrap-table-examples-master/assets/bootstrap-table/src/bootstrap-table.js"></script>
