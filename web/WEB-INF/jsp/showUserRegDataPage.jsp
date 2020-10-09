<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.showUserRegData.mainMessage" var="main_message"/>
<fmt:message bundle="${loc}" key="local.showUserRegData.pageMessage" var="page_message"/>
<fmt:message bundle="${loc}" key="local.regSurname.message" var="reg_surname_mess"/>
<fmt:message bundle="${loc}" key="local.regName.message" var="reg_name_mess"/>
<fmt:message bundle="${loc}" key="local.regPassportID.message" var="reg_passportID_mess"/>
<fmt:message bundle="${loc}" key="local.regDriverLicense.message" var="reg_driver_license_mess"/>
<fmt:message bundle="${loc}" key="local.regDateBirth.message" var="reg_date_birth_mess"/>
<fmt:message bundle="${loc}" key="local.regEMail.message" var="reg_email_mess"/>
<fmt:message bundle="${loc}" key="local.regPhone.message" var="reg_phone_mess"/>

<head>
    <title>Title</title>
</head>
<body>

<jsp:useBean id="userRegData" class="by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO" scope="session"/>
<jsp:include page="headerPage.jsp"/>

<p>
<h2>${main_message}</h2></p><br/>
<h3>${page_message}</h3><br/>

<form action="mainController" method="post">
    <input type="hidden" name="command" value="show_user_reg_data">
    <table style="with: 50%" border="1">
        <tr>
            <td>${reg_surname_mess}</td>
            <td><b><c:out value="${userRegData.surname}"/></b></td>
        </tr>
        <tr>
            <td>${reg_name_mess}</td>
            <td><b><c:out value="${userRegData.name}"/></b></td>
        </tr>
        <tr>
            <td>${reg_passportID_mess}</td>
            <td><b><c:out value="${userRegData.passportIdNumber}"/></b></td>
        </tr>
        <tr>
            <td>${reg_driver_license_mess}</td>
            <td><b><c:out value="${userRegData.driverLicense}"/></b></td>
        </tr>
        <tr>
            <td>${reg_date_birth_mess}</td>
            <td><b><c:out value="${userRegData.dateOfBirth}"/></b></td>
        </tr>
        <tr>
            <td>${reg_email_mess}</td>
            <td><b><c:out value="${userRegData.eMail}"/></b></td>
        </tr>
        <tr>
            <td>${reg_phone_mess}</td>
            <td><b><c:out value="${userRegData.phone}"/></b></td>
        </tr>
    </table>
</form>

<form action="mainController?command=go_to_main_page" method="post">
    <input type="submit" value="Main page"/>
</form>


</body>
</html>
