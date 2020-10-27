<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.allUserDataPage.main.message" var="main_message"/>
<fmt:message bundle="${loc}" key="local.findUser.login.message" var="login_message"/>
<fmt:message bundle="${loc}" key="local.findUser.surname.message" var="surname_message"/>
<fmt:message bundle="${loc}" key="local.findUser.name.message" var="name_message"/>
<fmt:message bundle="${loc}" key="local.findUser.passportNumber.message" var="passport_message"/>
<fmt:message bundle="${loc}" key="local.findUser.driverLicense.message" var="driv_lic_message"/>
<fmt:message bundle="${loc}" key="local.findUser.dateBirth.message" var="date_birth_message"/>
<fmt:message bundle="${loc}" key="local.findUser.e-mail.message" var="e_mail_message"/>
<fmt:message bundle="${loc}" key="local.findUser.phone.message" var="phone_message"/>
<fmt:message bundle="${loc}" key="local.findUser.role.message" var="role_message"/>
<fmt:message bundle="${loc}" key="local.allUserDataPage.edit.button" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.allUserDataPage.delete.button" var="delete_button"/>
<fmt:message bundle="${loc}" key="local.allUserDataPage.back.button" var="back_button"/>

<head>
    <title>Title</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/userProfileForm_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>


<c:choose>
    <c:when test="${not empty sessionScope.deleteUserResult}">
        <form id="edit_user_data" action="mainController" method="post" class="registration">
            <h3>${deleteUserResult}</h3>
                ${sessionScope.remove('usersFoundList')}
                ${sessionScope.remove('deleteUserResult')}
            <div class="button">
                <a href="mainController?command=go_to_find_user_page">${back_button}</a>
            </div>
        </form>
    </c:when>
    <c:otherwise>
        <form id="edit_user_data" action="mainController" method="post" class="registration">
            <h3>${main_message}</h3>

            <c:forEach items="${usersFoundList}" var="user">
                <c:if test="${user.id == requestScope.currentId}">

                    <p class="clearfix">
                        <label>Id</label>
                        <output>${user.id}</output>
                    </p>
                    <p class="clearfix">
                        <label>${login_message}</label>
                        <output>${user.login}</output>
                    </p>
                    <p class="clearfix">
                        <label>${surname_message}</label>
                        <output>${user.surname}</output>
                    </p>
                    <p class="clearfix">
                        <label>${name_message}</label>
                        <output>${user.name}</output>
                    </p>
                    <p class="clearfix">
                        <label>${passport_message}</label>
                        <output>${user.passportIdNumber}</output>
                    </p>
                    <p class="clearfix">
                        <label>${driv_lic_message}</label>
                        <output>${user.driverLicense}</output>
                    </p>
                    <p class="clearfix">
                        <label>${date_birth_message}</label>
                        <output>${user.dateOfBirth}</output>
                    </p>
                    <p class="clearfix">
                        <label>${e_mail_message}</label>
                        <output>${user.eMail}</output>
                    </p>
                    <p class="clearfix">
                        <label>${phone_message}</label>
                        <output>${user.phone}</output>
                    </p>
                    <p class="clearfix">
                        <label>${role_message}</label>
                        <output>${user.role}</output>
                    </p>
                    <div class="button">
                        <a href="mainController?command=go_to_edit_user_data_by_admin_page&user=${user.id}">${edit_button}</a>
                    </div>
                    <div class="button">
                        <a href="mainController?command=delete_user&deleteUserId=${requestScope.currentId}&user_login=${user.login}">${delete_button}</a>
                    </div>
                    <div class="button">
                        <a href="mainController?command=go_to_find_user_page">${back_button}</a>
                    </div>
                </c:if>
            </c:forEach>
        </form>
    </c:otherwise>
</c:choose>

</body>
</html>
