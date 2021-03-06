<!doctype html>
<html lang="en">
<head>
    <title>CreaturesHunting</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="plugins/iCheck/square/blue.css">
    <!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <%-- start: AngularJS --%>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-resource.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular-route.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.2/angular-cookies.js"></script>
    <%--end: AngularJS--%>

</head>

<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|               | and light versions (skin-blue-light)    |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body ng-app="creaturesHuntingApp" class="skin-blue">

<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="#/home" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <%--<span class="logo-mini"><b>CH</b></span>--%>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>CreaturesHunting</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <div class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </div>
            <!-- Navbar Right Menu -->
            <div ng-cloak class="navbar-custom-menu">
                <ul ng-if="!isAuthenticated()" class="nav navbar-nav">
                    <li>
                        <a href="pages/login.html"><span class="glyphicon glyphicon-log-in"></span> Login</a>
                    </li>
                </ul>
                <ul ng-if="isAuthenticated()" class="nav navbar-nav">
                    <!-- User Account Menu -->
                    <li class="dropdown">
                        <!-- Menu Toggle Button -->
                        <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown">
                            <b><span ng-if="isAuthorized(userRoles.admin)">Admin: </span>
                                <span class="glyphicon glyphicon-user"></span> {{currentUser.loginName}}</b>
                        </a>
                        <ul class="dropdown-menu">
                            <li><a href="#/user/{{currentUser.userId}}">Profile</a></li>
                            <li><a href="#/user/change-password/{{currentUser.userId}}">Change password</a></li>
                            <li class="divider"></li>
                            <li><a href="javascript:void(0)" ng-click="logout()">
                                <span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="nav navbar-nav">
                    <li>
                        <a href="pages/register.html">Register</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header">HEADER</li>
                <!-- Optionally, you can add icons to the links -->
                <li><a href="#/home"><i class="fa fa-link"></i> <span>Home</span></a></li>
                <li><a href="#/creatures/all"><i class="fa fa-link"></i> <span>Creatures</span></a></li>
                <li><a href="#/weapons"><i class="fa fa-link"></i> <span>Weapons</span></a></li>
                <li><a href="#/areas"><i class="fa fa-link"></i> <span>Areas</span></a></li>
                <li><a href="#/users/all"><i class="fa fa-link"></i> <span>Users</span></a></li>
            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <section class="content-header">
                    <!-- Bootstrap-styled alerts, visible when $rootScope.xxxAlert is defined -->
                    <div ng-cloak ng-click="hideWarningAlert()" ng-show="warningAlert" class="alert alert-warning alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close" aria-hidden="true">
                            &times;</button>
                        <h4><i class="icon fa fa-warning"></i> Warning!</h4>
                        {{warningAlert}}
                    </div>
                    <div ng-cloak ng-click="hideErrorAlert()" ng-show="errorAlert" class="alert alert-danger alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close" aria-hidden="true">
                            &times;</button>
                        <h4><i class="icon fa fa-ban"></i> Error!</h4>
                        {{errorAlert}}
                    </div>
                    <div ng-cloak ng-click="hideSuccessAlert()" ng-show="successAlert" class="alert alert-success alert-dismissible" role="alert">
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close" aria-hidden="true">
                            &times;</button>
                        <h4><i class="icon fa fa-check"></i> Success!</h4>
                        {{successAlert}}
                    </div>
        </section>

        <div ng-view>
        </div>

    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- Default to the left -->
        <strong>Copyright &copy; 2015 <a href="http://www.muni.cz">Masaryk University</a></strong>
    </footer>

</div>
<!-- ./wrapper -->

<%--start: AngularJS--%>
<script src="angular/creaturesHuntingApp.js"></script>
<script src="angular/controllers/weaponControllers.js"></script>
<script src="angular/controllers/areaControllers.js"></script>
<script src="angular/controllers/userControllers.js"></script>
<script src="angular/controllers/creatureControllers.js"></script>
<%--end: AngularJS--%>

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.1.4 -->
<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/app.min.js"></script>
<!-- DataTables -->
<script src="plugins/datatables/jquery.dataTables.min.js"></script>
<!-- SlimScroll -->
<script src="plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="plugins/fastclick/fastclick.js"></script>

<!-- Optionally, you can add Slimscroll and FastClick plugins.
     Both of these plugins are recommended to enhance the
     user experience. Slimscroll is required when using the
     fixed layout. -->

</body>
</html>
