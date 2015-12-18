controllers.controller('AreasController', function ($http, $scope) {
    console.log('GET all areas request');
    $http.get('/creatures-hunting/rest/areas').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas";

            });
});

controllers.controller('NoCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with no creature request');
    $http.get('/creatures-hunting/rest/areas/no-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with no creature";

            });
});


controllers.controller('AnyCreatureAreaController', function ($http, $scope) {
    console.log('GET the areas with any creature request');
    $http.get('/creatures-hunting/rest/areas/any-creature').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with any creature";

            });
});

controllers.controller('MostCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with most creatures request');
    $http.get('/creatures-hunting/rest/areas/most-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with the most creatures";

            });
});

controllers.controller('FewestCreaturesAreaController', function ($http, $scope) {
    console.log('GET the areas with fewest creatures request');
    $http.get('/creatures-hunting/rest/areas/fewest-creatures').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
                $scope.page = "Areas with the fewest creatures";

            });
});

controllers.controller('ParticularAreaController', function ($http, $rootScope, $routeParams,  $location,$scope) {
    var id = $routeParams.areaId;
    $scope.creatureChosenAddCreature = {
        'name': ''
    };
    $scope.creatureChosenMoveCreature = {
        'name': ''
    };
    $scope.areaChosenMoveCreature = {
        'name': ''
    };
    console.log('GET particular area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id).
            then(function (response) {
                $scope.area = response.data;

            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when load area ' + response.data.message;
                    });
    console.log('GET creatures to area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id).
            then(function (response) {
                $scope.creatures = response.data['creatures'];
            });
            
    $scope.delete = function (id) {
        console.log('Delete area with id=' + id);
        $http.delete('/creatures-hunting/rest/areas/' + id).
                then(function success(response) {
                    console.log('Area with id=' + id + ' was deleted.');
                    $rootScope.succesAllert = 'Area was deleted';
                    $location.path('/areas');
                }, function error(response) {
                    console.log('Error when deleting area with id=' + id);
                    console.log(response);
                    $rootScope.errorAlert('Problem has occured when deleting area!');
                });
                
    };
    
    
     $scope.addCreature = function (name) {
        console.log('Add creature with name=' + name + 'to area with id ' + id);
        $http.post('/creatures-hunting/rest/areas/' + id + '/' + name + '/addcreature').
                then(function success(response) {
                    console.log('Creature with name=' + name + ' was added.');
                    $location.path('/areas');
                }, function error(response) {
                    console.log('Error when adding creature with name=' + name);
                    console.log(response);
                });

    };


    $scope.moveCreature = function (name, nameArea) {        
        console.log('Move creature with name=' + name + ' from area with id=' + id + 'to area with name ' + nameArea);
        $http.post('/creatures-hunting/rest/areas/' + id + '/' + nameArea + '/' + name + '/movecreature').
                then(function success(response) {
                    console.log('Creature with name=' + name + ' was moved.');
                    $location.path('/areas');
                }, function error(response) {
                    console.log('Error when moving creature with name=' + name);
                    console.log(response);
                });
    };

    $http.get('/creatures-hunting/rest/areas').
            then(function (response) {
                $scope.areas = response.data['_embedded']['areas'];
            });
            
    $http.get('/creatures-hunting/rest/creatures').
            then(function (response) {
            $scope.creatures2 = response.data['_embedded']['creatures'];
            });        
    
    console.log('GET others creatures to area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id +'/otherscreatures').
            then(function (response) {
                $scope.othersCreatures = response.data['_embedded']['creatures']
                ;
            });
    
    console.log('GET others areas to area with id=' + id);
    $http.get('/creatures-hunting/rest/areas/' + id +'/othersareas').
            then(function (response) {
                $scope.othersAreas = response.data['_embedded']['areas']
                ;
            });
});

controllers.controller('NewAreaController', function ($http, $rootScope, $location, $scope) {
    console.log('New area controller');
    $scope.area = {
        'name': '',       
        'description': ''
    };
    $scope.create = function (area) {
        console.log('Create area: ' + area.name);
        $http.post('/creatures-hunting/rest/areas/create', area).
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