<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit user data page</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.editUserData.pageMessage" var="page_message"/>
<fmt:message bundle="${loc}" key="local.editLogin.message" var="login"/>
<fmt:message bundle="${loc}" key="local.editPassword.message1" var="current_password"/>
<fmt:message bundle="${loc}" key="local.editPassword.message2" var="new_password"/>
<fmt:message bundle="${loc}" key="local.editSurname.message" var="surname"/>
<fmt:message bundle="${loc}" key="local.editName.message" var="name"/>
<fmt:message bundle="${loc}" key="local.editPassportID.message" var="passport_ID"/>
<fmt:message bundle="${loc}" key="local.editDriverLicense.message" var="driver_license"/>
<fmt:message bundle="${loc}" key="local.editDateOfBirth.message" var="date_birth"/>
<fmt:message bundle="${loc}" key="local.editEMail.message" var="e_mail"/>
<fmt:message bundle="${loc}" key="local.editPhone.message" var="phone"/>
<fmt:message bundle="${loc}" key="local.editRole.message" var="role"/>
<fmt:message bundle="${loc}" key="local.editUser.button" var="button"/>

<jsp:useBean id="userRegData" class="by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO" scope="session"/>
<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editUserForm_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<form action="mainController" method="post" class="registration">
    <input type="hidden" name="command" value="edit_user_data">
    <input type="hidden" name="currentUserID" value="${sessionScope.userRegData.id}"/>

    <h3>${page_message}</h3>

    <p class="clearfix">
        <label>${login}</label>
        <input type="text" name="login" value="${sessionScope.currentUserLogin}"/>
    </p>
    <p class="clearfix">
        <label>${surname}</label>
        <input type="text" name="surname" value="${userRegData.surname}" required/>
    </p>
    <p class="clearfix">
        <label>${name}</label>
        <input type="text" name="name" value="${userRegData.name}" required/>
    </p>
    <p class="clearfix">
        <label>${passport_ID}</label>
        <input type="text" name="passportID" value="${userRegData.passportIdNumber}" required/>
    </p>
    <p class="clearfix">
        <label>${driver_license}</label>
        <input type="text" name="driverLicense" value="${userRegData.driverLicense}" required/>
    </p>
    <p class="clearfix">
        <label>${date_birth}</label>
        <input type="text" name="dateOfBirth" value="${userRegData.dateOfBirth}" required/>
    </p>
    <p class="clearfix">
        <label>${e_mail}</label>
        <input type="text" name="eMail" value="${userRegData.eMail}" required/>
    </p>
    <p class="clearfix">
        <label>${phone}</label>
        <input type="text" name="phone" value="${userRegData.phone}" required/>
    </p>
    <p class="clearfix">
        <label>Is_Deleted</label>
        <input type="text" name="phone" value="${userRegData.phone}" required/>
    </p>
    <c:if test="${sessionScope.userRegData.role eq 'ADMIN'}">
        <p class="clearfix">
            <label>${role}</label>
            <select name="user_role" required>
                <option selected="${userRegData.role}">${userRegData.role}</option>
                <c:choose>
                    <c:when test="${userRegData.role eq 'ADMIN'}">
                        <option value="CLIENT">CLIENT</option>
                    </c:when>
                    <c:otherwise>
                        <option value="ADMIN">ADMIN</option>
                    </c:otherwise>
                </c:choose>
            </select>
        </p>
    </c:if>

    <div class="message">
        <c:if test="${not empty sessionScope.get('fillRegDataError')}">
            <c:out value="${fill_reg_data_error}"/><br/>
        </c:if><br/>

        <c:if test="${not empty sessionScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if>
    </div>

    <p class="clearfix">
        <input type="submit" value="${button}">
    </p>
</form>
</body>
</html>
