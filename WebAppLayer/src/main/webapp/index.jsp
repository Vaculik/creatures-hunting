<!doctype html>
<html lang="en">
<head>
    <title>Creatures Hunting</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <%-- Bootstrap --%>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <%-- AngularJS --%>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script>

    <script src="js/creaturesHuntingApp.js"></script>




    <style>
        .footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 50px;
            background-color: #f5f5f5;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#/home">Creatures Hunting</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="#/home">Home</a></li>
                <li><a href="#/creatures">Creatures</a></li>
                <li><a href="#/weapons">Weapons</a></li>
                <li><a href="#/areas">Areas</a></li>
                <li><a href="#/users">Users</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#"><span class="glyphicon glyphicon-log-in"></span>Login</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div ng-app="creaturesHuntingApp">
        <!-- Bootstrap-styled alerts, visible when $rootScope.xxxAlert is defined -->
        <div ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
            <button type="button" class="close" aria-label="Close" ng-click="hideWarningAlert()"> <span aria-hidden="true">&times;</span></button>
            <strong>Warning!</strong> <span>{{warningAlert}}</span>
        </div>
        <div ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
            <button type="button" class="close" aria-label="Close" ng-click="hideErrorAlert()"> <span aria-hidden="true">&times;</span></button>
            <strong>Error!</strong> <span>{{errorAlert}}</span>
        </div>
        <div ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
            <button type="button" class="close" aria-label="Close" ng-click="hideSuccessAlert()"> <span aria-hidden="true">&times;</span></button>
            <strong>Success !</strong> <span>{{successAlert}}</span>
        </div>
        <div ng-view>
            <p>Cena: {{5+5}}</p>
        </div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">&copy;&nbsp;Masaryk University</p>
    </div>
</footer>


</body>
</html>