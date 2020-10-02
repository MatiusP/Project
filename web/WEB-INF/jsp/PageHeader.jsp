<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title></title>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="property/localisation" var="loc"/>
    <fmt:message bundle="${loc}" key="local.button.be" var="be_button"/>
    <fmt:message bundle="${loc}" key="local.button.ru" var="ru_button"/>
    <fmt:message bundle="${loc}" key="local.button.en" var="en_button"/>
    <fmt:message bundle="${loc}" key="local.message.signIn" var="sign_in"/>
    <fmt:message bundle="${loc}" key="local.message.mainPage" var="message"/>

    <fmt:message bundle="${loc}" key="local.logoText.message" var="logo_text"/>
    <fmt:message bundle="${loc}" key="local.sloganText.message" var="slogan_text"/>
    <fmt:message bundle="${loc}" key="local.home.href" var="home_button"/>
    <fmt:message bundle="${loc}" key="local.ourCars.href" var="our_cars_button"/>
    <fmt:message bundle="${loc}" key="local.outConditions.href" var="our_conditions_button"/>
    <fmt:message bundle="${loc}" key="local.contacts.href" var="our_contacts_button"/>
    <fmt:message bundle="${loc}" key="local.signOut.href" var="sign_out"/>

    <link rel="stylesheet" href="css/style_headerPage.css">
</head>
<body>

<header>
    <logo>MotorRENT</logo>
    <logo_text>${logo_text}</logo_text>
    <slogan_text>${slogan_text}</slogan_text>
</header>


<links>
    <a href="mainPage">${home_button}</a>
    <a href="carsPage">${our_cars_button}</a>
    <a href="rentalConditionsPage">${our_conditions_button}</a>
    <a href="contactPage">${our_contacts_button}</a>
    <a href="userController?command=change_locale&&local=ru">Key RUS</a>
    <a href="userController?command=change_locale&&local=be">Key BE</a>
    <a href="userController?command=change_locale&&local=en">Key EN</a>
    <a href="userController?command=authentication">${sign_in}</a>
    <a href="contactPage">${sign_out}</a><br/>
</links>

<img src="images/startPage.jpeg" alt="photo"/>


<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="be">
    <input type="submit" value="${be_button}">
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="ru">
    <input type="submit" value="${ru_button}">
</form>

<form action="userController" method="post">
    <input type="hidden" name="command" value="change_locale"/>
    <input type="hidden" name="local" value="en">
    <input type="submit" value="${en_button}">
</form>

<h2><c:out value="${message}"/></h2>

</body>
</html>
