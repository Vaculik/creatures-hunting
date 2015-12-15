/**
 * Created by vaculik on 14.12.15.
 */


var app = angular.module('creaturesHuntingApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.config(['$routeProvider', function($routeProvider) {
   $routeProvider.
       when('/home', {templateUrl: 'pages/home.html'}).
       when('/creatures', {templateUrl: 'pages/creatures.html', controller: 'CreaturesController'}).
       when('/weapons', {templateUrl: 'pages/weapons.html', controller: 'WeaponsController'}).
       when('/areas', {templateUrl: 'pages/areas.html', controller: 'AreasController'}).
       when('/users', {templateUrl: 'pages/users.html', controller: 'UsersController'}).
       otherwise({redirectTo: '/home'})
}]);


//app.run(function ($rootScope) {
//    $rootScope.hideSuccessAlert = function () {
//        $rootScope.successAlert = undefined;
//    };
//    $rootScope.hideWarningAlert = function () {
//        $rootScope.warningAlert = undefined;
//    };
//    $rootScope.hideErrorAlert = function () {
//        $rootScope.errorAlert = undefined;
//    };
//});

