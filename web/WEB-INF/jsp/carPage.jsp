<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="headerPage.jsp"/>


<h1>CAR PAGE</h1>



<c:forEach items="${car}" var="current_car">
    <c:out value="${current_car.id}"/>
</c:forEach>




<div id="car-images" class="rowing-left">
    <div id="main-car-image">
        <img id="main_car_img" src="${pageContext.request.contextPath}${requestScope.car_images[0]}">
    </div>
    <c:forEach items="${requestScope.car_images}" var="car_image">
        <a href="javascript:changeImage('${pageContext.request.contextPath}${car_image}')">
            <img class="small-car-img" src="${pageContext.request.contextPath}${car_image}">
        </a>
    </c:forEach>
</div>
<div id="car-information" class="rowing-left">
    <h1>${requestScope.car.brand} ${requestScope.car.model} ${requestScope.car.yearProduction}</h1>
    <div id="car-details">
        <div class="car-info">
            <h3>${description}:</h3>
            <p>${requestScope.car.comment}</p>
        </div>
        <div class="car-info">
            <h3>${features}:</h3>
            <ul class="car-features">
                <li>${transmission_title}: <fmt:message key="car.transmission.${requestScope.car.transmission}"/></li>
                <li>${engine_size}: ${requestScope.car.engineSize} ${engine_size_unit}</li>
                <li>${fuel_type}: <fmt:message key="car.fuel_type.${requestScope.car.fuelType}"/></li>
            </ul>
        </div>
    </div>

    <p id="car-price">
        <strong>
            <fmt:formatNumber minFractionDigits="2" value="${requestScope.car.pricePerDay}"/>
        </strong> ${price_per_day}</p>

    <p>${nearest_available_date}: 2020-10-16</p>
    <div id="order-form">
        <form action="someAction" method="post">
            <div class="rowing-left">
                <p>${pick_up_date}</p>
                <input type="date">
            </div>
            <div class="rowing-left">
                <p>${drop_off_date}</p>
                <input type="date">
            </div>
            <div>
                <button type="submit">${to_rent}</button>
            </div>
        </form>
    </div>
</div>
