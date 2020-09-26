<%@ page language="java" contentType="text/html; charset=UTF8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>Please, enter registration data</h3>

	<form action="../registration" method="post">
		<input type="hidden" name="command" value="save_new_user">
		Введите логин <input type="text" name="login" value="" /><br />
		Введите пароль <input type="text" name="password" value="" /><br />
		Повторите пароль <input type="text" name="password_repeat" value="" /><br />
		Введите фамилию <input type="text" name="surname" value="" /><br />
		Введите имя <input type="text" name="name" value="" /><br /> Введите
		идентификационный номер паспорта <input type="text"
			name="passport_Id_Number" value="" /><br /> Введите номер
		водительского удостоверения <input type="text" name="driver_license"
			value="" /><br /> Введите год рождения <input type="text"
			name="date_of_birth" value="" /><br /> Введите e-mail <input
			type="text" name="e_mail" value="" /><br /> Введите номер телефона
		<input type="text" name="phone" value="" /><br /> <br /> <input
			type="submit" value="Зарегистрироваться"><br />
	</form>
	<br />


</body>
</html>