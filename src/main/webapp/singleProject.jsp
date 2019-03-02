<%--
  Created by IntelliJ IDEA.
  User: alitabatabaeiat
  Date: 2019-03-02
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${project.id}"/></li>
    <li>title: <c:out value="${project.title}"/></li>
    <li>description: <c:out value="${project.description}"/></li>
    <li>imageUrl: <img src="${project.imageURL}" style="width: 50px; height: 50px;"></li>
    <li>budget: <C:out value="${project.budget}"/></li>
</ul>
<!-- display form if user has not bidded before -->
<form action="" method="">
    <label for="bidAmount">Bid Amount:</label>
    <input type="number" name="bidAmount">

    <button>Submit</button>
</form>
</body>
</html>
