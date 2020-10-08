 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



 <%
  session.invalidate();
 %>

 <c:redirect url="mainController?command=go_to_main_page"/>

