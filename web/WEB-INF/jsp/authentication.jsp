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
<fmt:message bundle="${loc}" key="local.authError.message" var="auth_error"/>

<jsp:include page="PageHeader.jsp"/>

<head>
    <title>Authentication page</title>
</head>

<body>
<h2>${message}</h2>

<c:if test="${not empty sessionScope.get('authError')}">
    <c:out value="${auth_error}"/>
</c:if>

<form action="mainController" method="get">
    <input type="hidden" name="command" value="check_user_auth_data"/>
    ${login} <br/>
    <input type="text" name="login" placeholder="${login}"/><br/>
    ${password} <br/>
    <input type="password" name="password" placeholder="${password}"/><br/><br/>
    <input type="submit" value="${sign_in}"/><br/>
</form>

<form>
    <select name="menu" size="1">
        <option value="first">First</option>
        <option value="second">Second</option>
        <option value="third">Third</option>
        <option value="four">Four</option>
        <option value="five">Five</option>
    </select>
</form>

${message_1_AuthPage} <a href="mainController?command=registration">${message_2_AuthPage}</a>

</body>
</html>
