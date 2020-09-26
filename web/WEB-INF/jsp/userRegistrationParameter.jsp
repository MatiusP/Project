<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User registration parameters</title>
</head>
<body>

	<jsp:useBean id="user" class="by.epamtc.protsko.rentcar.bean.UserDTO"
		scope="request" />

	<h2>Congratulations! You have successfully registered!</h2>
	<h3>Your registration data:</h3>

	<h4>
		Your login:
		<jsp:getProperty property="login" name="user" /></h4>
	<h4>
		Your surname:
		<jsp:getProperty property="surname" name="user" />
	</h4>

	<h4>
		Your name:
		<jsp:getProperty property="name" name="user" />
	</h4>

	<h4>
		Your passport ID number:
		<jsp:getProperty property="passportIdNumber" name="user" />
	</h4>

	<h4>
		Your driver license:
		<jsp:getProperty property="driverLicense" name="user" />
	</h4>

	<h4>
		Your date of birth:
		<jsp:getProperty property="dateOfBirth" name="user" />
	</h4>

	<h4>
		Your e-mail:
		<jsp:getProperty property="eMail" name="user" />
	</h4>

	<h4>
		Your phone:
		<jsp:getProperty property="phone" name="user" />
	</h4>

	<form action="index.jsp" method="post">
		<input type="submit" value="Go to main page" />
	</form>

</body>
</html>