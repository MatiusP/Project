<%@ page language="java" contentType="text/html; charset=UTF8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
</head>
<body>

<h2>Please, enter registration data</h2>

<form action="userController" method="post">
    <input type="hidden" name="command" value="save_new_user">
    Введите логин <input type="text" name="login" placeholder="login"/><br/>
    Введите пароль <input type="text" name="password" placeholder="password"/><br/>
    Повторите пароль <input type="text" name="password_repeat" placeholder="repeat password"/><br/>
    Введите фамилию <input type="text" name="surname" placeholder="surname"/><br/>
    Введите имя <input type="text" name="name" placeholder="name"/><br/> Введите
    идентификационный номер паспорта <input type="text"
                                            name="passport_Id_Number" placeholder="passport ID number"/><br/> Введите
    номер
    водительского удостоверения <input type="text" name="driver_license"
                                       placeholder="driver license number"/><br/> Введите год рождения <input
        type="text"
        name="date_of_birth" placeholder="yyyy-mm-dd"/><br/>
    Введите e-mail <input
        type="text" name="e_mail" placeholder="e-mail"/><br/> Введите номер телефона
    <input type="text" name="phone" placeholder="+xxx xx xxx xx xx"/><br/> <br/> <input
        type="submit" value="Зарегистрироваться"><br/>
</form>
<br/>


</body>
</html>