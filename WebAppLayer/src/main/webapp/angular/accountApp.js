/**
 * @author Karel Vaculik
 */

var app = angular.module('accountApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.

        otherwise({redirectTo: '/home'});
}]);

