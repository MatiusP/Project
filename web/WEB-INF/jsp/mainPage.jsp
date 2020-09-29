<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>

    <fmt:setLocale value="${sessionScope.locale}"/>
    <fmt:setBundle basename="property.locale" var="loc"/>

    <fmt:message bundle="${loc}" key="local.button.be" var="be_button"/>
    <fmt:message bundle="${loc}" key="local.button.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.button.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.mes.mainMes.mainPage" var="message"/>

</head>

<body>
<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="be_BY"/>
    <input type="submit" value="${be_button}"/>
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="ru_RU"/>
    <input type="submit" value="${ru_button}"/>
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="en_EN"/>
    <input type="submit" value="${en_button}"/>
</form>

<c:out value="${message}"/>
<c:out value="Привет!"/>


<a href="userController?command=authentication">Sign in</a>

</body>
</html>