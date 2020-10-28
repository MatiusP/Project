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

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/authForm_style.css"/>

<head>
    <title>Authentication page</title>
</head>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<form class="authentication" action="mainController" method="post">
    <div class="form-inner">
        <h3>${message}</h3>
        <input type="hidden" name="command" value="check_auth_data"/>
        <label>${login}</label>
        <input type="text" name="login" placeholder="${login}"/><br/>
        <label>${password}</label>
        <input type="password" name="password" placeholder="${password}"/>
        <div class="message">
            <c:if test="${not empty sessionScope.get('authError')}">
                <div class="error-message">
                    <c:out value="${auth_error}"/>
                </div>
                ${sessionScope.remove('authError')}
            </c:if>
        </div>
        <input type="submit" value="${sign_in}"/><br/>
        <div class="message">
            ${message_1_AuthPage} <a href="mainController?command=registration">${message_2_AuthPage}</a>
        </div>
    </div>
</form>

</body>

</html>
