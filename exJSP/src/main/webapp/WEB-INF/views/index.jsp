<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
</head>
<body>
    <!-- Declaration -->
    <%!
        int sum = 0;
        public void add(int num) {
            sum += num;
        }
    %>
    <h1>JSP index</h1>
    <!-- Script let -->
    <%
        for(int i=1;i<=10;i++) {
            add(i);
        %>
            <!-- Expression -->
            <div><%=i%></div>
        <%
        }
    %>
    <%=sum%>
</body>
</html>