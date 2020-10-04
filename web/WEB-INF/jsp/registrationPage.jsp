<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.message.regPage" var="mainMessage"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterLogin" var="enter_login"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterPassword" var="enter_pass"/>
<fmt:message bundle="${loc}" key="local.message.regPage.repeatPassword" var="repeat_pass"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterSurname" var="enter_surname"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterName" var="enter_name"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterPassport" var="enter_passport_ID"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterDriverLicense" var="enter_driver_license"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterDateOfBirth" var="enter_date_birth"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterEMail" var="enter_email"/>
<fmt:message bundle="${loc}" key="local.message.regPage.enterPhone" var="enter_phone"/>
<fmt:message bundle="${loc}" key="local.message.login" var="login"/>
<fmt:message bundle="${loc}" key="local.message.password" var="password"/>
<fmt:message bundle="${loc}" key="local.message.repeat.password" var="repeat_password"/>
<fmt:message bundle="${loc}" key="local.message.surname" var="surname"/>
<fmt:message bundle="${loc}" key="local.message.name" var="name"/>
<fmt:message bundle="${loc}" key="local.message.passport" var="passport"/>
<fmt:message bundle="${loc}" key="local.message.driver_license" var="driver_license"/>
<fmt:message bundle="${loc}" key="local.message.date_birth" var="date_birth"/>
<fmt:message bundle="${loc}" key="local.button.regPage" var="button"/>
<fmt:message bundle="${loc}" key="local.passwordsError.message" var="passwords_error"/>
<fmt:message bundle="${loc}" key="local.fillRegDataError.message" var="fill_reg_data_error"/>

<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
</head>

<jsp:include page="PageHeader.jsp"/>

<body>

<h2>${mainMessage}</h2>

<form action="mainController" method="post">
    <input type="hidden" name="command" value="save_new_user">

    <c:if test="${not empty requestScope.get('fillRegDataError')}">
        <c:out value="${fill_reg_data_error}"/><br/>
    </c:if><br/>

    <c:if test="${not empty requestScope.get('validationError')}">
        <c:out value="${validationError}"/><br/>
    </c:if><br/>

    <table style="with: 50%">
        <tr>
            <td>${enter_login}</td>
            <td><input type="text" name="login" placeholder="${login}" required/></td>
        </tr>
        <tr>
            <td>${enter_pass}</td>
            <td><input type="text" name="password" placeholder="${password}" required/></td>
        </tr>
        <tr>
            <td>${repeat_pass}</td>
            <td><input type="text" name="password_repeat" placeholder="${repeat_password}" required/>
                <c:if test="${not empty requestScope.get('passwordsError')}">
                    <c:out value="${passwords_error}"/>
                </c:if>
            </td>

        </tr>
        <tr>
            <td>${enter_surname}</td>
            <td><input type="text" name="surname" placeholder="${surname}" required/></td>
        </tr>
        <tr>
            <td>${enter_name}</td>
            <td><input type="text" name="name" placeholder="${name}" required/></td>
        </tr>
        <tr>
            <td>${enter_passport_ID}</td>
            <td><input type="text" name="passport_Id_Number" placeholder="${passport}" required/></td>
        </tr>
        <tr>
            <td>${enter_driver_license}</td>
            <td><input type="text" name="driver_license" placeholder="${driver_license}" required/></td>
        </tr>
        <tr>
            <td>${enter_date_birth}</td>
            <td><input type="text" name="date_of_birth" placeholder="${date_birth}" required/></td>
        </tr>
        <tr>
            <td>${enter_email}</td>
            <td><input type="text" name="e_mail" placeholder="e-mail" required/></td>
        </tr>
        <tr>
            <td>${enter_phone}</td>
            <td><input type="text" name="phone" placeholder="+xxx xx xxx xx xx" required/></td>
        </tr>
    </table>
    <input type="submit" value="${button}"><br/>
</form>
</body>

</html>