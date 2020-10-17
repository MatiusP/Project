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
    }
</style>

<body>

<div class="tab">
    <button class="tablinks" form="allCars" type="submit"
            onclick="openCarClass(event, 'all_cars')">${all_car_button}</button>
    <button class="tablinks" form="economCars" type="submit"
            onclick="openCarClass(event, 'econom_cars')">${econom_car_class_button}
    </button>
    <button class="tablinks" form="middleCars" type="submit"
            onclick="openCarClass(event, 'middle_cars')">${middle_car_class_button}
    </button>
    <button class="tablinks" form="premiumCars" type="submit"
            onclick="openCarClass(event, 'premium_cars')">${premium_car_class_button}
    </button>
</div>


<div id="all_cars" class="tabcontent">
    <form id="allCars" action="mainController" method="post">
        <input type="hidden" name="command" value="get_cars"/>
        <input type="hidden" name="class" value=""/>
    </form>
</div>

<div id="econom_cars" class="tabcontent">
    <form id="economCars" action="mainController" method="post">
        <input type="hidden" name="command" value="get_cars"/>
        <input type="hidden" name="class" value="economy"/>
    </form>
</div>

<div id="middle_cars" class="tabcontent">
    <form id="middleCars" action="mainController" method="post">
        <input type="hidden" name="command" value="get_cars"/>
        <input type="hidden" name="class" value="middle"/>
    </form>

</div>

<div id="premium_cars" class="tabcontent">
    <form id="premiumCars" action="mainController" method="post">
        <input type="hidden" name="command" value="get_cars"/>
        <input type="hidden" name="class" value="premium"/>
    </form>

</div>

<c:forEach items="${cars}" var="car">
    <figure class="sign">
        <p>
        <div class="link">
            <figcaption>${car.brand} ${car.model} (${car.manufactureDate} г.в.)</figcaption>
        </div>
        <p>
            <img src="${pageContext.request.contextPath}/images/car/hyundai-solaris-01.jpg" width="175" alt="Photo">
        </p>

        <figcaption>
            <div class="link">
                <figcaption>PRICE</figcaption>
            </div>

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

</body>
</html>
