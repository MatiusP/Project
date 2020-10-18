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

<fmt:message bundle="${loc}" key="local.findUser.mainMessage" var="main_message"/>
<fmt:message bundle="${loc}" key="local.findUser.login.message" var="login_message"/>
<fmt:message bundle="${loc}" key="local.findUser.surname.message" var="surname_message"/>
<fmt:message bundle="${loc}" key="local.findUser.name.message" var="name_message"/>
<fmt:message bundle="${loc}" key="local.findUser.passportNumber.message" var="passport_message"/>
<fmt:message bundle="${loc}" key="local.findUser.driverLicense.message" var="driv_lic_message"/>
<fmt:message bundle="${loc}" key="local.findUser.dateBirth.message" var="date_birth_message"/>
<fmt:message bundle="${loc}" key="local.findUser.e-mail.message" var="e_mail_message"/>
<fmt:message bundle="${loc}" key="local.findUser.phone.message" var="phone_message"/>
<fmt:message bundle="${loc}" key="local.findUser.status.message" var="status_message"/>
<fmt:message bundle="${loc}" key="local.findUser.role.message" var="role_message"/>
<fmt:message bundle="${loc}" key="local.findUser.find.button" var="find_button"/>
<fmt:message bundle="${loc}" key="local.findUser.exit.button" var="exit_button"/>

<head>
    <title>Title</title>
</head>

<body>

<p>
<h3>${main_message}</h3>

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
            <td><input type="number" name="isDeleted" min="0" max="1" placeholder="0"/></td>
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

    <c:choose>
        <c:when test="${not empty sessionScope.noUsersMessage}">
            <h2><c:out value="${sessionScope.noUsersMessage}"/></h2>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty sessionScope.usersFoundList}">
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
                        <th>${role_message}</th>
                    </tr>

                    <c:forEach items="${usersFoundList}" var="user">
                        <tr>
                            <td>${user.id}</td>
                            <td><a href="mainController?command=show_all_user_data&currentId=${user.id}"/>${user.login}</td>
                            <td>${user.surname}</td>
                            <td>${user.name}</td>
                            <td>${user.passportIdNumber}</td>
                            <td>${user.driverLicense}</td>
                            <td>${user.dateOfBirth}</td>
                            <td>${user.eMail}</td>
                            <td>${user.phone}</td>
                            <td>${user.role}</td>
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
