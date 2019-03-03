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
                    <c:when test="${endorsedSkills.indexOf(skill.name) < 0}">
                        <form action="" method="">
                            <button>Endorse</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <div>endorsed</div>
                    </c:otherwise>
                </c:choose>
            </li>
            </c:forEach>
        </ul>
    </li>
</ul>
</body>
</html>
