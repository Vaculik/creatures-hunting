controllers.controller('WeaponsController', function ($http, $scope) { //$routeParams
    console.log('GET weapons request');
    $http.get('rest/weapons/').then(function (response) {
        $scope.weapons = response.data['_embedded']['weapons'];
    });
    $scope.title = "All weapons";
});

controllers.controller('ParticularWeaponController', function ($http, $rootScope, $location, $routeParams, $scope, $route) {
    var weaponId = $routeParams.weaponId;
    console.log('GET weapon by id=' + weaponId);
    $http.get('rest/weapons/' + weaponId).then(function (response) {//Request successful
        $scope.weapon = response.data;
        $scope.ammoWeapons = weaponsByAmmoType(response.data.ammoType, $http, $scope);
        $scope.typeWeapons = weaponsByType(response.data.type, $http, $scope);
    }, function (data) {//Request failed
        $rootScope.warningAlert = "Could not load weapon: " + data.message;
    });

    $http.get('rest/weapon-efficiencies/most-vulnerable-to/weapon/' + weaponId).then(function (response) {
        console.log('GET creature most vulnerable to weapon ' + weaponId + ' SUCCESS');
        if ('_embedded' in response.data) {
            $scope.mostVulnerable = response.data['_embedded']['creatures'];
        }
    }, function (response) {
        $rootScope.warningAlert = "Could not load most vulnerable creature: " + data.message;
    });

    $http.get('rest/weapon-efficiencies/weapon/' + weaponId).then(function (response) {
        console.log('GET all efficiencies of weapon ' + weaponId + ' SUCCESS');
        if ('_embedded' in response.data) {
            $scope.efficiencies = response.data['_embedded']['weapon-efficiencies'];
        }
    }, function (response) {
        $rootScope.warningAlert = "Could not load weapon efficiencies: " + data.message;
    });
    
    $scope.delete = function (id) {
        console.log('DELETE weapon by id=' + weaponId);
        $http.delete('rest/weapons/' + weaponId).then(function (response) {//Request succesful
                console.log('DELETE weapon id=' + weaponId + 'SUCCESS');
                $rootScope.successAlertToDisplay = "Weapon " + $scope.weapon.name + " was deleted.";
                $location.path('/weapons');
            }, function (response) {//Request failed
                console.log('DELETE weapon id=' + weaponId + 'FAILURE');
                $rootScope.errorAlert = "Could not delete weapon: " + data.message;
            });
    };

    $scope.deleteEff = function (id) {
        console.log('DELETE weapon efficiency request');
        $http.delete('rest/weapon-efficiencies/' + id).then(function (response) {
            $route.reload();
        })
    }
});

var weaponsByAmmoType = function (ammoType, $http, $scope) {
    console.log('GET weapons request');
    $http.get('rest/weapons/ammotype/' + ammoType).then(function (response) {
        $scope.ammoWeapons = response.data['_embedded']['weapons'];
    });
};

var weaponsByType = function (type, $http, $scope) {
    console.log('GET weapons request');
    $http.get('rest/weapons/type/' + type).then(function (response) {
        $scope.typeWeapons = response.data['_embedded']['weapons'];
    });
};


controllers.controller('NewWeaponController', function ($http, $rootScope, $location, $scope) {
    $scope.types = ["MELEE", "GUN", "ENERGY", "EXPLOSIVE"];
    $scope.ammoTypes = ["NONE", "BULLET_9MM", "BULLET_NATO", "BATTERY", "MAGNUM_44"];
    $scope.weapon = {
        'name': '',
        'type': $scope.types[0],
        'ammoType': $scope.ammoTypes[0],
        'range': 0,
        'description': ""
    };
    $scope.create = function (weapon) {
        console.log("Create weapon " + weapon.name);
        $http.post('rest/weapons/create', weapon).then(function (response) {//Request successful
            $rootScope.successAlertToDisplay = "Weapon " + weapon.name + " was created";
            $location.path('/weapons');
        }, function (response) {//Request failed
            console.log("CREATE weapon failed");
            console.log(response);
            $rootScope.errorAlert = "Weapon could not be created.";
        });
    };
});

controllers.controller('EditWeaponController', function ($http, $routeParams, $rootScope, $location, $scope) {
    var weaponId = $routeParams.weaponId;
    $scope.types = ["MELEE", "GUN", "ENERGY", "EXPLOSIVE"];
    $scope.ammoTypes = ["NONE", "BULLET_9MM", "BULLET_NATO", "BATTERY", "MAGNUM_44"];
    console.log('GET weapon by id=' + weaponId);
    $http.get('rest/weapons/' + weaponId).then(function (response) {//Request successful
        $scope.weapon = response.data;
    }, function (data) {//Request failed
        $rootScope.warningAlert = "Could not load weapon: " + data.message;
    });
    $scope.edit = function (weapon) {
        console.log("EDIT weapon " + weapon.name);
        $http.post('rest/weapons/edit/' + weapon.id, weapon).then(function (response) {//Request successful
            $rootScope.successAlertToDisplay = "Weapon " + weapon.name + " was edited";
            $location.path('/weapons/' + weapon.id);
        }, function (response) {//Request failed
            console.log("EDIT weapon failed");
            console.log(response);
            $rootScope.errorAlert = "Weapon could not be edited.";
        });
    };
});


controllers.controller('NewWeaponEfficiencyController', function ($http, $rootScope, $location, $scope, $routeParams) {
    var weaponId = $routeParams.weaponId;
    console.log('GET creatures request viewType=all');
    $http.get('rest/creatures/?view=all').then(function (response) {
        if ('_embedded' in response.data) {
            $scope.creatureDTOs = response.data['_embedded']['creatures'];
        }
    });
    $scope.efficiency = {
        'creatureName': '',
        'weaponName': '',
        'efficiency': 0
    };
    console.log('GET weapons request');
    $http.get('rest/weapons/').then(function (response) {
        if ('_embedded' in response.data) {
            $scope.weaponDTOs = response.data['_embedded']['weapons'];
            for (var i = 0; i < $scope.weaponDTOs.length; i++) {
                if ($scope.weaponDTOs[i].id == weaponId) {
                    $scope.weapon = $scope.weaponDTOs[i];
                    $scope.efficiency.weaponName = $scope.weapon.name;
                    break;
                }
            }
        }
    });

    $scope.create = function () {
        console.log("Create efficiency for weapon "+$scope.efficiency.weaponName+
            ' and creature '+$scope.efficiency.creatureName);
        var newEfficiency = {
            "efficiency": $scope.efficiency.efficiency,
            "creatureId": '',
            "weaponId": ''
        };
        for (var i=0; i < $scope.weaponDTOs.length; i++) {
            if ($scope.efficiency.weaponName == $scope.weaponDTOs[i].name) {
                newEfficiency.weaponId = $scope.weaponDTOs[i].id;
                break;
            }
        }
        for (i=0; i < $scope.creatureDTOs.length; i++) {
            if ($scope.efficiency.creatureName == $scope.creatureDTOs[i].name) {
                newEfficiency.creatureId = $scope.creatureDTOs[i].id;
                break;
            }
        }
        console.log('POST new efficiency relation for weapon with id='+newEfficiency.weaponId+' and creature with id='+
            newEfficiency.creatureId+' with efficiency='+newEfficiency.efficiency);
        $http.post('rest/weapon-efficiencies/create', newEfficiency).
            then(function (response) {//Request successful
            $rootScope.successAlertToDisplay = "Weapon efficency was created";
            $location.path('/weapons/'+weaponId);
        }, function (response) {//Request failed
            console.log("CREATE efficiency failed");
            console.log(response);
            $rootScope.errorAlert = "Efficiency could not be created.";
        });
    };
});
