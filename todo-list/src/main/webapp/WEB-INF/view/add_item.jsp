<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="academy.learnprogramming.util.AttributeNames" %>
<html>
<head>
    <title>Add Items</title>
</head>
<body>
    <div align="center">
        <form:form method="POST" modelAttribute="${AttributeNames.TODO_ITEMS}">

        </form:form>
    </div>
</body>
</html>