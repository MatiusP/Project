<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/carPage_style.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ourCars_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<script>
    function changeImage(imageSrc) {
        document.getElementById("current_image").src = imageSrc;
    }
</script>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.carPage.enginePower.text" var="engine_power"/>
<fmt:message bundle="${loc}" key="local.carPage.fuelConsumption.text" var="fuel_consumption"/>
<fmt:message bundle="${loc}" key="local.carPage.transmission.text" var="transmission"/>
<fmt:message bundle="${loc}" key="local.carPage.pricePeriod.text" var="price_period"/>
<fmt:message bundle="${loc}" key="local.carPage.rent.button.text" var="rent_button"/>
<fmt:message bundle="${loc}" key="local.carPage.anotherCar.button.text" var="another_car_button"/>
<fmt:message bundle="${loc}" key="local.carPage.authorization.message" var="auth_messasge"/>
<fmt:message bundle="${loc}" key="local.message.signIn" var="sign_in"/>
<fmt:message bundle="${loc}" key="local.carPage.startRent.message" var="start_rent_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.message" var="end_rent_message"/>


<c:forEach items="${car}" var="current_car">
    <div class="inst">
        <div class="main_img">
            <img id="current_image" src="${pageContext.request.contextPath}${current_car.photos[0]}" width="450"
                 height="350">
            <div>
                <c:forEach items="${current_car.photos}" var="photo">
                    <a href="javascript:changeImage('${pageContext.request.contextPath}${photo}')">
                        <img class="secondary_img" src="${pageContext.request.contextPath}${photo}" width="90px">
                    </a>
                </c:forEach>
            </div>
        </div>
        <h1>${current_car.brand} ${current_car.model} ${current_car.manufactureDate}</h1>
        <p>${engine_power} ${current_car.enginePower}</p>
        <p>${fuel_consumption} ${current_car.fuelConsumption}</p>
        <p>${transmission} ${current_car.transmissionType}</p>
        <h3>${price_period} ${current_car.pricePerDay} BYN</h3>
    </div>
</c:forEach>

<div class="tab">
<button class="tablinks"  name="rent_car" value="rent">
    <a href="mainController?command=go_to_our_cars_page"/>${another_car_button}
</button>
</div>



<div class="tab">
<%--    <c:choose>--%>
<%--        <c:when test="${not empty sessionScope.currentUserLogin}">--%>

            <form id="create_order" action="mainController" method="post">
                <input type="hidden" name="command" value="rent_car">

                <p>${start_rent_message}
                    <input type="date" name="start_rent" value="" required>
                </p>

                <p>${end_rent_message}
                <input type="date" name="end_rent" value="" required>
                </p>


            </form>


            <button class="tablinks" name="rent_car" value="rent">
                <a href="mainController?command=create_order"/>${rent_button}
            </button>

<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <div class="inst">--%>
<%--                <h2>${auth_messasge}--%>
<%--                    <a href="mainController?command=authentication">${sign_in}</a>--%>
<%--                </h2>--%>
<%--            </div>--%>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
</div>


