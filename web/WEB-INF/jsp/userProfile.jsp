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
<fmt:message bundle="${loc}" key="local.regBack.button" var="back_button"/>

<head>
    <title>User profile</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userProfileForm_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>


<body>

<form action="mainController" method="post" class="profile_form">
    <input type="hidden" name="command" value="go_to_main_page">

    <c:if test="${registration_successfully != null}">
        <h2>${main_message}</h2>
        ${sessionScope.remove('registration_successfully')}
    </c:if>

    <h3>${page_message}</h3>

    <div>
        <label>${reg_surname_mess}</label>
        <output>${userRegData.surname}</output>
    </div>

    <div>
        <label>${reg_name_mess}</label>
        <output>${userRegData.name}</output>
    </div>

    <div>
        <label> ${reg_passportID_mess}</label>
        <output>${userRegData.passportIdNumber}</output>
    </div>

    <div>
        <label>${reg_driver_license_mess}</label>
        <output>${userRegData.driverLicense}</output>
    </div>

    <div>
        <label>${reg_date_birth_mess}</label>
        <output>${userRegData.dateOfBirth}</output>
    </div>

    <div>
        <label>${reg_email_mess}</label>
        <output>${userRegData.eMail}</output>
    </div>

    <div>
        <label>${reg_phone_mess}</label>
        <output>${userRegData.phone}</output>
    </div>

    <div>
        <a href="mainController?command=go_to_main_page">${back_button}</a>
    </div>

</form>


</body>
</html>
