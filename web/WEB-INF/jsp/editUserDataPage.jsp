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

<body>

<form action="mainController" method="post" class="form-3">
    <input type="hidden" name="command" value="edit_user_data">
    <input type="hidden" name="currentUserID" value="${sessionScope.userRegData.id}"/>
    <input type="hidden" name="currentLogin" value="${sessionScope.currentUserLogin}">
    <input type="hidden" name="role" value="${sessionScope.userRegData.role}"/>

    <h3>${page_message}</h3>

    <p class="clearfix">
        <label>${login}</label>
        <input type="text" name="login" value="${sessionScope.currentUserLogin}"/>
    </p>
    <p class="clearfix">
        <label>${current_password}</label>
        <h4><input type="password" name="currentPassword" placeholder="current_password"/></h4>
    </p>
    <p class="clearfix">
        <label>${new_password}</label>
        <h4><input type="password" name="newPassword" placeholder="new_password"/></h4>
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
    <c:if test="${sessionScope.userRegData.role == 2}">
        <p class="clearfix">
            <label>${role}</label>
            <input type="text" name="role" value="${userRegData.role}" required/>
        </p>
    </c:if>

    <div class="message">
        <br/>
        <c:if test="${not empty requestScope.get('fillRegDataError')}">
            <c:out value="${fill_reg_data_error}"/><br/>
        </c:if><br/>

        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if><br/>
    </div>


    <p class="clearfix">
        <input type="submit" value="${button}"><br/>
    </p>
</form>

<form>
    <input type="submit" value="Edit user data"/>
</form>

<form action="mainController?command=go_to_main_page" method="post">
    <input type="submit" value="Exit"/>
</form>


</body>
</html>
