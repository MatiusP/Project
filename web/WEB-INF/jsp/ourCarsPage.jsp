<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>MotorRENT - Our cars</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.allCar.button" var="all_car_button"/>
<fmt:message bundle="${loc}" key="local.economClassCar.button" var="econom_car_class_button"/>
<fmt:message bundle="${loc}" key="local.middleClassCar.button" var="middle_car_class_button"/>
<fmt:message bundle="${loc}" key="local.premiumClassCar.button" var="premium_car_class_button"/>
<fmt:message bundle="${loc}" key="local.transmissionType.message" var="transmission"/>
<fmt:message bundle="${loc}" key="local.engineType.message" var="engine"/>
<fmt:message bundle="${loc}" key="local.fuelConsumption.message" var="fuel_consumption"/>
<fmt:message bundle="${loc}" key="local.carClass.message" var="car_class"/>


<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ourCars_style.css"/>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
        width: 100%;
        height: 100%;
    }
</style>

<body>

<div class="tab">
    <button class="tablinks"><a href="mainController?command=get_cars&class="/>${all_car_button}</button>
    <button class="tablinks"><a href="mainController?command=get_cars&class=economy"/>${econom_car_class_button}
    </button>
    <button class="tablinks"><a href="mainController?command=get_cars&class=middle"/>${middle_car_class_button}
    </button>
    <button class="tablinks"><a href="mainController?command=get_cars&class=premium"/>${premium_car_class_button}
    </button>
    <button class="tablinks"><a href="mainController?command=go_to_add_car_page"/>ADD CAR
    </button>
</div>

<c:if test="${not empty cars}">
    <c:forEach items="${cars}" var="car">
        <figure class="sign">
            <p>
            <div class="link">
                <a href="mainController?command=go_to_car_page&carId=${car.id}"/>
                <figcaption>${car.brand} ${car.model} (${car.manufactureDate} г.в.)</figcaption>
                <img src="${pageContext.request.contextPath}${car.photos[0]}" width="180" height="auto" alt="Photo">
            </div>

            <figcaption>
                <div class="link">${car.pricePerDay} BYN</div>

                <div class="car-characteristics">
                    <p>${transmission} ${car.transmissionType}</p>
                    <p>${engine} ${car.enginePower} (л.с.)</p>
                    <p>${fuel_consumption} ${car.fuelConsumption} (л/100)</p>
                    <p>${car_class} ${car.classType}</p>
                </div>
            </figcaption>
            </p>
        </figure>
    </c:forEach>
</c:if>
</body>
</html>
