<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="errorPage.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <title>Error page</title>
</head>


<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.errorPage.main.message" var="message"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/errorPage.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<div class="error-page">
    <img src="${pageContext.request.contextPath}/images/errorPage-img.jpg"
         width="100%" height="auto">
    <h2>${message}</h2>
</div>

<body>


</body>
</html>
