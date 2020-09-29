<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.button.be" var="be_button"/>
<fmt:message bundle="${loc}" key="local.button.ru" var="ru_button"/>
<fmt:message bundle="${loc}" key="local.button.en" var="en_button"/>
<fmt:message bundle="${loc}" key="local.message.signIn" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.message.mainPage" var="message"/>

<head>
    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
    <title>Insert title here</title>
</head>

<body>
<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="be">
    <input type="submit" value="${be_button}">
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="ru">
    <input type="submit" value="${ru_button}">
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="en">
    <input type="submit" value="${en_button}">
</form>

<h2><c:out value="${message}"/></h2>


<a href="userController?command=authentication">${sign_in}</a>

</body>
</html>