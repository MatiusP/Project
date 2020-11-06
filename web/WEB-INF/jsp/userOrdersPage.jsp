<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>User orders</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ordersPage_style.css"/>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>
<script type="text/javascript">
    <%@include file="../../js/sortTable.js"%>
</script>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.userOrderPage.main.message" var="main_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.noOrders.message" var="no_orders_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.orderId.message" var="order_number_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.orderDate.message" var="order_date_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.carBrand.message" var="car_brand_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.carModel.message" var="car_model_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.startRent.message" var="start_rent_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.endRent.message" var="end_rent_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.price.message" var="price_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.status.message" var="status_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.notAccepted.message" var="not_acc_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.accepted.message" var="acc_message"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.cancelOrder.button" var="cancel_button"/>
<fmt:message bundle="${loc}" key="local.userOrderPage.back.button" var="back_button"/>

<body>
<form action="mainController" method="post">
    <input type="hidden" name="command" value="">

    <h1>${main_message}</h1>

    <c:choose>
        <c:when test="${requestScope.noEnyOrders != null}">
            <div class="info-message">
                <h3><c:out value="${no_orders_message}"/></h3>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty userOrders}">
                <table class="table_sort" align="center">
                    <thead>
                    <tr>
                        <th>${order_number_message}</th>
                        <th>${order_date_message}</th>
                        <th>${car_brand_message}</th>
                        <th>${car_model_message}</th>
                        <th>${start_rent_message}</th>
                        <th>${end_rent_message}</th>
                        <th>${price_message}</th>
                        <th>${status_message}</th>
                    </tr>
                    </thead>
                    <c:forEach items="${userOrders}" var="order">
                        <fmt:formatDate pattern="dd-MM-yyyy"
                                        value="${order.orderStartRent}"
                                        var="formattedStartRent"/>
                        <fmt:formatDate pattern="dd-MM-yyyy"
                                        value="${order.orderEndRent}"
                                        var="formattedEndRent"/>
                        <fmt:parseDate value="${order.orderDate}"
                                       pattern="yyyy-MM-dd'T'HH:mm"
                                       var="parsedDateTime" type="both"/>
                        <fmt:formatDate pattern="dd.MM.yyyy HH:mm"
                                        value="${parsedDateTime}"
                                        var="formattedOrderDate"/>
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${formattedOrderDate}</td>
                            <td>${order.orderCarBrand}</td>
                            <td>${order.orderCarModel}</td>
                            <td>${formattedStartRent}</td>
                            <td>${formattedEndRent}</td>
                            <td>${order.orderTotalPrice}</td>
                            <td>
                                <c:choose>
                                <c:when test="${order.isOrderAccepted eq 'Accepted'}">
                                    ${acc_message}
                                </c:when>
                                <c:otherwise>
                                    ${not_acc_message}
                                </c:otherwise>
                                </c:choose>
                            <td>
                            <button class="table_sort">
                                <a href=" mainController?command=go_to_edit_car_page&id=${car.id}"/>${cancel_button}
                            </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:otherwise>
    </c:choose>
</form>
<form id="exit" action="mainController?command=go_to_main_page" method="post">
    <button form="exit" class="table_sort" type="submit">${back_button}</button>
</form>


</body>
</html>
