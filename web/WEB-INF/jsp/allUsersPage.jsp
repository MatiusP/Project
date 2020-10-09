<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>All users page</title>

    <jsp:include page="headerPage.jsp"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table_style.css"/>

</head>
<body>

<h1>Список всех пользователей системы</h1>


<c:choose>
    <c:when test="${not empty requestScope.noUsersException}">
        <h3><c:out value="${requestScope.noUsersException}"/></h3>
    </c:when>
    <c:otherwise>
        <table class="table_dark">
            <tr>
                <th>id</th>
                <th>login</th>
                <th>password</th>
                <th>surname</th>
                <th>name</th>
                <th>passportIdNumber</th>
                <th>driverLicense</th>
                <th>dateOfBirth</th>
                <th>eMail</th>
                <th>phone</th>
                <th>role</th>
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
