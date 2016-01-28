/**
 * @author Karel Vaculik
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

var areaOfCreature = function (creatureId, $http, $scope) {
    console.log('GET area of creature with id=' + creatureId);
    $http.get('rest/creatures/'+creatureId+'/area').
        then(function (response) {
            $scope.creature.area = response.data;
            if (response.data.id != -1) {
                $scope.hasArea = true;
            } else {
                $scope.hasNoArea = true
            }
        })
};


controllers.controller('ParticularCreatureController', function ($http, $route, $rootScope, $location, $routeParams, $scope) {
    var creatureId = $routeParams.creatureId;
    $scope.chosenArea = {
        'areaName': ''
    };

    console.log('GET particular creature with id=' + creatureId);
    $http.get('rest/creatures/' + creatureId).
        then(function (response) {
            $scope.creature = response.data;
            creaturesOfType(response.data.type, $http, $scope);
            areaOfCreature(creatureId, $http, $scope);
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
                $rootScope.successAlertToDisplay = 'Creature was deleted';
                $location.path('/creatures/all');
            }, function error(response) {
                console.log('Error when deleting creature with id=' + id);
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured when deleting creature!';
            });
    };

    console.log('GET all areas');
    $http.get('rest/areas').
        then(function (response) {
            if ('_embedded' in response.data) {
                $scope.selectAreas = response.data['_embedded']['areas'];
            }
        });

    $scope.selectArea = function () {
        console.log('Add creature with name=' + $scope.creature.name + ' to area with name=' + $scope.chosenArea.areaName);
        var addCreatureDTO = {
            'areaId': 0,
            'creatureName': $scope.creature.name
        };
        for (var i = 0; i < $scope.selectAreas.length; i++) {
            console.log('Area with name=' + $scope.selectAreas[i].name);
            if ($scope.selectAreas[i].name == $scope.chosenArea.areaName) {
                addCreatureDTO.areaId = $scope.selectAreas[i].id;
            }
        }
        if (addCreatureDTO.areaId != 0) {
            $http.post('rest/areas/add-creature', addCreatureDTO).
                then(function success(response) {
                    console.log('Creature was added to area with name=' + $scope.chosenArea.areaName + ' .');
                    $route.reload();
                }, function error(response) {
                    console.log('Error when adding creature to area with name=' + $scope.chosenArea.areaName);
                    console.log(response);
                    $rootScope.errorAlert = 'Error when adding creature to area with name=', $scope.chosenArea.areaName;
                });
        }
    };

    $scope.removeArea = function() {
        console.log('Remove area of creature with name=' + $scope.creature.name);
        $http.post('rest/creatures/'+$scope.creature.id+'/remove-area').
            then(function success(response) {
                console.log('Area of creature '+$scope.creature.name+' was removed.');
                $route.reload();
            }, function error(response) {
                console.log('Error when removing area of creature '+$scope.creature.name);
                console.log(response);
                $rootScope.errorAlert = 'Error when removing area of creature with name='+$scope.creature.name;
            })
    }
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
                $rootScope.successAlertToDisplay = 'The new creature was created.';
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
            $rootScope.successAlertToDisplay = 'Creature was changed.';
            $location.path('/creature/' + creature.id);
        }, function (response) {//Request failed
            console.log("EDIT creature failed");
            console.log(response);
            $rootScope.errorAlert = "Creature could not be edited.";
        });
    };
});