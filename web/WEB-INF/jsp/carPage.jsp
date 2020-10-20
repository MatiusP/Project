<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/carPage_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>
<fmt:message bundle="${loc}" key="local.carPage.enginePower.text" var="engine_power"/>
<fmt:message bundle="${loc}" key="local.carPage.fuelConsumption.text" var="fuel_consumption"/>
<fmt:message bundle="${loc}" key="local.carPage.transmission.text" var="transmission"/>
<fmt:message bundle="${loc}" key="local.carPage.pricePeriod.text" var="price_period"/>

<c:forEach items="${car}" var="current_car">

    <div class="inst">
        <div class="main_img">
            <img id="current_image" src="${pageContext.request.contextPath}${current_car.photos[0]}" width="450" height="350">
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

        <h3>${price_period}  ${current_car.pricePerDay} BYN</h3>
    </div>


    <form action="mainController" method="post">







    </form>


</c:forEach>

<script>
    function changeImage(imageSrc) {
        document.getElementById("current_image").src = imageSrc;
    }
</script>