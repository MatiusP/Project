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

<jsp:useBean id="userRegData" class="by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO" scope="session"/>
<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/showUserProfile_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>


<body>
<form action="mainController" method="post" class="form-3">
    <input type="hidden" name="command" value="go_to_main_page">

    <h3>${page_message}</h3>

    <p class="clearfix">
        <label>${reg_surname_mess}</label>
        <output>${userRegData.surname}</output>
    </p>
    <p class="clearfix">
        <label>${reg_name_mess}</label>
        <output>${userRegData.name}</output>
    </p>
    <p class="clearfix">
        <label> ${reg_passportID_mess}</label>
        <output>${userRegData.passportIdNumber}</output>
    </p>
    <p class="clearfix">
        <label>${reg_driver_license_mess}</label>
        <output>${userRegData.driverLicense}</output>
    </p>
    <p class="clearfix">
        <label>${reg_date_birth_mess}</label>
        <output>${userRegData.dateOfBirth}</output>
    </p>
    <p class="clearfix">
        <label>${reg_email_mess}</label>
        <output>${userRegData.eMail}</output>
    </p>
    <p class="clearfix">
        <label>${reg_phone_mess}</label>
        <output>${userRegData.phone}</output>
    </p>
    <p class="clearfix">
        <input type="submit" value="Back"/>
    </p>
</form>


</body>
</html>
