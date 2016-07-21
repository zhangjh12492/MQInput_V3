<%@ page import="java.net.InetAddress" %>
<%--
  Created by IntelliJ IDEA.
  User: MaYong
  Date: 2015/4/17
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>接入服务检测</title>
    <%
        InetAddress addr = InetAddress.getLocalHost();
        String ip = addr.getHostAddress().toString();
        String address = addr.getHostName().toString();//获得本机名称
    %>
</head>
<body>

接入服务检测

hostname:<%=address%>
IP:<%=ip%>
</body>
</html>
