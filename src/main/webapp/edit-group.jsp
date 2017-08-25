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

             <h2>Edit the group ${groupName} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>
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
            <form action="/edit-group" method="POST" id="devicesForm" role="form" >
                <input type="hidden" id="deviceId" name="deviceId">
                <input type="hidden" id="action" name="action">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <h3>Registered devices</h3>
                <c:choose>
                    <c:when test="${not empty devicesList}">
                        <table  class="table table-striped">
                            <thead>
                                <tr>
                                    <td>Name</td>
                                    <td>Public ID</td>
                                    <td></td>
                                </tr>
                            </thead>
                            <c:set var="countActive" value="0" scope="page" />
                            <c:forEach var="device" items="${devicesList}">
                                <c:if test = "${device.state == 'ACTIVE'}">
                                    <c:set var="classSucess" value="info"/>
                                    <c:set var="countActive" value="${countActive + 1}" scope="page"/>
                                    <tr class="${classSucess}">
                                        <td><a href="/edit-device?deviceId=${device.idDevice}">${device.name}</a></td>
                                        <td>${device.pubId}</td>
                                        <td><a href="#" id="remove"
                                               onclick="document.getElementById('action').value = 'remove';document.getElementById('deviceId').value = '${device.idDevice}';
                                                    document.getElementById('devicesForm').submit();">
                                                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                            </a>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </table>
                        <c:if test = "${countActive == 0}">
                            <br>
                            <div class="alert alert-info">
                                No registered devices
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <br>
                        <div class="alert alert-info">
                            No registered devices
                        </div>
                    </c:otherwise>
                </c:choose>
                <h3>Awaited for devices</h3>
                <c:choose>
                <c:when test="${not empty devicesList}">
                    <table  class="table table-striped">
                        <thead>
                            <tr>
                                <td>Name</td>
                                <td>Public ID</td>
                                <td></td>
                            </tr>
                        </thead>
                        <c:set var="countAwaited" value="0" scope="page" />
                        <c:forEach var="device" items="${devicesList}">
                            <c:if test = "${device.state == 'CREATED'}">
                                <c:set var="classSucess" value="info"/>
                                <c:set var="countAwaited" value="${countAwaited + 1}" scope="page"/>
                                <tr class="${classSucess}">
                                    <td><a href="/edit-device?deviceId=${device.idDevice}">${device.name}</a></td>
                                    <td>${device.pubId}</td>
                                    <td><a href="#" id="remove"
                                           onclick="document.getElementById('action').value = 'remove';document.getElementById('deviceId').value = '${device.idDevice}';
                                                document.getElementById('devicesForm').submit();">
                                            <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
                                        </a>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </table>
                    <c:if test = "${countAwaited == 0}">
                        <br>
                        <div class="alert alert-info">
                            No awaited devices
                        </div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <br>
                    <div class="alert alert-info">
                        No awaited devices
                    </div>
                </c:otherwise>
            </c:choose>
        </form>

        <form action ="${contextPath}/new-device" >
            <input type="hidden" id="groupName" name="groupName" value="${groupName}">
            <br></br>
            <button type="submit" class="btn btn-primary  btn-md">Add device</button>
        </form>
        </c:if>
        </div>
        <!-- /container -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
