<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User</title>
</head>
<body>
<ul>
    <li>id: <c:out value="${user.id}"/></li>
    <li>first name: <c:out value="${user.firstName}"/></li>
    <li>last name: <c:out value="${user.lastName}"/></li>
    <li>jobTitle: <c:out value="${user.jobTitle}"/></li>
    <li>bio: <c:out value="${user.bio}"/></li>
    <li>
        skills:
        <ul>
            <c:forEach var="skill" items="${user.skills}">
            <li>
                <c:out value="${skill.name}"/>: <c:out value="${skill.point}"/>
                <c:choose>
                    <c:when test="${skill.endorsers.indexOf(loggedInUser.id) < 0}">
                        <form action="http://localhost:8080/ali_malikeh_war_exploded/users/${user.id}" method="POST">
                            <input name="skill" type="hidden" value="${skill.name}"/>
                            <button>Endorse</button >
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div>Endorsed by you</div>
                    </c:otherwise>
                </c:choose>
            </li>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>
</html>
