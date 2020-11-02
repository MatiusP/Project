<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_style.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/buttons_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>


<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.userManagement.mainMessage" var="main_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.noUsers.message" var="no_users_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.deleteUserResult.message" var="delete_result_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.editUserResult.message" var="edit_result_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.login.message" var="login_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.surname.message" var="surname_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.name.message" var="name_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.passportNumber.message" var="passport_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.driverLicense.message" var="driv_lic_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.dateBirth.message" var="date_birth_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.e-mail.message" var="e_mail_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.phone.message" var="phone_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.status.message" var="status_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.role.message" var="role_message"/>
<fmt:message bundle="${loc}" key="local.userManagement.find.button" var="find_button"/>
<fmt:message bundle="${loc}" key="local.userManagement.exit.button" var="exit_button"/>
<fmt:message bundle="${loc}" key="local.userManagement.edit.button" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.userManagement.delete.button" var="delete_button"/>


<head>
    <title>User management</title>
</head>

<body>
<div class="h3">
    <h3>${main_message}</h3>
</div>

<form id="find_user" action="mainController" method="post">
    <table class="table_dark">
        <tr>
            <th>id</th>
            <th>${login_message}</th>
            <th>${surname_message}</th>
            <th>${name_message}</th>
            <th>${passport_message}</th>
            <th>${driv_lic_message}</th>
            <th>${date_birth_message}</th>
            <th>${e_mail_message}</th>
            <th>${phone_message}</th>
            <th>${status_message}</th>
            <th>${role_message}</th>
        </tr>
        <tr>
            <td><input type="number" name="id" min="0" max="100000" placeholder=""/></td>
            <td><input type="text" name="login" placeholder="${login_message}"/></td>
            <td><input type="text" name="surname" placeholder="${surname_message}"/></td>
            <td><input type="text" name="name" placeholder="${name_message}"/></td>
            <td><input type="text" name="passportID" placeholder="${passport_message}"/></td>
            <td><input type="text" name="driverLicense" placeholder="${driv_lic_message}"/></td>
            <td><input type="date" name="dateOfBirth" placeholder="${date_birth_message}"/></td>
            <td><input type="email" name="eMail" placeholder="${e_mail_message}"/></td>
            <td><input type="text" name="phone" placeholder="+xxx xx xxx xx xx"/></td>
            <td>
                <select name="status">
                    <option selected="ACTIVE">ACTIVE</option>
                    <option value="DELETED">DELETED</option>
                </select>
            <td>
                <select name="role">
                    <option selected=""></option>
                    <option value="CLIENT">CLIENT</option>
                    <option value="ADMIN">ADMIN</option>
                </select>
            </td>
        </tr>
    </table>
    <input type="hidden" name="command" value="find_user"/>
    <button class="bot5" form="find_user" type="submit">${find_button}</button>
</form>

<form action="mainController" method="post">
    <input type="hidden" name="command" value="">

    <c:if test="${not empty sessionScope.get('deleteUserResult')}">
        <div class="info-message">
            <h3><c:out value="${delete_result_message}"/></h3>
        </div>
        ${sessionScope.remove('deleteUserResult')}
    </c:if>

    <c:if test="${sessionScope.editUserResult != null}">
        <div class="info-message">
            <h3><c:out value="${edit_result_message}"/></h3>
        </div>
        ${sessionScope.remove('editUserResult')}
    </c:if>

    <c:choose>
        <c:when test="${not empty requestScope.noUsersFound}">
            <div class="h3">
                <h3><c:out value="${no_users_message}"/></h3>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty usersFoundList}">
                <table class="table_dark">
                    <tr>
                        <th>id</th>
                        <th>${login_message}</th>
                        <th>${surname_message}</th>
                        <th>${name_message}</th>
                        <th>${passport_message}</th>
                        <th>${driv_lic_message}</th>
                        <th>${date_birth_message}</th>
                        <th>${e_mail_message}</th>
                        <th>${phone_message}</th>
                        <th>${status_message}</th>
                        <th>${role_message}</th>
                    </tr>

                    <c:forEach items="${usersFoundList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.login}</td>
                            <td>${user.surname}</td>
                            <td>${user.name}</td>
                            <td>${user.passportIdNumber}</td>
                            <td>${user.driverLicense}</td>
                            <td>${user.dateOfBirth}</td>
                            <td>${user.eMail}</td>
                            <td>${user.phone}</td>
                            <td>${user.status}</td>
                            <td>${user.role}</td>

                            <c:if test="${user.login  != currentUserLogin}">
                                <c:choose>
                                    <c:when test="${user.status eq 'ACTIVE'}">
                                        <td>
                                            <a href="mainController?command=go_to_edit_user_data_page&id=${user.id}"/>${edit_button}
                                        </td>
                                        <td>
                                            <a href="mainController?command=delete_user&deleteUserId=${user.id}"/>${delete_button}
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <a href="mainController?command=go_to_edit_user_data_page&id=${user.id}"/>${edit_button}
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:otherwise>
    </c:choose>
</form>

<form id="exit" action="mainController?command=go_to_main_page" method="post">
    <button class="bot5" form="exit" type="submit">${exit_button}</button>
</form>

</body>
</html>
