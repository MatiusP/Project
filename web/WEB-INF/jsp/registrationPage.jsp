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
<fmt:message bundle="${loc}" key="local.goToAuthentication.message1" var="go_to_auth"/>
<fmt:message bundle="${loc}" key="local.goToAuthentication.message2" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.passwordsError.message" var="passwords_error"/>
<fmt:message bundle="${loc}" key="local.fillRegDataError.message" var="fill_reg_data_error"/>
<fmt:message bundle="${loc}" key="local.regPage.loginValidRules.message" var="login_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.passwordValidRules.message" var="password_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.surnameValidRules.message" var="surname_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.nameValidRules.message" var="name_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.passportValidRules.message" var="passport_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.driverLicValidRules.message" var="driverLic_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.dateBirthValidRules.message" var="dateBirth_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.emailValidRules.message" var="email_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.phoneValidRules.message" var="phone_valid_rules"/>

<head>
    <meta charset="UTF-8">
    <title>Registration form</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/regForm.css"/>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<form class="registration" action="mainController" method="post">
    <input type="hidden" name="command" value="save_new_user">

    <h3>${mainMessage}</h3>

    <div class="form_field">
        <label>${enter_login}</label>
        <input type="text" name="login" placeholder="${login}"
               pattern="^(?=[a-zA-ZА-ЯЁа-яё0-9._]{5,45}$)(?!.*[_.]{2})[^_.].*[^_.]$"
               required/>
        <span class="form_error">${login_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_pass}</label>
        <input type="password" name="password" placeholder="${password}"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,45}$"
               required/>
        <span class="form_error">${password_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${repeat_pass}</label>
        <input type="password" name="password_repeat" placeholder="${repeat_password}" required/>
        <c:if test="${not empty requestScope.get('passwordsError')}">
            <div class="registration-pas-message">
                <c:out value="${passwords_error}"/>
            </div>
        </c:if>
        <span class="form_error">${password_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_surname}</label>
        <input type="text" name="surname" placeholder="${surname}"
               pattern="^.{1,75}$"
               required/>
        <span class="form_error">${surname_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_name}</label>
        <input type="text" name="name" placeholder="${name}"
               pattern="^.{1,75}$"
               required/>
        <span class="form_error">${name_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_passport_ID}</label>
        <input type="text" name="passport_Id_Number" placeholder="${passport}"
               pattern="^(?!^0+$)[a-zA-Z0-9]{5,14}$"
               required/>
        <span class="form_error">${passport_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_driver_license}</label>
        <input type="text" name="driver_license" placeholder="${driver_license}"
               pattern="^(?!^0+$)[a-zA-Z0-9]{3,14}$"
               required/>
        <span class="form_error">${driverLic_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_date_birth}</label>
        <input type="text" name="date_of_birth" placeholder="${date_birth}"
               pattern="^(([1][9][0-9]\d)|([2][0][0-1]\d))((\/)|(-))(((0)[0-9])|((1)[0-2]))((\/)|(-))([0-2][0-9]|(3)[0-1])$"
               required/>
        <span class="form_error">${dateBirth_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_email}</label>
        <input type="email" name="e_mail" placeholder="e-mail"
               pattern="^[a-zA-Z]{1}[a-zA-Z\d\u002E\u005F]+@([a-zA-Z]+\u002E){1,2}[a-z]{1,3}$"
               required/>
        <span class="form_error">${email_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${enter_phone}</label>
        <input type="text" name="phone" placeholder="+xxx xx xxx xx xx"
               pattern="^(\+?\d{1,3})([- .]?\d{2,3})([- .]?\d{3})([- .]?\d{1,2})([- .]?\d{1,2})$"
               required/>
        <span class="form_error">${phone_valid_rules}</span>
    </div>

    <div class="registration-message">
        <br/>
        <c:if test="${not empty requestScope.get('fillRegDataError')}">
            <c:out value="${fill_reg_data_error}"/><br/>
        </c:if><br/>
        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if><br/>
        <p>
            <input type="submit" value="${button}"><br/>
        </p>
        ${go_to_auth} <a href="mainController?command=authentication">${sign_in}</a>
    </div>


</form>

</body>

</html>