<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Groups</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <h2>List of groups for ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

        <c:if test="${not empty message}">
                        <div class="alert alert-success">
                            ${message}
                        </div>
        </c:if>
        <c:if test="${not empty error}">
                        <div class="alert alert-warning">
                            ${error}
                        </div>
        </c:if>
        <form action="/groups" method="POST" id="groupsForm" role="form" >
            <input type="hidden" id="groupName" name="groupName">
            <input type="hidden" id="action" name="action">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <c:choose>
                <c:when test="${not empty groupsList}">
                    <table  class="table table-striped">
                        <thead>
                            <tr>
                                <td>Name</td>
                                <td>Status</td>
                                <td></td>
                            </tr>
                        </thead>
                        <c:forEach var="group" items="${groupsList}">
                            <c:set var="classSucess" value="info"/>
                            <tr class="${classSucess}">
                                <c:choose>
                                    <c:when test="${not group.deactivated}">
                                        <td><a href="/edit-group?groupName=${group.name}">${group.name}</a></td>
                                        <td>Active</td>
                                        <td><a href="#" id="remove"
                                               onclick="document.getElementById('action').value = 'remove';document.getElementById('groupName').value = '${group.name}';
                                                    document.getElementById('groupsForm').submit();">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            </a>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                         <td>${group.name}</td>
                                         <td>Deactivated</td>
                                         <td></td>
                                    </c:otherwise>
                                </c:choose>
                            </tr>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <br>
                    <div class="alert alert-info">
                        Create new group
                    </div>
                </c:otherwise>
            </c:choose>
        </form>

        <form action ="${contextPath}/new-group">
            <br></br>
            <button type="submit" class="btn btn-primary  btn-md">New group</button>
        </form>
    </c:if>
</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
