<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Project</title>
</head>
<body>

<c:if test="${not empty msg}">
    <div style="color: red;"><c:out value="${msg}"/></div>
</c:if>

<ul>
    <li>id: <c:out value="${project.id}"/></li>
    <li>title: <c:out value="${project.title}"/></li>
    <li>description: <c:out value="${project.description}"/></li>
    <li>imageUrl: <img src="${project.imageURL}" style="width: 50px; height: 50px;"></li>
    <li>budget: <c:out value="${project.budget}"/></li>
</ul>
<!-- display form if user has not bidded before -->
<c:choose>
    <c:when test="${!isBidAdded}">
        <form action="http://localhost:8080/ali_malikeh_war_exploded/projects/${project.id}/add_bid" method="POST">
            <label for="bidAmount">Bid Amount:</label>
            <input type="number" name="bidAmount">

            <button>Submit</button>
        </form>
    </c:when>
    <c:otherwise>
        <div>Bid already assigned!</div>
    </c:otherwise>
</c:choose>


</body>
</html>
