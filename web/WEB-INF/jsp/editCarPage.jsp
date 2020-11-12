<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Edit car</title>
</head>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="property/localisation" var="loc"/>

<fmt:message bundle="${loc}" key="local.editCarPage.page.message" var="page_message"/>
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
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.message" var="car_status_message"/>
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.deleted.message" var="delete_status"/>
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.active.message" var="active_status"/>
<fmt:message bundle="${loc}" key="local.addCarPage.photos.message" var="photos_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.economyClass.message" var="econom_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.middleClass.message" var="middle_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.premiumClass.message" var="premium_class_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.manualTransm.message" var="manual_transm_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.automatTransm.message" var="automat_transm_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.available.message" var="available_message"/>
<fmt:message bundle="${loc}" key="local.addCarPage.notAvailable.message" var="not_available_message"/>
<fmt:message bundle="${loc}" key="local.editCarPage.edit.button" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.addCarPage.back.button" var="back_button"/>
<fmt:message bundle="${loc}" key="local.addCarPage.brandValidRules.message" var="brand_valid_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.VINValidRules.message" var="vin_valid_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.manDateValidRules.message" var="man_date_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.engPowValidRules.message" var="eng_pow_rules"/>
<fmt:message bundle="${loc}" key="local.addCarPage.fuelConsValidRules.message" var="fu_cons_rules"/>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addCarForm.css"/>

<style>
    body {
        background: url("${pageContext.request.contextPath}/images/page_font.jpg");
    }
</style>

<body>
<form action="mainController" method="post" class="add_car_form">
    <input type="hidden" name="command" value="edit_car">

    <h3>${page_message}</h3>

    <c:forEach items="${car}" var="carForEdit">
        <input type="hidden" name="carId" value="${carForEdit.id}">

        <div class="form_field">
            <label>${brand_message}</label>
            <input type="text" name="carBrand" value="${carForEdit.brand}"
                   pattern="^[A-Za-zА-ЯЁа-яё]*$"
                   required/>
            <span class="form_error">${brand_valid_rules}</span>
        </div>

        <div class="form_field">
            <label>${model_message}</label>
            <input type="text" name="carModel" value="${carForEdit.model}" required/>
        </div>

        <div class="form_field">
            <label>${class_message}</label>
            <select name="carClass" required>
                <c:if test="${carForEdit.classType eq 'ECONOMY'}">
                    <option selected value="Economy">${carForEdit.classType}</option>
                    <option value="Middle">${middle_class_message}</option>
                    <option value="Premium">${premium_class_message}</option>
                </c:if>
                <c:if test="${carForEdit.classType eq 'MIDDLE'}">
                    <option selected value="Middle">${middle_class_message}</option>
                    <option value="Economy">${econom_class_message}</option>
                    <option value="Premium">${premium_class_message}</option>
                </c:if>
                <c:if test="${carForEdit.classType eq 'PREMIUM'}">
                    <option selected value="Premium">${premium_class_message}</option>
                    <option value="Economy">${econom_class_message}</option>
                    <option value="Middle">${middle_class_message}</option>
                </c:if>
            </select>
        </div>

        <div class="form_field">
            <label>${transm_message}</label>
            <select name="carTransmission" required>
                <c:if test="${carForEdit.transmissionType eq 'MANUAL'}">
                    <option selected value="Manual">${manual_transm_message}</option>
                    <option value="Automatic">${automat_transm_message}</option>
                </c:if>
                <c:if test="${carForEdit.transmissionType eq 'AUTOMATIC'}">
                    <option selected value="Automatic">${automat_transm_message}</option>
                    <option value="Manual">${manual_transm_message}</option>
                </c:if>
            </select>
        </div>

        <div class="form_field">
            <label>${vin_message}</label>
            <input type="text" name="carVIN" value="${carForEdit.vin}"
                   pattern="^[a-zA-Z0-9]{14,18}$"
                   required/>
            <span class="form_error">${vin_valid_rules}</span>
        </div>

        <div class="form_field">
            <label>${manuf_message}</label>
            <input type="text" name="carManufactureDate" value="${carForEdit.manufactureDate}"
                   pattern="^(([1][9][4-9]\\d)|([2][0][0-2][0-9]))$"
                   required/>
            <span class="form_error">${man_date_rules}</span>
        </div>

        <div class="form_field">
            <label>${eng_power_message}</label>
            <input type="text" name="carEnginePower" value="${carForEdit.enginePower}"
                   pattern="^[0-9]{0,5}$"
                   required/>
            <span class="form_error">${eng_pow_rules}</span>
        </div>

        <div class="form_field">
            <label>${fuel_cons_message}</label>
            <input type="text" name="carFuelConsumption" value="${carForEdit.fuelConsumption}"
                   pattern="^(\d{1,2}\.\d+)$"
                   required/>
            <span class="form_error">${fu_cons_rules}</span>
        </div>

        <div class="form_field">
            <label>${price_message}</label>
            <input type="number" name="pricePerDay" value="${carForEdit.pricePerDay}" required/>
        </div>

        <div class="form_field">
            <label>${is_available_message}</label>
            <select name="isAvailableToRent" required>
                <c:if test="${carForEdit.isAvailableToRent eq 'AVAILABLE'}">
                    <option selected value="AVAILABLE">${available_message}</option>
                    <option value="NOT_AVAILABLE">${not_available_message}</option>
                </c:if>
                <c:if test="${carForEdit.isAvailableToRent eq 'NOT_AVAILABLE'}">
                    <option selected value="NOT_AVAILABLE">${not_available_message}</option>
                    <option value="AVAILABLE">${available_message}</option>
                </c:if>
            </select>
        </div>

        <div class="form_field">
            <label>${car_status_message}</label>
            <select name="isCarDeleted" required>
                <c:if test="${carForEdit.isDeleted eq 'ACTIVE'}">
                    <option selected value="${carForEdit.isDeleted}">${active_status}</option>
                    <option value="Deleted">${delete_status}</option>
                </c:if>
                <c:if test="${carForEdit.isDeleted eq 'DELETED'}">
                    <option selected value="Deleted">${delete_status}</option>
                    <option value="Active">${active_status}</option>
                </c:if>
            </select>
        </div>
    </c:forEach>

    <div class="edit_profile-message">
        <c:if test="${not empty requestScope.get('validationError')}">
            <c:out value="${validationError}"/><br/>
        </c:if>
    </div>

    <div>
        <input type="submit" value="${edit_button}">
    </div>

    <div>
        <a href="mainController?command=go_to_main_page">${back_button}</a>
    </div>
</form>
</body>
</html>
