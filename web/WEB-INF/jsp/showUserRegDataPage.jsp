<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
</head>
<body>

<jsp:useBean id="userRegData" class="by.epamtc.protsko.rentcar.bean.User" scope="session"/>

<p>
<h2>Congratulations!
    You have successfully registered on the MotorRent website.</h2></p>
Your registration data is:<br/>

Your registration surname: <b><c:out value="${userRegData.surname}"/></b><br/>
Your registration name: <b><c:out value="${userRegData.name}"/></b><br/>
Your registration passport ID number: <b><c:out value="${userRegData.passportIdNumber}"/></b><br/>
Your registration driver license: <b><c:out value="${userRegData.driverLicense}"/></b><br/>
Your registration date of birth: <b><c:out value="${userRegData.dateOfBirth}"/></b><br/>
Your registration e-mail: <b><c:out value="${userRegData.eMail}"/></b><br/>
Your registration phone: <b><c:out value="${userRegData.phone}"/></b><br/>

<form action="userController" method="post">
    <input type="hidden" name="command" value="go_to_main_page"/>
    <input type="submit" value="Main page"/>
</form>


</body>
</html>
