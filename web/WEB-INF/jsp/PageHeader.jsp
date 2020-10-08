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

    <fmt:message bundle="${loc}" key="local.showUserRegData.button" var="show_user_button"/>
    <fmt:message bundle="${loc}" key="local.editUserData.button" var="edit_user_button"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style_headerPage.css"/>

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

    <form action="mainController" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="local" value="ru"/>
        <input type="image" src="${pageContext.request.contextPath}/images/rus_ikon.jpg" height="17" align="top"
               alt="Rus">
    </form>

    <form action="mainController" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="local" value="be"/>
        <p><input type="image" src="${pageContext.request.contextPath}/images/be_icon1.jpg" height="17" align="top"
                  alt="Bel"></p>
    </form>

    <form action="mainController" method="post">
        <input type="hidden" name="command" value="change_locale"/>
        <input type="hidden" name="local" value="en"/>
        <p><input type="image" src="${pageContext.request.contextPath}/images/en_icon.png" height="17" align="top"
                  alt="EN"></p>
    </form>

    <c:choose>
        <c:when test="${not empty sessionScope.currentUserLogin}">
            <a href="">${currentUserLogin}</a>
        </c:when>
        <c:otherwise>
            <a href="mainController?command=authentication">${sign_in}</a>
        </c:otherwise>
    </c:choose>

    <c:if test="${sessionScope.currentUserRole == 1}">
        <a href="mainController?command=show_user_reg_data">${show_user_button}</a>
        <a href="mainController?command=go_to_edit_user_data_page">${edit_user_button}</a>

    </c:if>
    <c:if test="${sessionScope.currentUserRole == 2}">
        <a href="mainController?command=show_user_reg_data">${show_user_button}</a>
        <a href="mainController?command=go_to_edit_user_data_page">${edit_user_button}</a>
        <a href="">Drop user</a>
        <a href="mainController?command=get_all_users">Show all users</a>
        <a href="mainController?command=go_to_find_user_page">Find user</a>
    </c:if>

    <a href="mainController?command=sign_out">${sign_out} </a>

</links>

</body>
</html>
