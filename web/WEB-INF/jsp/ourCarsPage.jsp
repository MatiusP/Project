<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>MotorRENT - Our cars</title>
</head>

<jsp:include page="headerPage.jsp"/>
<jsp:include page="cars.jsp"/>


<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>


<body>


<c:forEach items="${cars}" var="car">
<a href="mainController?command=go_to_car_page&car_id=${car.id}">
    <img width="250" src="${pageContext.request.contextPath}/images/car/page_font.jpg" alt="carImage">


    <table>
        <tr>
            <td>${car.brand}</td>
            <td>${car.model}</td>
            <td>${car.manufactureDate}</td>
            <td>${car.transmissionType}</td>
            <td>${car.classType}</td>
        </tr>
    </table>


    </c:forEach>


</body>
</html>
