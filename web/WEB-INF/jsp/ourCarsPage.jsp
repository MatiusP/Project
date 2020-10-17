<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>MotorRENT - Our cars</title>
</head>

<jsp:include page="headerPage.jsp"/>
<jsp:include page="cars.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ourCars_style.css"/>
<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>

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
                <p>Тип КПП: ${car.transmissionType}</p>
                <p>Двигатель: ${car.enginePower} (л.с.)</p>
                <p>Класс: ${car.classType}"</p>
            </div>
        </figcaption>
        </p>
    </figure>
</c:forEach>

</body>
</html>
