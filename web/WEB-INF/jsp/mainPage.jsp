<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>MotorRENT</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainPage.css"/>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.mainPage.text1.message" var="message_1"/>
<fmt:message bundle="${loc}" key="local.mainPage.text2.message" var="message_2"/>
<fmt:message bundle="${loc}" key="local.mainPage.text3.message" var="message_3"/>
<fmt:message bundle="${loc}" key="local.mainPage.phone.message" var="phone"/>


<div class="main-page">
    <img src="${pageContext.request.contextPath}/images/StartPage.jpg"
         width="100%" height="auto">
    <h2>
	<span>${message_1}
		<br/>
        <br/>
		${message_2}
        <br/>
        ${message_3}
        <span class="spacer"></span>
        <br/>
        ${phone} +375(29)750-54-82
        <span class="spacer"></span>
        <br/>
        ${phone} +375(44)511-76-44
        <br/>
    </span>
    </h2>
</div>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>
<body>
</body>
</html>
<jsp:include page="footerPage.jsp"/>
