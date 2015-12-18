controllers.controller('WeaponsController', function ($http, $scope) { //$routeParams
    console.log('GET weapons request');
    $http.get('/creatures-hunting/rest/weapons/').then(function (response) {
        $scope.weapons = response.data['_embedded']['weapons'];
    });
    $scope.title = "All weapons";
});

controllers.controller('ParticularWeaponController', function ($http, $rootScope, $location, $routeParams, $scope) {
    var weaponId = $routeParams.weaponId;
    console.log('GET weapon by id=' + weaponId);
    $http.get('/creatures-hunting/rest/weapons/' + weaponId).then(function (response) {//Request successful
        $scope.weapon = response.data;
        var ammoWeapons = weaponsByAmmoType(response.data.ammoType, $http, $scope);
        $scope.ammoWeapons = ammoWeapons;
        $scope.typeWeapons = weaponsByType(response.data.type, $http, $scope);
    }, function (data) {//Request failed
        $rootScope.warningAlert = "Could not load weapon: " + data.message;
    });

    $http.get('rest/weapon-efficiencies/most-vulnerable-to/weapon/' + weaponId).then(function (response) {
        console.log('GET creature most vulnerable to weapon ' + weaponId + ' SUCCESS');
        $scope.mostVulnerable = response.data['_embedded']['creatures'];
    }, function (response) {
        $rootScope.warningAlert = "Could not load most vulnerable creature: " + data.message;
    });

    $scope.delete = function (id) {
        console.log('DELETE weapon by id=' + weaponId);
        console.log('GET weapon-efficiencies of weapon'); //DELETE weapon-efficiencies of weapon
        $http.get('rest/weapon-efficiencies/weapon/' + weaponId).then(function (response) {//Request succesful
            var efficiencies = (response.data['_embedded'] != null) ? response.data['_embedded']['weapon-efficiencies'] : [];
            var promises = [];
            for (var i = 0; i < efficiencies.length; i++) {
                promises.push($http.delete('rest/weapon-efficiencies/' + efficiencies[i].id));
            }
            if (promises.length > 0)
                $q.all(promises);
            $http.delete('/creatures-hunting/rest/weapons/' + weaponId).then(function (response) {//Request succesful
                console.log('DELETE weapon id=' + weaponId + 'SUCCESS');
                $rootScope.succesAllert = "Weapon " + $scope.weapon.name + "deleted.";
                $location.path('/weapons/');
            }, function (data) {//Request failed
                console.log('DELETE weapon id=' + weaponId + 'FAILURE');
                $rootScope.errorAlert = "Could not delete weapon: " + data.message;
            });
        }, function (response) {//Request failed
            console.log('GET weapon-efficiencies of weapon FAILURE');
            console.log('DELETE weapon id=' + weaponId + 'FAILURE');
            $rootScope.errorAlert = "Could not delete weapon: " + response.data.message;
            return;
        });
    };
});

var weaponsByAmmoType = function (ammoType, $http, $scope) {
    console.log('GET weapons request');
    $http.get('/creatures-hunting/rest/weapons/ammotype/' + ammoType).then(function (response) {
        $scope.ammoWeapons = response.data['_embedded']['weapons'];
    });
};

var weaponsByType = function (type, $http, $scope) {
    console.log('GET weapons request');
    $http.get('/creatures-hunting/rest/weapons/type/' + type).then(function (response) {
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
        $http.post('/creatures-hunting/rest/weapons/create', weapon).then(function (response) {//Request successful
//            $rootScope.succesAllert("Weapon " + weapon.name + " created");
            $location.path('/weapons');
        }, function (response) {//Request failed
            console.log("CREATE weapon failed");
            console.log(response);
            $rootScope.errorAlert("Weapon could not be created.");
        });
    };
});

controllers.controller('EditWeaponController', function ($http, $routeParams, $rootScope, $location, $scope) {
    var weaponId = $routeParams.weaponId;
    $scope.types = ["MELEE", "GUN", "ENERGY", "EXPLOSIVE"];
    $scope.ammoTypes = ["NONE", "BULLET_9MM", "BULLET_NATO", "BATTERY", "MAGNUM_44"];
    console.log('GET weapon by id=' + weaponId);
    $http.get('/creatures-hunting/rest/weapons/' + weaponId).then(function (response) {//Request successful
        $scope.weapon = response.data;
    }, function (data) {//Request failed
        $rootScope.warningAlert = "Could not load weapon: " + data.message;
    });
    $scope.edit = function (weapon) {
        console.log("EDIT weapon " + weapon.name);
        $http.post('/creatures-hunting/rest/weapons/edit/'+weapon.id, weapon).then(function (response) {//Request successful
//            $rootScope.succesAllert("Weapon " + weapon.name + " edited");
            $location.path('/weapons/' + weapon.id);
        }, function (response) {//Request failed
            console.log("EDIT weapon failed");
            console.log(response);
            $rootScope.errorAlert("Weapon could not be edited.");
        });
    };
});