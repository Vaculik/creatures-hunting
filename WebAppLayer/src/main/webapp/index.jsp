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

    <style>
        .footer {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 30px;
            background-color: #f5f5f5;
        }
    </style>
</head>

<body>
<nav class="navbar navbar-inverse navbar-static-top">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Creatures Hunting</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#/home">Home</a></li>
                <li><a href="#">Creatures</a></li>
                <li><a href="#">Weapons</a></li>
                <li><a href="#">Areas</a></li>
                <li><a href="#">Users</a></li>
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
        <div ng-view></div>
    </div>
</div>

<footer class="footer">
    <div class="container">
        <p class="text-muted">&copy;&nbsp;Masaryk University</p>
    </div>
</footer>
</body>
</html>
