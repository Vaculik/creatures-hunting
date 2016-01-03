/**
 * Created by vaculik on 24.12.15.
 */

controllers.controller('CreaturesController', function ($http, $routeParams, $scope) {
    var viewType = $routeParams.viewType;
    console.log('GET creatures request viewType=' + viewType);
    $http.get('rest/creatures/?view=' + viewType).
        then(function (response) {
            $scope.creatures = response.data['_embedded']['creatures'];
        });
    $scope.title = "All creatures";
    if (viewType === "highest") {
        $scope.title = "The highest creatures";
    } else if (viewType === "heaviest") {
        $scope.title = "The heaviest creatures";
    }
});


var creaturesOfType = function (type, $http, $scope) {
    console.log('GET creatures of type=' + type);
    $http.get('rest/creatures/type/' + type).
        then(function (response) {
            $scope.typeCreatures = response.data['_embedded']['creatures'];
        });
};


controllers.controller('ParticularCreatureController', function ($http, $rootScope, $location, $routeParams, $scope) {
    var creatureId = $routeParams.creatureId;
    console.log('GET particular creature with id=' + creatureId);
    $http.get('rest/creatures/' + creatureId).
        then(function (response) {
            $scope.creature = response.data;
            creaturesOfType(response.data.type, $http, $scope);
        },
        function error(response) {
            $rootScope.warningAlert = 'Problem occured when load creature ' + response.data.message;
        });

    console.log('GET most effective weapons to creature with id=' + creatureId);
    $http.get('rest/weapon-efficiencies/most-effective-to/creature/' + creatureId).
        then(function (response) {
            $scope.mostEffective = response.data['_embedded']['weapons'];
        });

    $scope.delete = function (id) {
        console.log('Delete creature with id=' + id);
        $http.delete('rest/creatures/' + id).
            then(function success(response) {
                console.log('Creature with id=' + id + ' was deleted.');
                $rootScope.succesAllert = 'Creature was deleted';
                $location.path('/creatures/all');
            }, function error(response) {
                console.log('Error when deleting creature with id=' + id);
                console.log(response);
                $rootScope.errorAlert('Problem has occured when deleting creature!');
            });
    };
});

controllers.controller('NewCreatureController', function ($http, $rootScope, $location, $scope, TYPES) {
    console.log('New creature controller');
    $scope.types = TYPES.creatureTypes;
    $scope.creature = {
        'name': '',
        'height': 0,
        'weight': 0,
        'type': $scope.types[0],
        'description': ''
    };
    $scope.create = function (creature) {
        console.log('Create creature: ' + creature.name);
        $http.post('rest/creatures/create', creature).
            then(function success(response) {
                $rootScope.succesAllert = 'The new creature was created.';
                $location.path('/creatures/all');
            }, function error(response) {
                console.log('Error when creating new creature');
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured, cannot create new creature!';
            });
    };
});

controllers.controller('EditCreatureController', function ($http, $routeParams, $rootScope, $location, $scope, TYPES) {
    var creatureId = $routeParams.creatureId;
    $scope.types = TYPES.creatureTypes;
    console.log('GET creature by id=' + creatureId);
    $http.get('rest/creatures/' + creatureId).
        then(function (response) {
            $scope.creature = response.data;
        },
        function error(response) {
            $rootScope.warningAlert = 'Problem occured when loading creature ' + response.data.message;
        });

    $scope.edit = function (creature) {
        console.log("EDIT creature " + creature.name);
        $http.post('rest/creatures/update', creature).then(function (response) {
            $rootScope.succesAllert = 'Creature was changed.';
            $location.path('/creature/' + creature.id);
        }, function (response) {//Request failed
            console.log("EDIT creature failed");
            console.log(response);
            $rootScope.errorAlert("Creature could not be edited.");
        });
    };
});