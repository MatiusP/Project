<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="myCustomTag" uri="/WEB-INF/tld" %>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/carPage.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/rent_car_style.css"/>

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
<fmt:message bundle="${loc}" key="local.carPage.endRent.rentCar.main.message" var="rent_main_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.invalidPeriod.message" var="invalid_period_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.main.message" var="accept_order_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.model.message" var="car_model_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.renPeriod.message" var="rent_period_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.renPeriodLength.message" var="rent_length_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.price.message" var="total_price_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.acceptOrder.message" var="accept_button"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.selectCar.back.button" var="back_button"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.carNotAvailable.message" var="car_not_avail_message"/>
<fmt:message bundle="${loc}" key="local.carPage.endRent.createOrder.create.message" var="create_order_message"/>

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


<form id="createOrderForm" action="mainController" method="post">
    <input type="hidden" name="command" value="create_order"/>
</form>


<div class="rent">

    <c:choose>
        <c:when test="${sessionScope.added_result_ok != null}">
            <h2>${create_order_message}</h2>
            ${sessionScope.remove('added_result_ok')}
        </c:when>
        <c:otherwise>
            <form action="mainController" method="post">
                <input type="hidden" name="command" value="check_order_data">
                <input type="hidden" name="current_car" value="${car[0].id}"/>
                <input type="hidden" name="current_user" value="${currentUserLogin}"/>

                <c:choose>
                    <c:when test="${requestScope.orderForAccept != null}">
                        <div class="rent">
                            <h2>${accept_order_message}</h2>
                            <p>${car_model_message} <b>${orderForAccept.carBrand} ${orderForAccept.carModel}</b></p>
                            <p>${rent_period_message} <b><myCustomTag:dateFormatTag date="${orderForAccept.startRent}"/>
                                - <myCustomTag:dateFormatTag date="${orderForAccept.endRent}"/></b></p>
                            <p>${rent_length_message} <b>${orderForAccept.rentPeriodLength}</b></p>
                            <p>${total_price_message} <b>${orderForAccept.totalPrice}</b></p>
                        </div>
                        <button><a href="mainController?command=go_to_car_page&carId=${currentCarId}">${back_button}</a>
                        </button>

                        <input form="createOrderForm" type="hidden" name="startRentByOrder"
                               value="<myCustomTag:dateFormatFromUITag date="${orderForAccept.startRent}"/>"/>
                        <input form="createOrderForm" type="hidden" name="endRentByOrder"
                               value="<myCustomTag:dateFormatFromUITag date="${orderForAccept.endRent}"/>"/>
                        <input form="createOrderForm" type="hidden" name="totalPrice"
                               value="${orderForAccept.totalPrice}">
                        <input form="createOrderForm" type="hidden" name="user_id" value="${userRegData.id}">
                        <input form="createOrderForm" type="hidden" name="car_id" value="${currentCarId}">
                        <input form="createOrderForm" type="submit" value="${accept_button}">
                    </c:when>

                    <c:otherwise>
                        <h1>${rent_main_message}</h1>
                        <div class="rent">
                            <p>${start_rent_message}
                                <input type="date" name="start_rent" value="" required><br/></p>
                            <p>${end_rent_message}
                                <input type="date" name="end_rent" value="" required><br/></p>
                            <div class="rent_error_message">
                                <c:if test="${sessionScope.invalidPeriod != null}">
                                    <h3>${invalid_period_message}</h3>
                                    ${sessionScope.remove('invalidPeriod')}
                                </c:if>
                            </div>

                            <div class="rent_error_message">
                                <c:if test="${sessionScope.notLogin != null}">
                                    <h3>${auth_messasge} <a href="mainController?command=authentication">${sign_in}</a>
                                    </h3>
                                    ${sessionScope.remove('notLogin')}
                                </c:if>
                            </div>

                            <div class="rent_error_message">
                                <c:if test="${sessionScope.carNotAvailable != null}">
                                    <h3>${car_not_avail_message}</h3>
                                    ${sessionScope.remove('carNotAvailable')}
                                </c:if>
                            </div>

                            <button><a href="mainController?command=go_to_our_cars_page">${another_car_button}</a>
                            </button>
                            <input type="submit" value="${rent_button}">
                        </div>
                    </c:otherwise>
                </c:choose>
            </form>
        </c:otherwise>
    </c:choose>
</div>




