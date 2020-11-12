 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <%
  session.invalidate();
 %>
 <c:redirect url="mainController?command=go_to_main_page"/>

