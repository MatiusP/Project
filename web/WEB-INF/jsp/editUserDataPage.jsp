<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit user data page</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.regLogin.message" var="reg_login_mess"/>
<fmt:message bundle="${loc}" key="local.regSurname.message" var="reg_surname_mess"/>
<fmt:message bundle="${loc}" key="local.regName.message" var="reg_name_mess"/>
<fmt:message bundle="${loc}" key="local.regPassportID.message" var="reg_passportID_mess"/>
<fmt:message bundle="${loc}" key="local.regDriverLicense.message" var="reg_driver_license_mess"/>
<fmt:message bundle="${loc}" key="local.regDateBirth.message" var="reg_date_birth_mess"/>
<fmt:message bundle="${loc}" key="local.regEMail.message" var="reg_email_mess"/>
<fmt:message bundle="${loc}" key="local.regPhone.message" var="reg_phone_mess"/>

<body>

<jsp:useBean id="userRegData" class="by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO" scope="session"/>
<jsp:include page="PageHeader.jsp"/>


<p>
<h3>${page_message}</h3><br/>

<form action="mainController" method="post">
    <input type="hidden" name="command" value="edit_user_data">
    <input type="hidden" name="currentUserID" value="${sessionScope.get('currentUserID')}"/>
    <input type="hidden" name="currentLogin" value="${sessionScope.get('currentUserLogin')}">

    <table style="with: 50%" border="1">
        <tr>
            <td>Login</td>
            <td><input type="text" name="login" value="${sessionScope.currentUserLogin}" required/></td>
        </tr>
        <tr>
            <td>Current password</td>
            <td><input type="password" name="currentPassword" placeholder="current_password"/></td>
        </tr>
        <tr>
            <td>New password</td>
            <td><input type="password" name="newPassword" placeholder="new_password"/></td>
        </tr>
        <tr>
            <td>${reg_surname_mess}</td>
            <td><input type="text" name="surname" value="${userRegData.surname}" required/></td>
        </tr>
        <tr>
            <td>${reg_name_mess}</td>
            <td><input type="text" name="name" value="${userRegData.name}" required/></td>
        </tr>
        <tr>
            <td>${reg_passportID_mess}</td>
            <td><input type="text" name="passportID" value="${userRegData.passportIdNumber}" required/></td>
        </tr>
        <tr>
            <td>${reg_driver_license_mess}</td>
            <td><input type="text" name="driverLicense" value="${userRegData.driverLicense}" required/></td>
        </tr>
        <tr>
            <td>${reg_date_birth_mess}</td>
            <td><input type="text" name="dateOfBirth" value="${userRegData.dateOfBirth}" required/></td>
        </tr>
        <tr>
            <td>${reg_email_mess}</td>
            <td><input type="text" name="eMail" value="${userRegData.eMail}" required/></td>
        </tr>
        <tr>
            <td>${reg_phone_mess}</td>
            <td><input type="text" name="phone" value="${userRegData.phone}" required/></td>
        </tr>
        <c:if test="${sessionScope.get('currentUserRole') == 2}">
            <tr>
                <td>User role</td>
                <td><input type="text" name="role" value="${userRegData.role}" required/></td>
            </tr>
        </c:if>
    </table>

    <c:if test="${requestScope.get('role') == null}">
        <input type="hidden" name="role" value="${userRegData.role}"/>
    </c:if>

    <input type="submit" value="Edit user data"/>
</form>

<form action="mainController?command=go_to_main_page" method="post">
    <input type="submit" value="Exit"/>
</form>


</body>
</html>
