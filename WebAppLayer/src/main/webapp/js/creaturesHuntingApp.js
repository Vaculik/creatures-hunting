/**
 * Created by vaculik on 14.12.15.
 */


var app = angular.module('creaturesHuntingApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.config(['$routeProvider', function($routeProvider) {
   $routeProvider.
       when('/home', {templateUrl: 'pages/home.html'}).
       when('/creatures/:viewType', {templateUrl: 'pages/creatures.html', controller: 'CreaturesController'}).
       when('/creature/new', {templateUrl: 'pages/new/new-creature.html', controller: 'NewCreatureController'}).
       when('/creature/:creatureId', {
           templateUrl: 'pages/particular/creature.html',
           controller: 'ParticularCreatureController'}).
       when('/weapons', {templateUrl: 'pages/weapons.html', controller: 'WeaponsController'}).
       when('/areas', {templateUrl: 'pages/areas.html', controller: 'AreasController'}).
       when('/users', {templateUrl: 'pages/users.html', controller: 'UsersController'}).
       otherwise({redirectTo: '/home'})
}]);


app.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});



controllers.controller('CreaturesController', function($http, $routeParams, $scope) {
    var viewType = $routeParams.viewType;
    console.log('GET creatures request viewType=' + viewType);
    $http.get('/creatures-hunting/rest/creatures/?view=' + viewType).
        then(function (response) {
            $scope.creatures = response.data['_embedded']['creatures'];;
        });
    $scope.title = "All creatures";
    if (viewType == "highest") {
        $scope.title = "The highest creatures";
    } else if (viewType == "heaviest") {
        $scope.title = "The heaviest creatures";
    }
});



controllers.controller('ParticularCreatureController', function ($http, $rootScope, $routeParams, $scope) {
    var id = $routeParams.creatureId;
    console.log('GET particular creature with id=' + id);
    $http.get('/creatures-hunting/rest/creatures/' + id).
        then(function(response) {
            $scope.creature = response.data;;
        },
        function error (response) {
            $rootScope.warningAlert = 'Problem occured when load creature ' + response.data.message;
        });
    console.log('GET most effective weapons to creature with id=' + id);
    $http.get('/creatures-hunting/rest/weapon-efficiencies/most-effective-to/creature/' + id).
        then(function(response) {
            $scope.mostEffective = response.data['_embedded']['weapons'];;
        });
});


controllers.controller('NewCreatureController', function($http, $scope) {
    console.log('New creature controller');
    $scope.types=['VAMPIRE', 'BEAST', 'UNDEAD'];
})
