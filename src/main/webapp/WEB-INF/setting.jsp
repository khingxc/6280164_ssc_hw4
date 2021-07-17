<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<title>Setting Page</title>
<body>
<h2>
    This is where the content of the setting page is.
</h2>
<h3>
    Time: <%
        Date d = new Date();
        out.print(d);
    %>
</h3>
</body>
</html>