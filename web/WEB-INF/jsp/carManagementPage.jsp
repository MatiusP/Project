<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Car management</title>
</head>

<jsp:include page="headerPage.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addCarForm.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css"/>

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

<fmt:message bundle="${loc}" key="local.carManagPage.addCar.button" var="addCar_button"/>
<fmt:message bundle="${loc}" key="local.carManagPage.findCar.button" var="findCar_button"/>
<fmt:message bundle="${loc}" key="local.carManagPage.allCars.button" var="allCars_button"/>
<fmt:message bundle="${loc}" key="local.carManagPage.find.main.message" var="find_main_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.allCars.allCars.main.message" var="allCars_main_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.noCars.message" var="no_cars_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.deleteCarResult.message" var="deleteCar_res_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.editCarResult.message" var="editCar_res_message"/>

<fmt:message bundle="${loc}" key="local.carManagPage.brand.message" var="carBrand_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.model.message" var="carModel_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.class.message" var="carClass_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.transmission.message" var="carTransm_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.price.message" var="carPrice_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.manDate.message" var="carManDate_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.engPower.message" var="carEngPow_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.fuelCons.message" var="carFuCons_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.availableToRent.message" var="carAvailable_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.status.message" var="carStatus_message"/>
<fmt:message bundle="${loc}" key="local.carManagPage.find.button" var="find_button"/>
<fmt:message bundle="${loc}" key="local.carManagPage.edit.button" var="edit_button"/>
<fmt:message bundle="${loc}" key="local.carManagPage.delete.button" var="delete_button"/>

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
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.message" var="car_status_message"/>
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.deleted.message" var="delete_status"/>
<fmt:message bundle="${loc}" key="local.editCarPage.carStatus.active.message" var="active_status"/>
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

<body>

<div class="management-button">
    <button><a href="mainController?command=go_to_car_management_page&param=addCar">${addCar_button}</a></button>
    <button><a href="mainController?command=go_to_car_management_page&param=getAllCars">${allCars_button}</a></button>
    <button><a href="mainController?command=go_to_car_management_page&param=findCar">${findCar_button}</a></button>
</div>

<div class="add_car_form-message">
    <c:if test="${not empty sessionScope.added_result}">
        <c:out value="${added_result}"/><br/>
        ${sessionScope.remove('added_result')}
    </c:if>
</div>

<c:if test="${sessionScope.command eq 'addCar'}">
    <form action="mainController" method="post" class="add_car_form">
        <input type="hidden" name="command" value="add_car"/>

        <h3>${page_message}</h3>

        <div class="form_field">
            <label>${brand_message}</label>
            <input type="text" name="carBrand" placeholder="${brand_message}"
                   pattern="^[A-Za-zА-ЯЁа-яё]*$"
                   required/>
            <span class="form_error">${brand_valid_rules}</span>
        </div>

        <div class="form_field">
            <label>${model_message}</label>
            <input type="text" name="carModel" placeholder="${model_message}" required/>
        </div>

        <div class="form_field">
            <label>${class_message}</label>
            <select name="carClassType" required>
                <option selected disabled>${select_message}</option>
                <option value="Economy">${econom_class_message}</option>
                <option value="Middle">${middle_class_message}</option>
                <option value="Premium">${premium_class_message}</option>
            </select>
        </div>

        <div class="form_field">
            <label>${transm_message}</label>
            <select name="transmissionType" required>
                <option selected disabled>${select_message}</option>
                <option value="Manual">${manual_transm_message}</option>
                <option value="Automatic">${automat_transm_message}</option>
            </select>
        </div>

        <div class="form_field">
            <label>${vin_message}</label>
            <input type="text" name="carVIN" placeholder="${vin_message}"
                   pattern="^[a-zA-Z0-9]{11}[0-9]{6}$"
                   required/>
            <span class="form_error">${vin_valid_rules}</span>
        </div>

        <div class="form_field">
            <label>${manuf_message}</label>
            <input type="text" name="manufactureDate" placeholder="${manuf_message}"
                   pattern="^(([1][9][4-9]\\d)|([2][0][0-2][0-1]))$"
                   required/>
            <span class="form_error">${man_date_rules}</span>
        </div>

        <div class="form_field">
            <label>${eng_power_message}</label>
            <input type="text" name="enginePower" placeholder="${eng_power_message}"
                   pattern="^[0-9]{0,5}$"
                   required/>
            <span class="form_error">${eng_pow_rules}</span>
        </div>

        <div class="form_field">
            <label>${fuel_cons_message}</label>
            <input type="text" name="fuelConsumption" placeholder="${fuel_cons_message}"
                   pattern="^[0-9]{0,2}$"
                   required/>
            <span class="form_error">${fu_cons_rules}</span>
        </div>

        <div class="form_field">
            <label>${price_message}</label>
            <input type="number" name="pricePerDay" placeholder="${price_message}" required/>
        </div>

        <div class="form_field">
            <label>${is_available_message}</label>
            <select name="availableToRent" required>
                <option selected disabled>${select_message}</option>
                <option value="Available">${available_message}</option>
                <option value="Not_available">${not_available_message}</option>
            </select>
        </div>

        <div class="form_field">
            <label>${photos_message}</label>
            <input type="file" name="photo" multiple accept="image/jpeg,image/png"/>
        </div>

        <div>
            <input type="submit" value="ADD">
        </div>
    </form>
</c:if>

<c:if test="${sessionScope.command eq 'findCar'}">
    <form id="findCar_form" action="mainController" method="post">
        <input type="hidden" name="command" value="find_car"/>

        <div class="h3">
            <h3>${find_main_message}</h3>
        </div>

        <table class="table_dark">
            <tr>
                <th>id</th>
                <th>${carBrand_message}</th>
                <th>${carModel_message}</th>
                <th>${carClass_message}</th>
                <th>${carTransm_message}</th>
                <th>${carPrice_message}</th>
                <th>${vin_message}</th>
                <th>${carManDate_message}</th>
                <th>${carEngPow_message}</th>
                <th>${carFuCons_message}</th>
                <th>${carAvailable_message}</th>
                <th>${carStatus_message}</th>
            </tr>

            <tr>
                <td><input type="number" name="carId" min="1" max="10000" placeholder="id"/></td>
                <td><input type="text" name="brand" placeholder="${carBrand_message}"/></td>
                <td><input type="text" name="model" placeholder="${carModel_message}"/></td>
                <td>
                    <select name="classType">
                        <option selected value="" disabled>${select_message}</option>
                        <option value="Economy">${econom_class_message}</option>
                        <option value="Middle">${middle_class_message}</option>
                        <option value="Premium">${premium_class_message}</option>
                    </select>
                </td>

                <td>
                    <select name="transmission">
                        <option selected value="" disabled>${select_message}</option>
                        <option value="Manual">${manual_transm_message}</option>
                        <option value="Automatic">${automat_transm_message}</option>
                    </select>
                </td>

                <td><input type="number" name="price" placeholder="${carPrice_message}"/></td>
                <td><input type="text" name="carVIN" placeholder="VIN"/></td>
                <td><input type="number" name="manufactureDate" placeholder="${carManDate_message}"/></td>
                <td><input type="number" name="enginePower" placeholder="${carEngPow_message}"/></td>
                <td><input type="number" name="fuelConsumption" placeholder="${carFuCons_message}"/></td>
                <td>
                    <select name="isAvailable">
                        <option selected value="" disabled>${select_message}</option>
                        <option value="Available">${available_message}</option>
                        <option value="Not_available">${not_available_message}</option>
                    </select>
                </td>
                <td>
                    <select name="isDeleted">
                        <option selected value="" disabled>${select_message}</option>
                        <option value="Deleted">${delete_status}</option>
                        <option value="Active">${active_status}</option>
                    </select>
                </td>
            </tr>
        </table>
        <button class="bot5" form="findCar_form" type="submit">${findCar_button}</button>
    </form>

    <form action="mainController" method="post">
        <input type="hidden" name="command" value="">

        <c:if test="${not empty sessionScope.deleteCarResult}">
            <div class="info-message">
                <h3><c:out value="${deleteCar_res_message}"/></h3>
            </div>
            ${sessionScope.remove('deleteCarResult')}
        </c:if>

        <c:if test="${not empty sessionScope.editCarResult}">
            <div class="info-message">
                <h3><c:out value="${editCar_res_message}"/></h3>
            </div>
            ${sessionScope.remove('editCarResult')}
        </c:if>

        <c:choose>
            <c:when test="${not empty requestScope.carNotFound}">
                <div class="info-message">
                    <h3><c:out value="${no_cars_message}"/></h3>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${not empty cars}">
                    <table class="table_dark table_sort">
                        <thead>
                        <tr>
                            <th>id</th>
                            <th>${carBrand_message}</th>
                            <th>${carModel_message}</th>
                            <th>${carClass_message}</th>
                            <th>${carTransm_message}</th>
                            <th>${carPrice_message}</th>
                            <th>${vin_message}</th>
                            <th>${carManDate_message}</th>
                            <th>${carEngPow_message}</th>
                            <th>${carFuCons_message}</th>
                            <th>${carAvailable_message}</th>
                            <th>${carStatus_message}</th>
                        </tr>
                        </thead>
                        <c:forEach items="${cars}" var="car">
                            <tr>
                                <td>${car.id}</td>
                                <td>${car.brand}</td>
                                <td>${car.model}</td>
                                <td>${car.classType}</td>
                                <td>${car.transmissionType}</td>
                                <td>${car.pricePerDay}</td>
                                <td>${car.vin}</td>
                                <td>${car.manufactureDate}</td>
                                <td>${car.enginePower}</td>
                                <td>${car.fuelConsumption}</td>
                                <td>${car.isAvailableToRent}</td>
                                <td>${car.isDeleted}</td>
                                <td>
                                    <a href="mainController?command=go_to_edit_car_page&id=${car.id}"/>${edit_button}
                                </td>
                                <td>
                                    <a href="mainController?command=delete_car&deleteCarId=${car.id}"/>${delete_button}
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </c:if>
            </c:otherwise>
        </c:choose>
    </form>
    <form id="exit" action="mainController?command=go_to_car_management_page" method="post">
        <button form="exit" class="bot5" type="submit">${back_button}</button>
    </form>
</c:if>

    <c:if test="${sessionScope.command eq 'getAllCars'}">

        <div class="h3">
            <h3>${allCars_main_message}</h3>
        </div>

        <form action="mainController" method="post">
            <input type="hidden" name="command" value="">

            <c:choose>
                <c:when test="${not empty requestScope.carNotFound}">
                    <div class="info-message">
                        <h3><c:out value="${no_cars_message}"/></h3>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${not empty cars}">
                        <table class="table_dark table_sort">
                            <thead>
                            <tr>
                                <th>id</th>
                                <th>${carBrand_message}</th>
                                <th>${carModel_message}</th>
                                <th>${carClass_message}</th>
                                <th>${carTransm_message}</th>
                                <th>${carPrice_message}</th>
                                <th>${vin_message}</th>
                                <th>${carManDate_message}</th>
                                <th>${carEngPow_message}</th>
                                <th>${carFuCons_message}</th>
                                <th>${carAvailable_message}</th>
                                <th>${carStatus_message}</th>
                            </tr>
                            </thead>
                            <c:forEach items="${cars}" var="car">
                                <tr>
                                    <td>${car.id}</td>
                                    <td>${car.brand}</td>
                                    <td>${car.model}</td>
                                    <td>${car.classType}</td>
                                    <td>${car.transmissionType}</td>
                                    <td>${car.pricePerDay}</td>
                                    <td>${car.vin}</td>
                                    <td>${car.manufactureDate}</td>
                                    <td>${car.enginePower}</td>
                                    <td>${car.fuelConsumption}</td>
                                    <td>${car.isAvailableToRent}</td>
                                    <td>${car.isDeleted}</td>
                                    <td>
                                        <a href="mainController?command=go_to_edit_car_page&id=${car.id}"/>${edit_button}
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </form>
        <form id="exit" action="mainController?command=go_to_car_management_page" method="post">
            <button form="exit" class="bot5" type="submit">${back_button}</button>
        </form>
    </c:if>











${sessionScope.remove('command')}

</body>
</html>
