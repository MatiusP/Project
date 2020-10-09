<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>All users page</title>

    <jsp:include page="headerPage.jsp"/>
</head>
<body>

<h1>Список всех пользователей системы</h1>


<c:choose>
    <c:when test="${not empty requestScope.noUsersException}">
        <h3><c:out value="${requestScope.noUsersException}"/></h3>
    </c:when>
    <c:otherwise>
        <table style="with: 100%" border="1">
            <tr>
                <td>id</td>
                <td>login</td>
                <td>password</td>
                <td>surname</td>
                <td>name</td>
                <td>passportIdNumber</td>
                <td>driverLicense</td>
                <td>dateOfBirth</td>
                <td>eMail</td>
                <td>phone</td>
                <td>role</td>
            </tr>

            <c:forEach items="${requestScope.usersList}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.login}</td>
                    <td>${user.password}</td>
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
    </c:otherwise>
</c:choose>


</body>
</html>
