/**
 * Created by vaculik on 14.12.15.
 */


var app = angular.module('creaturesHuntingApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.config(['$routeProvider', function ($routeProvider) {
        $routeProvider.
                when('/home', {templateUrl: 'pages/home.html'}).
                when('/creatures', {templateUrl: 'pages/creatures.html', controller: 'CreaturesController'}).
                when('/creatures/highest', {templateUrl: 'pages/creatures.html', controller: 'HighestCreaturesController'}).
                when('/creatures/heaviest', {templateUrl: 'pages/creatures.html', controller: 'HeaviestCreaturesController'}).
                when('/creatures/new', {templateUrl: 'pages/new/new-creature.html', controller: 'NewCreatureController'}).
                when('/creature/:creatureId', {
                    templateUrl: 'pages/particular/creature.html',
                    controller: 'ParticularCreatureController'}).
                when('/area/:areaId', {
                    templateUrl: 'pages/particular/area.html',
                    controller: 'ParticularAreaController'}).
                when('/weapons', {templateUrl: 'pages/weapons.html', controller: 'WeaponsController'}).
                when('/areas', {templateUrl: 'pages/areas.html', controller: 'AreasController'}).
                when('/areas/nocreature', {templateUrl: 'pages/areas.html', controller: 'NoCreatureAreaController'}).
                when('/areas/anycreature', {templateUrl: 'pages/areas.html', controller: 'AnyCreatureAreaController'}).
                when('/areas/mostcreatures', {templateUrl: 'pages/areas.html', controller: 'MostCreaturesAreaController'}).
                when('/areas/fewestcreatures', {templateUrl: 'pages/areas.html', controller: 'FewestCreaturesAreaController'}).
                when('/areas/new', {templateUrl: 'pages/new/new-area.html', controller: 'NewAreaController'}).
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

controllers.controller('AreasController', function ($http, $scope) {
    console.log('GET all areas request');
    $http.get('/creatures-hunting/rest/areas').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                ;
            });
});

controllers.controller('NoCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with no creature request');
    $http.get('/creatures-hunting/rest/areas/no-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                ;
            });
});


controllers.controller('AnyCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with any creature request');
    $http.get('/creatures-hunting/rest/areas/any-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                ;
            });
});

controllers.controller('MostCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with most creatures request');
    $http.get('/creatures-hunting/rest/areas/most-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                ;
            });
});

controllers.controller('FewestCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with fewest creatures request');
    $http.get('/creatures-hunting/rest/areas/fewest-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                ;
            });
});

controllers.controller('CreaturesController', function ($http, $scope) {
    console.log('GET all creatures request');
    $http.get('/creatures-hunting/rest/creatures').
            then(function (response) {
                $scope.creatures = response.data['_embedded']['creatures'];
                ;
            });
});

controllers.controller('HighestCreaturesController', function ($http, $scope) {
    console.log('GET the highest creatures request');
    $http.get('/creatures-hunting/rest/creatures/max-height').
            then(function (response) {
                $scope.creatures = response.data['_embedded']['creatures'];
                ;
            });
});

controllers.controller('HeaviestCreaturesController', function ($http, $scope) {
    console.log('GET the heaviest creatures request');
    $http.get('/creatures-hunting/rest/creatures/max-weight').
            then(function (response) {
                $scope.creatures = response.data['_embedded']['creatures'];
                ;
            });
});


controllers.controller('ParticularCreatureController', function ($http, $rootScope, $routeParams, $scope) {
    var id = $routeParams.creatureId;
    console.log('GET particular creature with id=' + id);
    $http.get('/creatures-hunting/rest/creatures/' + id).
            then(function (response) {
                $scope.creature = response.data;
                ;
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when load creature ' + response.data.message;
                    });
    console.log('GET most effective weapons to creature with id=' + id);
    $http.get('/creatures-hunting/rest/weapon-efficiencies/most-effective-to/creature/' + id).
            then(function (response) {
                $scope.mostEffective = response.data['_embedded']['weapons'];
                ;
            });
});

controllers.controller('ParticularAreaController', function ($http, $rootScope, $routeParams, $scope) {
    var id = $routeParams.areaId;
    console.log('GET particular area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id).
            then(function (response) {
                $scope.area = response.data;
                ;
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when load area ' + response.data.message;
                    });
    console.log('GET creatures to area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id).
            then(function (response) {
                $scope.creatures = response.data['creatures'];
                $scope.page = '/creatures-hunting/rest/creatures/';
                ;
            });

});


controllers.controller('NewCreatureController', function ($http, $scope) {
    console.log('New creature controller');
    $scope.types = ['VAMPIRE', 'BEAST', 'UNDEAD'];
})










//
//controllers.controller('CreaturesController', function ($scope, $http) {
//    $http.get('/creatures-hunting/rest/creatures/').then(function (response) {
//        var creatures = response.data['_embedded']['creatures'];
//        $scope.creatures = creatures;
//    });
//});