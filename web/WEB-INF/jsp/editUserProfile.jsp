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
<fmt:message bundle="${loc}" key="local.editPage.repeatPassword" var="repeat_password"/>
<fmt:message bundle="${loc}" key="local.editSurname.message" var="surname"/>
<fmt:message bundle="${loc}" key="local.editName.message" var="name"/>
<fmt:message bundle="${loc}" key="local.editPassportID.message" var="passport_ID"/>
<fmt:message bundle="${loc}" key="local.editDriverLicense.message" var="driver_license"/>
<fmt:message bundle="${loc}" key="local.editDateOfBirth.message" var="date_birth"/>
<fmt:message bundle="${loc}" key="local.editEMail.message" var="e_mail"/>
<fmt:message bundle="${loc}" key="local.editPhone.message" var="phone"/>
<fmt:message bundle="${loc}" key="local.editRole.message" var="role"/>
<fmt:message bundle="${loc}" key="local.editUser.button" var="button"/>
<fmt:message bundle="${loc}" key="local.regBack.button" var="back_button"/>
<fmt:message bundle="${loc}" key="local.editPasswordsError.message" var="passwords_error"/>
<fmt:message bundle="${loc}" key="local.regPage.loginValidRules.message" var="login_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.passwordValidRules.message" var="password_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.surnameValidRules.message" var="surname_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.nameValidRules.message" var="name_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.passportValidRules.message" var="passport_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.driverLicValidRules.message" var="driverLic_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.dateBirthValidRules.message" var="dateBirth_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.emailValidRules.message" var="email_valid_rules"/>
<fmt:message bundle="${loc}" key="local.regPage.phoneValidRules.message" var="phone_valid_rules"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editProfileForm.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>
<form action="mainController" method="post" class="edit_profile_form">
    <input type="hidden" name="command" value="edit_profile">
    <input type="hidden" name="currentUserID" value="${userRegData.id}"/>
    <input type="hidden" name="currentRole" value="${userRegData.role}">
    <input type="hidden" name="currentLogin" value="${currentUserLogin}">


    <h3>${page_message}</h3>

    <div class="form_field">
        <label>${login}</label>
        <input type="text" name="login" placeholder="" value="${currentUserLogin}"
               pattern="^(?=[a-zA-ZА-ЯЁа-яё0-9._]{5,45}$)(?!.*[_.]{2})[^_.].*[^_.]$"
               required/>
        <span class="form_error">${login_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${current_password}</label>
        <input type="password" name="currentPassword" placeholder="current_password"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,45}$"/>
        <span class="form_error">${password_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${new_password}</label>
        <input type="password" name="newPassword" placeholder="new_password"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,45}$"/>
        <span class="form_error">${password_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${repeat_password}</label>
        <input type="password" name="repeatNewPassword" placeholder="repeat_new_password"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{5,45}$"/>
        <c:if test="${not empty requestScope.get('passwordsError')}">
            <div class="edit_profile-pas-message">
                <c:out value="${passwords_error}"/>
            </div>
        </c:if>
        <span class="form_error">${password_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${surname}</label>
        <input type="text" name="surname" placeholder="" value="${userRegData.surname}"
               pattern="^.{1,75}$"
               required/>
        <span class="form_error">${surname_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${name}</label>
        <input type="text" name="name" placeholder="" value="${userRegData.name}"
               pattern="^.{1,75}$"
               required/>
        <span class="form_error">${name_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${passport_ID}</label>
        <input type="text" name="passportID" placeholder="" value="${userRegData.passportIdNumber}"
               pattern="^(?!^0+$)[a-zA-Z0-9]{5,14}$"
               required/>
        <span class="form_error">${passport_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${driver_license}</label>
        <input type="text" name="driverLicense" placeholder="" value="${userRegData.driverLicense}"
               pattern="^(?!^0+$)[a-zA-Z0-9]{3,14}$"
               required/>
        <span class="form_error">${driverLic_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${date_birth}</label>
        <input type="text" name="dateOfBirth" placeholder="" value="${userRegData.dateOfBirth}"
               pattern="^(([1][9][0-9]\d)|([2][0][0-1]\d))((\/)|(-))(((0)[0-9])|((1)[0-2]))((\/)|(-))([0-2][0-9]|(3)[0-1])$"
               required/>
        <span class="form_error">${dateBirth_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${e_mail}</label>
        <input type="email" name="eMail" placeholder="" value="${userRegData.eMail}"
               pattern="^[a-zA-Z]{1}[a-zA-Z\d\u002E\u005F]+@([a-zA-Z]+\u002E){1,2}[a-z]{1,3}$"
               required/>
        <span class="form_error">${email_valid_rules}</span>
    </div>

    <div class="form_field">
        <label>${phone}</label>
        <input type="text" name="phone" placeholder="" value="${userRegData.phone}"
               pattern="^(\+?\d{1,3})([- .]?\d{2,3})([- .]?\d{3})([- .]?\d{1,2})([- .]?\d{1,2})$"
               required/>
        <span class="form_error">${phone_valid_rules}</span>
    </div>


    <div class="edit_profile-message">
        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if>
    </div>


    <div>
        <input type="submit" value="${button}">
    </div>

    <div>
        <a href="mainController?command=go_to_main_page">${back_button}</a>
    </div>
</form>
</body>
</html>
