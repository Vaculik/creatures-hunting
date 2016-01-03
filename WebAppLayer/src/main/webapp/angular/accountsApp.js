/**
 * Created by vaculik on 3.1.16.
 */

var app = angular.module('accountsApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.

        otherwise({redirectTo: '/home'});
}]);

