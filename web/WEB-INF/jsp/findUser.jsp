<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>

<jsp:include page="PageHeader.jsp"/>

<head>
    <title>Title</title>
</head>
<body>


<p>
<h3>Заполните данные для поиска пользователя</h3><br/>

<form action="mainController" method="post">

    <table style="with: 50%" border="1">
        <tr>
            <td>User id</td>
            <td><input type="number" name="id" placeholder="id"/></td>
        </tr>
        <tr>
            <td>User login</td>
            <td><input type="text" name="login" placeholder="login"/></td>
        </tr>
        <tr>
            <td>User surname</td>
            <td><input type="text" name="surname" placeholder="surname"/></td>
        </tr>
        <tr>
            <td>User name</td>
            <td><input type="text" name="name" placeholder="name"/></td>
        </tr>
        <tr>
            <td>User passport ID number</td>
            <td><input type="text" name="passportID" placeholder="passportIdNumber"/></td>
        </tr>
        <tr>
            <td>User driver license number</td>
            <td><input type="text" name="driverLicense" placeholder="driver license number"/></td>
        </tr>
        <tr>
            <td>User date of birth</td>
            <td><input type="text" name="dateOfBirth" placeholder="date of birth}"/></td>
        </tr>
        <tr>
            <td>User e-mail</td>
            <td><input type="text" name="eMail" placeholder="e-mail"/></td>
        </tr>
        <tr>
            <td>User phone</td>
            <td><input type="text" name="phone" placeholder="+xxx xx xxx xx xx"/></td>
        </tr>
        <tr>
            <td>User role</td>
            <td><input type="text" name="role" placeholder="role"/></td>
        </tr>
    </table>


    <c:choose>
        <c:when test="${not empty sessionScope.validationError}">
            <c:out value="${sessionScope..validationError}"
            
        </c:when>
        <c:otherwise>
            <a href="mainController?command=authentication">${sign_in}</a>
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
