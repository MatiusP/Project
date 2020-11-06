<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>
    <title>Order management</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orderManagementPage_style.css"/>

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

<fmt:message bundle="${loc}" key="local.AllOrdersPage.main.message" var="main_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.noOrders.message" var="no_orders_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.orderId.message" var="order_id_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.orderDate.message" var="order_date_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.startRent.message" var="start_rent_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.endRent.message" var="end_rent_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.price.message" var="price_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.status.message" var="status_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.isCanceled.message" var="is_canceled_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.isClosed.message" var="is_closed_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.userId.message" var="user_id_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.userPassport.message" var="user_passport_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.carId.message" var="car_id_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.carBrand.message" var="brand_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.carModel.message" var="model_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.notAccepted.message" var="not_acc_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.accepted.message" var="acc_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.notCanceled.message" var="not_cancel_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.canceled.message" var="cancel_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.notClosed.message" var="not_close_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.closed.message" var="close_message"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.getOrders.button" var="get_orders_button"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.accept.button" var="accept_button"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.cancel.button" var="cancel_button"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.close.button" var="close_button"/>
<fmt:message bundle="${loc}" key="local.AllOrdersPage.exit.button" var="exit_button"/>

<body>


<form id="get_orders" action="mainController" method="post">
    <input type="hidden" name="command" value="get_all_orders"/>
</form>

<div class="management-button">
    <button form="get_orders" type="submit">${get_orders_button}</button>
    <button><a href="mainController?command=go_to_main_page">${exit_button}</a></button>
</div>

<form action="mainController" method="post">
    <input type="hidden" name="command" value="">
    <c:choose>
        <c:when test="${sessionScope.noAnyOrders != null}">
            <div class="info-message">
                <h3><c:out value="${noAnyOrders}"/></h3>
            </div>
        </c:when>
        <c:otherwise>
            <c:if test="${not empty orderList}">
                <h1>${main_message}</h1>

                <table class="table_sort" align="center">
                    <thead>
                    <tr>
                        <th>${order_id_message}</th>
                        <th>${order_date_message}</th>
                        <th>${start_rent_message}</th>
                        <th>${end_rent_message}</th>
                        <th>${price_message}</th>
                        <th>${status_message}</th>
                        <th>${is_canceled_message}</th>
                        <th>${is_closed_message}</th>
                        <th>${user_id_message}</th>
                        <th>${user_passport_message}</th>
                        <th>${car_id_message}</th>
                        <th>${brand_message}</th>
                        <th>${model_message}</th>
                    </tr>
                    </thead>
                    <c:forEach items="${orderList}" var="order">
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
                            <td>${formattedStartRent}</td>
                            <td>${formattedEndRent}</td>
                            <td>${order.orderTotalPrice}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.isOrderAccepted eq 'Accepted'}">
                                        ${acc_message}"
                                    </c:when>
                                    <c:otherwise>
                                        <font color="red">${not_acc_message}</font>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.isOrderCanceled eq 'Canceled'}">
                                        ${cancel_message}
                                    </c:when>
                                    <c:otherwise>
                                        ${not_cancel_message}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${order.isOrderClosed eq 'Closed'}">
                                        ${close_message}
                                    </c:when>
                                    <c:otherwise>
                                        ${not_close_message}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>${order.orderUserId}</td>
                            <td>${order.orderUserPassport}</td>
                            <td>${order.orderCarId}</td>
                            <td>${order.orderCarBrand}</td>
                            <td>${order.orderCarModel}</td>
                            <td>
                                <button class="order_manager">
                                    <a href=" mainController?command=accept_order&id=${order.orderId}"/>${accept_button}
                                </button>
                            </td>
                            <td>
                                <button class="order_manager">
                                    <a href=" mainController?command=cancel_order&id=${order.orderId}"/>${cancel_button}
                                </button>
                            </td>
                            <td>
                                <button class="order_manager">
                                    <a href=" mainController?command=close_order&id=${order.orderId}"/>${close_button}
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:if>
        </c:otherwise>
    </c:choose>
</form>
</body>
</html>
