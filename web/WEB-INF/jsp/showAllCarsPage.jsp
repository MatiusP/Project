<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title></title>
</head>
<body>

<c:choose>
    <c:when test="${not empty requestScope.No_eny_cars_found}">
        <h3><c:out value="${requestScope.No_eny_cars_found}"/></h3>
    </c:when>
    <c:otherwise>
        <table>
            <tr>
                <th>Brand</th>
                <th>Model</th>
                <th>Manufacture date</th>
                <th>Transmission</th>
                <th>Class</th>
            </tr>

            <c:forEach var="car" items="${cars}">
                <tr>
                    <td>${car.brand}</td>
                    <td>${car.model}</td>
                    <td>${car.manufactureDate}</td>
                    <td>${car.transmissionType}</td>
                    <td>${car.classType}</td>
                </tr>
            </c:forEach>

        </table>
    </c:otherwise>
</c:choose>

</body>
</html>
