<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<head>
    <title>Edit user</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.editUser.pageMessage" var="main_message"/>
<fmt:message bundle="${loc}" key="local.editUser.id.message" var="id_message"/>
<fmt:message bundle="${loc}" key="local.editUser.login.message" var="login_message"/>
<fmt:message bundle="${loc}" key="local.editUser.surname.message" var="surname_message"/>
<fmt:message bundle="${loc}" key="local.editUser.name.message" var="name_message"/>
<fmt:message bundle="${loc}" key="local.editUser.eMail.message" var="email_message"/>
<fmt:message bundle="${loc}" key="local.editUser.phone.message" var="phone_message"/>
<fmt:message bundle="${loc}" key="local.editUser.status.message" var="status_message"/>
<fmt:message bundle="${loc}" key="local.editUser.role.message" var="role_message"/>
<fmt:message bundle="${loc}" key="local.editUser.edit.button" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.editUser.back.button" var="back_button"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/editProfileForm_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<form action="mainController" method="post" class="edit_profile_form">
    <input type="hidden" name="command" value="edit_user_data"/>
    <input type="hidden" name="passportID" value="${user.passportIdNumber}"/>
    <input type="hidden" name="driverLicense" value="${user.driverLicense}"/>
    <input type="hidden" name="dateOfBirth" value="${user.dateOfBirth}"/>

    <h3>${main_message}</h3>

    <div class="form__field">
        <label>${id_message}</label>
        <input type="text" name="id" value="${user.id}" readonly/>
    </div>

    <div class="form__field">
        <label>${login_message}</label>
        <input type="text" name="login" value="${user.login}" readonly/>
    </div>

    <div class="form__field">
        <label>${surname_message}</label>
        <input type="text" name="surname" value="${user.surname}" readonly/>
    </div>

    <div class="form__field">
        <label>${name_message}</label>
        <input type="text" name="name" value="${user.name}" readonly/>
    </div>

    <div class="form__field">
        <label>${email_message}</label>
        <input type="text" name="eMail" value="${user.eMail}" readonly/>
    </div>

    <div class="form__field">
        <label>${phone_message}</label>
        <input type="text" name="phone" value="${user.phone}" readonly/>
    </div>

    <div class="form__field">
        <label>${status_message}</label>
        <select name="status" required>
            <option selected="${user.status}">${user.status}</option>
            <c:choose>
                <c:when test="${user.status eq 'ACTIVE'}">
                    <option value="DELETED">DELETED</option>
                </c:when>
                <c:otherwise>
                    <option value="ACTIVE">ACTIVE</option>
                </c:otherwise>
            </c:choose>
        </select>
    </div>

    <div class="form__field">
        <label>${role_message}</label>
        <select name="role" required>
            <option selected="${user.role}">${user.role}</option>
            <c:choose>
                <c:when test="${user.role eq 'ADMIN'}">
                    <option value="CLIENT">CLIENT</option>
                </c:when>
                <c:otherwise>
                    <option value="ADMIN">ADMIN</option>
                </c:otherwise>
            </c:choose>
        </select>
    </div>

    <div class="edit_profile-message">
        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if>
    </div>

    <div>
        <input type="submit" value="${edit_button}">
    </div>

    <div>
        <a href="mainController?command=go_to_user_management_page">${back_button}</a>
    </div>
</form>
</body>
</html>
