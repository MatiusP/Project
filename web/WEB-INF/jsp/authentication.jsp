<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.mesage.signIn.authPage" var="message"/>
<fmt:message bundle="${loc}" key="local.message.login" var="login"/>
<fmt:message bundle="${loc}" key="local.message.password" var="password"/>
<fmt:message bundle="${loc}" key="local.message.signIn" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.reg.message.authPage.message1" var="message_1_AuthPage"/>
<fmt:message bundle="${loc}" key="local.reg.message.authPage.message2" var="message_2_AuthPage"/>


<head>
    <title>Authentication page</title>
</head>
<body>
<h2>${message}</h2>

<c:if test="${not empty sessionScope.get('authError')}">
    <c:out value="${sessionScope.get('authError')}"/>
</c:if>


<form action="userController" method="get">
    <input type="hidden" name="command" value="check_user_auth_data"/>
    ${login} <br/>
    <input type="text" name="login" placeholder="${login}"/><br/>
    ${password} <br/>
    <input type="password" name="password" placeholder="${password}"/><br/>
    <input type="submit" value="${sign_in}"/><br/>
</form>

${message_1_AuthPage} <a href="userController?command=registration">${message_2_AuthPage}</a>

</body>
</html>
