controllers.controller('AreasController', function ($http, $scope) {
    console.log('GET all areas request');
    $http.get('rest/areas').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "All areas";

            });
});

controllers.controller('NoCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with no creature request');
    $http.get('rest/areas/no-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with no creature";

            });
});


controllers.controller('AnyCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with any creature request');
    $http.get('rest/areas/any-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with any creature";

            });
});

controllers.controller('MostCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with most creatures request');
    $http.get('rest/areas/most-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with the most creatures";

            });
});

controllers.controller('FewestCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with fewest creatures request');
    $http.get('rest/areas/fewest-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with the fewest creatures";

            });
});

controllers.controller('ParticularAreaController', function ($http, $rootScope, $route, $routeParams, $location, $scope) {
    var id = $routeParams.areaId;
    $scope.creatureChosenMoveCreature = {
        'name': ''
    };
    $scope.areaChosenMoveCreature = {
        'name': ''
    };
    $scope.addCreatureDTO = {
        'areaId': id,
        'creatureName': ''
    };
    $scope.creatures = {};
    
    $http.get('rest/creatures/?view=all').
        then(function (response) {
            $scope.addCreatures = response.data['_embedded']['creatures']; // TODO subtract area.creatures
        });

        console.log('GET particular area with id=' + id);
    $http.get('rest/areas/' + id).
            then(function (response) {
                $scope.area = response.data;
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when load area ' + response.data.message;
                    });
    
    $scope.delete = function (id) {
        console.log('Delete area with id=' + id);
        $http.delete('rest/areas/' + id).
                then(function success(response) {
                    console.log('Area with id=' + id + ' was deleted.');
                    $rootScope.succesAllert = 'Area was deleted';
                    $location.path('/areas');
                }, function error(response) {
                    console.log('Error when deleting area with id=' + id);
                    console.log(response);
                    $rootScope.errorAlert = 'Problem has occured when deleting area!';
                });
    };


    $scope.addCreature = function () {
        console.log('Add creature with name=' + $scope.addCreatureDTO.creatureName + ' to area with id ' + $scope.addCreatureDTO.areaId);
        $http.post('rest/areas/add-creature', $scope.addCreatureDTO).
                then(function success(response) {
                    console.log('Creature with name=' + $scope.addCreatureDTO.creatureName + ' was added.');
                    $route.reload();
                }, function error(response) {
                    console.log('Error when adding creature with name=' + $scope.addCreatureDTO.creatureName);
                    console.log(response);
                    $rootScope.errorAlert = 'Error when adding creature with name=', $scope.addCreatureDTO.creatureName;
                });

    };

    $http.get('rest/areas').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
            });

    $http.get('rest/creatures').
            then(function (response) {
                $scope.creatures2 = response.data['_embedded']['creatures'];
            });

    console.log('GET creatures in no area');
    $http.get('rest/creatures/?view=no-area').
            then(function (response) {
                if ('_embedded' in response.data) {
                    $scope.addCreatures = response.data['_embedded']['creatures'];
                }
            });

    console.log('GET others areas to area with id=' + id);
    $http.get('rest/areas/' + id + '/othersareas').
            then(function (response) {
                $scope.othersAreas = response.data['_embedded']['areas']
                        ;
            });

    $scope.removeCreature = function (id) {
        console.log('Remove creature with id=' + id + ' from area ' + $scope.area.name);
        $http.post('rest/creatures/' + id + '/remove-area').
                then(function success(response) {
                    console.log('Area of creature with id=' + id + ' was removed.');
                    $route.reload();
                }, function error(response) {
                    console.log('Error when removing area of creature with id=' + id);
                    console.log(response);
                    $rootScope.errorAlert='Error when removing area of creature with id=' + id;
                })
    }
});


controllers.controller('NewAreaController', function ($http, $rootScope, $location, $scope) {
    console.log('New area controller');
    $scope.area = {
        'name': '',
        'description': ''
    };
    $scope.create = function (area) {
        console.log('Create area: ' + area.name);
        $http.post('rest/areas/create', area).
                then(function success(response) {
                    $rootScope.succesAllert = 'The new area was created.';
                    $location.path('/areas');
                }, function error(response) {
                    console.log('Error when creating new area');
                    console.log(response);
                    $rootScope.errorAlert = 'Problem has occured, cannot create new area!';
                });
    };

});


controllers.controller('EditAreaController', function ($http, $routeParams, $rootScope, $location, $scope, TYPES) {
    var areaId = $routeParams.areaId;
    console.log('GET area by id=' + areaId);
    $http.get('rest/areas/' + areaId).
            then(function (response) {
                $scope.area = response.data;
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when loading area ' + response.data.message;
                    });

    $scope.edit = function (area) {
        console.log("EDIT area " + area.name);
        $http.post('rest/areas/update', area).then(function (response) {
            $rootScope.succesAllert = 'Area was changed.';
            $location.path('/area/' + area.id);
        }, function (response) {//Request failed
            console.log("EDIT area failed");
            console.log(response);
            $rootScope.errorAlert = "Area could not be edited.";
        });
    };
});