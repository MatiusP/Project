<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Our contacts</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.contactPage.main.message" var="main_message"/>
<fmt:message bundle="${loc}" key="local.contactPage.address.message" var="address_message"/>
<fmt:message bundle="${loc}" key="local.contactPage.address.value" var="address_value"/>
<fmt:message bundle="${loc}" key="local.contactPage.phone.message" var="phone_message"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/gooleMap.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/contactPage.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<div class="map-responsive">
    <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2349.512108505146!2d27.587158215993068!3d53.9226452390881!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x46dbcf0a1083af35%3A0x64c379a49d525432!2z0YPQuy4g0K_QutGD0LHQsCDQmtC-0LvQsNGB0LAgMjMvMSwg0JzQuNC90YHQug!5e0!3m2!1sru!2sby!4v1602949623091!5m2!1sru!2sby"
            frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false"
            tabindex="0"></iframe>
</div>

<div class="contactPage">
    <h2>MotorRENT</h2>
    <h3>${main_message}</h3>
    <h4>${address_message} <b>${address_value}</b></h4>
    <h4>${phone_message} <b>+375(29)750-54-82</b></h4>
    <h4>${phone_message} <b>+375(44)511-76-44</b></h4>
</div>

</body>
</html>
