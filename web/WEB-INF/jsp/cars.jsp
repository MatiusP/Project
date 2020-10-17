<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ourCars_style.css"/>

<script type="text/javascript">
    <%@ include file="../../js/ourCars.js"%>
</script>

<body>

<h1>HELLO FROM OUR CARS PAGE</h1>

<form id="allCars" action="mainController" method="post">
</form>

<div class="tab">
    <button class="tablinks" form="allCars" type="submit" name="command" value="get_all_cars">All cars</button>
    <button class="tablinks" form="allCars" type="submit" name="command" value="get_all_cars">Econom class</button>
    <button class="tablinks" onclick="openCarClass(event, 'middle_class_cars')">Middle class</button>
    <button class="tablinks" onclick="openCarClass(event, 'premium_class_cars')">Premium class</button>
</div>

<div id="middle_class_cars" class="tabcontent">
    <h3>London</h3>
    <p>London is the capital city of England.</p>
</div>


</body>
</html>
