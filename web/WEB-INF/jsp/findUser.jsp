<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>

<jsp:include page="headerPage.jsp"/>

<head>
    <title>Title</title>
</head>
<body>


<p>
<h3>Заполните необходимые поля для поиска пользователя</h3><br/>

<form action="mainController" method="post">

    <table style="with: 50%" border="1">
        <tr>
            <td>id</td>
            <td>login</td>
            <td>surname</td>
            <td>name</td>
            <td>passportIdNumber</td>
            <td>driverLicense</td>
            <td>dateOfBirth</td>
            <td>eMail</td>
            <td>phone</td>
            <td>role</td>
        </tr>

        <tr>
            <td><input type="number" name="id" placeholder="id"/></td>
            <td><input type="text" name="login" placeholder="login"/></td>
            <td><input type="text" name="surname" placeholder="surname"/></td>
            <td><input type="text" name="name" placeholder="name"/></td>
            <td><input type="text" name="passportID" placeholder="passportIdNumber"/></td>
            <td><input type="text" name="driverLicense" placeholder="driver license number"/></td>
            <td><input type="date" name="dateOfBirth" placeholder="date of birth"/></td>
            <td><input type="email" name="eMail" placeholder="e-mail"/></td>
            <td><input type="text" name="phone" placeholder="+xxx xx xxx xx xx"/></td>
            <td><input type="number" name="role" placeholder="role"/></td>
        </tr>
    </table>
    <br/><br/>


    <c:choose>
        <c:when test="${not empty sessionScope.noUsersMessage}">
            <h2><c:out value="${sessionScope.noUsersMessage}"/></h2>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty requestScope.usersFoundList}">
                <table style="with: 100%" border="1">
                    <tr>
                        <td>id</td>
                        <td>login</td>
                        <td>surname</td>
                        <td>name</td>
                        <td>passportIdNumber</td>
                        <td>driverLicense</td>
                        <td>dateOfBirth</td>
                        <td>eMail</td>
                        <td>phone</td>
                        <td>role</td>
                    </tr>

                    <c:forEach items="${requestScope.usersFoundList}" var="user">
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
                            <td>${user.role}</td>
                        </tr>
                    </c:forEach>

                </table>
            </c:if>
        </c:otherwise>
    </c:choose>

    <input type="hidden" name="command" value="find_user"/>
    <input type="submit" value="Find user"/>
</form>

<form action="mainController?command=go_to_main_page" method="post">
    <input type="submit" value="Exit"/>
</form>

</body>
</html>
