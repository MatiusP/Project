<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title></title>
    <fmt:setLocale value="${sessionScope.local}"/>
    <fmt:setBundle basename="property/localisation" var="loc"/>

    <fmt:message bundle="${loc}" key="local.message.signIn" var="sign_in"/>
    <fmt:message bundle="${loc}" key="local.sloganText.message" var="slogan_text"/>
    <fmt:message bundle="${loc}" key="local.home.href" var="home_button"/>
    <fmt:message bundle="${loc}" key="local.ourCars.href" var="our_cars_button"/>
    <fmt:message bundle="${loc}" key="local.outConditions.href" var="our_conditions_button"/>
    <fmt:message bundle="${loc}" key="local.contacts.href" var="our_contacts_button"/>
    <fmt:message bundle="${loc}" key="local.signOut.href" var="sign_out"/>
    <fmt:message bundle="${loc}" key="local.showUserRegData.button" var="show_user_button"/>
    <fmt:message bundle="${loc}" key="local.editUserData.button" var="edit_user_button"/>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/headerPage_style.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu_style.css"/>

</head>

<body>
<div class="header">
    <div class="header_section">
        <div class="header_logo">MotorRENT</div>
        <div class="header_logotext">Best service to rent a car!</div>
    </div>
    <div class="header_section">
        <div class="header_slogan">${slogan_text}</div>
    </div>
</div>
<div class="header_nav">
    <div class=" header_item header_button">
        <a href="mainPage">${home_button}</a>
    </div>
    <div class="header_item header_button">
        <a href="carsPage">${our_cars_button}</a>
    </div>
    <div class="header_item header_button">
        <a href="rentalConditionsPage">${our_conditions_button}</a>
    </div>
    <div class="header_item header_button">
        <a href="contactPage">${our_contacts_button}</a>
    </div>
    <div>
        <form action="mainController" method="post">
            <input type="hidden" name="command" value="change_locale"/>
            <input type="hidden" name="local" value="ru"/>
            <input type="image" src="${pageContext.request.contextPath}/images/RU.png" height="25" alt="RU">
        </form>
    </div>
    <div>
        <form action="mainController" method="post">
            <input type="hidden" name="command" value="change_locale"/>
            <input type="hidden" name="local" value="be"/>
            <input type="image" src="${pageContext.request.contextPath}/images/BY.png" height="25" alt="BY">
        </form>
    </div>
    <div>
        <form action="mainController" method="post">
            <input type="hidden" name="command" value="change_locale"/>
            <input type="hidden" name="local" value="en"/>
            <input type="image" src="${pageContext.request.contextPath}/images/GB.png" height="25" alt="EN">
        </form>
    </div>
    <div>
        <c:choose>
            <c:when test="${not empty sessionScope.currentUserLogin}">
                <div>
                    <nav>
                        <ul class="topmenu">
                            <li><a href="" class="submenu-link">${sessionScope.currentUserLogin}</a>
                                <c:if test="${sessionScope.currentUserRole == 1}">
                                    <ul class="submenu">
                                        <li><a href="mainController?command=show_user_reg_data">${show_user_button}</a>
                                        </li>
                                        <li>
                                            <a href="mainController?command=go_to_edit_user_data_page">${edit_user_button}</a>
                                        </li>
                                        <li><a href="">My orders</a></li>
                                    </ul>
                                </c:if>
                                <c:if test="${sessionScope.currentUserRole == 2}">
                                    <ul class="submenu">
                                        <li><a href="mainController?command=show_user_reg_data">${show_user_button}</a>
                                        </li>
                                        <li>
                                            <a href="mainController?command=go_to_edit_user_data_page">${edit_user_button}</a>
                                        </li>
                                        <li><a href="">Drop user</a></li>
                                        <li><a href="mainController?command=get_all_users">Show all users</a></li>
                                        <li><a href="mainController?command=go_to_find_user_page">Find user</a></li>
                                    </ul>
                                </c:if>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:when>
            <c:otherwise>
                <div class="header_item header_button">
                    <a href="mainController?command=authentication">${sign_in}</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="header_item header_button">
        <a href="mainController?command=sign_out">${sign_out}</a>
    </div>
</div>
<main>


    <%-- <img src="${pageContext.request.contextPath}/images/StartPage.jpg"
         width="100%" height="auto"> --%>
</main>
</body>
</html>
