<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Final rent act</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.finalAct.main.message" var="main_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costOverduePeriod.message" var="overdue_period_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costFuel.message" var="cost_fuel_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costMileage.message" var="cost_mileage_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costParking.message" var="cost_parking_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costPolice.message" var="cost_police_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costDamage.message" var="cost_damage_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.costOtherPenalty.message" var="cost_other_penalty_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.orderId.message" var="order_id_message"/>
<fmt:message bundle="${loc}" key="local.finalAct.closeOrder.button" var="close_order_button"/>
<fmt:message bundle="${loc}" key="local.finalAct.back.button" var="back_button"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/finalRentAct.css"/>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

<form action="mainController" method="post" class="final_act_form">
    <input type="hidden" name="command" value="close_order"/>
    <input type="hidden" name="orderId" value="${finalRentAct.orderId}"/>
    <input type="hidden" name="finalActId" value="${finalRentAct.id}"/>

    <h3>${main_message}</h3>

    <div class="form_field">
        <label>${overdue_period_message}</label>
        <input type="number" step="0.01" name="overduePeriod" value="${finalRentAct.costOverduePeriod}" required/>
    </div>

    <div class="form_field">
        <label>${cost_fuel_message}</label>
        <input type="number" step="0.01" name="fuelCost" value="${finalRentAct.costByFuel}" required/>
    </div>

    <div class="form_field">
        <label>${cost_mileage_message}</label>
        <input type="number" step="0.01" name="mileage" value="${finalRentAct.costByMileage}" required/>
    </div>

    <div class="form_field">
        <label>${cost_parking_message}</label>
        <input type="number" step="0.01" name="parking" value="${finalRentAct.costByParkingPenalty}" required/>
    </div>

    <div class="form_field">
        <label>${cost_police_message}</label>
        <input type="number" step="0.01" name="police" value="${finalRentAct.costByPolicePenalty}" required/>
    </div>

    <div class="form_field">
        <label>${cost_damage_message}</label>
        <input type="number" step="0.01" name="damage" value="${finalRentAct.costByDamagePenalty}" required/>
    </div>

    <div class="form_field">
        <label>${cost_other_penalty_message}</label>
        <input type="number" step="0.01" name="other" value="${finalRentAct.costByOtherPenalty}" required/>
    </div>
    <div>
        <input type="submit" value="${close_order_button}">
    </div>

    <div>
        <a href="mainController?command=get_all_orders">${back_button}</a>
    </div>
</form>


</body>
</html>
