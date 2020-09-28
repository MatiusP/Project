<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Authentication page</title>
</head>
<body>
<h2>Sign in to MotorRent</h2>

<c:if test="${not empty sessionScope.get('authError')}">
    <c:out value="${sessionScope.get('authError')}"/>
</c:if>


<form action="userController" method="get">
    <input type="hidden" name="command" value="check_user_auth_data"/>
    Login <br/>
    <input type="text" name="login" placeholder="login"/><br/>
    Password <br/>
    <input type="password" name="password" placeholder="password"/><br/>
    <input type="submit" value="Sign in"/><br/>
</form>

New to MotorRent? <a href="userController?command=registration">Create an account</a>

</body>
</html>
