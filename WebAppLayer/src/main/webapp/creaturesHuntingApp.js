/**
 * Created by vaculik on 14.12.15.
 */

var app = angular.module('creaturesHuntingApp', []);

app.config(['$routeProvider', function($routeProvider) {
   $routeProvider.
       when('/home', {templateUrl: 'pages/home.html', controller: 'HomeController'})
}]);
