 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



 <c:remove var="currentUserLogin"/>
 <c:remove var="userRegData"/>
 <c:remove var="currentUserRole"/>
 <c:remove var="currentUserID"/>

 <c:redirect url="mainController?command=go_to_main_page"/>

