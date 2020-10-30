<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
    <title>Add car</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.addCarPage.page.message" var="page_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.brand.message" var="brand_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.classType.message" var="class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.transmission.message" var="transm_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.model.message" var="model_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.VIN.message" var="vin_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.manufDate.message" var="manuf_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.enginePower.message" var="eng_power_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.fuelConsumption.message" var="fuel_cons_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.price.message" var="price_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.isAvailable.message" var="is_available_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.photos.message" var="photos_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.select.message" var="select_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.economyClass.message" var="econom_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.middleClass.message" var="middle_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.premiumClass.message" var="premium_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.manualTransm.message" var="manual_transm_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.automatTransm.message" var="automat_transm_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.available.message" var="available_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.notAvailable.message" var="not_available_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.add.button" var="add_button"/>
<fmt:message bundle="${loc}" key="local.addCarPage.back.button" var="back_button"/>
<fmt:message bundle="${loc}" key="local.addCarPage.brandValidRules.message" var="brand_valid_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.VINValidRules.message" var="vin_valid_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.manDateValidRules.message" var="man_date_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.engPowValidRules.message" var="eng_pow_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.fuelConsValidRules.message" var="fu_cons_rules"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addCarForm_style.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>


<body>

<form action="mainController" method="post" class="add_car_form">
    <input type="hidden" name="command" value="add_car"/>

    <h3>${page_message}</h3>

    <div class="form__field">
        <label>${brand_message}</label>
        <input type="text" name="carBrand" placeholder="${brand_message}"
               pattern="^[A-Za-zА-ЯЁа-яё]*$"
               required/>
        <span class="form__error">${brand_valid_rules}</span>
    </div>

    <div class="form__field">
        <label>${model_message}</label>
        <input type="text" name="carModel" placeholder="${model_message}" required/>
    </div>

    <div class="form__field">
        <label>${class_message}</label>
        <select name="carClassType" required>
            <option selected disabled>${select_message}</option>
            <option value="Economy">${econom_class_message}</option>
            <option value="Middle">${middle_class_message}</option>
            <option value="Premium">${premium_class_message}</option>
        </select>
    </div>

    <div class="form__field">
        <label>${transm_message}</label>
        <select name="transmissionType" required>
            <option selected disabled>${select_message}</option>
            <option value="Manual">${manual_transm_message}</option>
            <option value="Automatic">${automat_transm_message}</option>
        </select>
    </div>

    <div class="form__field">
        <label>${vin_message}</label>
        <input type="text" name="carVIN" placeholder="${vin_message}"
               pattern="^[a-zA-Z0-9]{11}[0-9]{6}$"
               required/>
        <span class="form__error">${vin_valid_rules}</span>
    </div>

    <div class="form__field">
        <label>${manuf_message}</label>
        <input type="text" name="manufactureDate" placeholder="${manuf_message}"
               pattern="^(([1][9][4-9]\\d)|([2][0][0-2][0-1]))$"
               required/>
        <span class="form__error">${man_date_rules}</span>
    </div>

    <div class="form__field">
        <label>${eng_power_message}</label>
        <input type="text" name="enginePower" placeholder="${eng_power_message}"
               pattern="^[0-9]{0,5}$"
               required/>
        <span class="form__error">${eng_pow_rules}</span>
    </div>

    <div class="form__field">
        <label>${fuel_cons_message}</label>
        <input type="text" name="fuelConsumption" placeholder="${fuel_cons_message}"
               pattern="^[0-9]{0,2}$"
               required/>
        <span class="form__error">${fu_cons_rules}</span>
    </div>

    <div class="form__field">
        <label>${price_message}</label>
        <input type="number" name="pricePerDay" placeholder="${price_message}" required/>
    </div>

    <div class="form__field">
        <label>${is_available_message}</label>
        <select name="availableToRent" required>
            <option selected disabled>${select_message}</option>
            <option value="Available">${available_message}</option>
            <option value="Not_available">${not_available_message}</option>
        </select>
    </div>

    <div class="form__field">
        <label>${photos_message}</label>
        <input type="file" name="photo" multiple accept="image/jpeg,image/png"/>
    </div>

    <div class="edit_profile-message">
        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if>
    </div>

    <div>
        <input type="submit" value="ADD">
    </div>

    <div>
        <a href="mainController?command=go_to_user_management_page">${back_button}</a>
    </div>
</form>


</body>
</html>
