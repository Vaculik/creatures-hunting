
/**
 * @author Karel Vaculik
 */

var app = angular.module('creaturesHuntingApp', ['ngRoute', 'controllers']);
var controllers = angular.module('controllers', []);

app.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

app.constant('USER_ROLES', {
    admin: 'admin',
    user: 'user'
});


app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/home', {templateUrl: 'pages/home.html'}).
        when('/creatures/:viewType', {templateUrl: 'pages/creatures.html', controller: 'CreaturesController'}).
        when('/creature/new', {templateUrl: 'pages/new/new-creature.html', controller: 'NewCreatureController'}).
        when('/creature/:creatureId', {
            templateUrl: 'pages/particular/creature.html',
            controller: 'ParticularCreatureController'}).
        when('/weapons', {templateUrl: 'pages/weapons.html', controller: 'WeaponsController'}).
        when('/areas', {templateUrl: 'pages/areas.html', controller: 'AreasController'}).
        when('/areas/nocreature', {templateUrl: 'pages/areas.html', controller: 'NoCreatureAreaController'}).
        when('/areas/anycreature', {templateUrl: 'pages/areas.html', controller: 'AnyCreatureAreaController'}).
        when('/areas/mostcreatures', {templateUrl: 'pages/areas.html', controller: 'MostCreaturesAreaController'}).
        when('/areas/fewestcreatures', {templateUrl: 'pages/areas.html', controller: 'FewestCreaturesAreaController'}).
        when('/areas/new', {templateUrl: 'pages/new/new-area.html', controller: 'NewAreaController'}).
        when('/area/:areaId', {
            templateUrl: 'pages/particular/area.html',
            controller: 'ParticularAreaController'}).
        when('/login', {templateUrl: 'pages/login.html', controller: 'LoginController'}).
        when('/users/:viewType', {templateUrl: 'pages/users.html', controller: 'UsersController'}).
        when('/user/new', {templateUrl: 'pages/new/new-user.html', controller: 'NewUserController'}).
        when('/user/:userId', {templateUrl: 'pages/particular/user.html', controller: 'ParticularUserController'}).
        otherwise({redirectTo: '/home'});
}]);


app.service('Session', function () {
    this.create = function (userId, userRole) {
        console.log('Create session:', userId, userRole);
        this.userId = userId;
        this.userRole = userRole;
    };
    this.destroy = function () {
        console.log('Destroy session');
        this.userId = null;
        this.userRole = null;
    };
});


app.factory('AuthService', function ($http, $rootScope, USER_ROLES, Session) {
    var authService = {};

    authService.login = function (credentials) {
        console.log('Authenticate user with loginName=' + credentials.logiName);
        return $http
            .post('/creatures-hunting/rest/users/login', credentials)
            .then(function (response) {
                console.log('User has been authenticated');
                var role = USER_ROLES.user;
                if (response.data.admin) {
                    role = USER_ROLES.admin;
                }
                Session.create(response.data.userId, role);
                $rootScope.successAlert = 'User has been authenticated.';
                return response.data;
            }, function(response) {
                console.log("User hasn't been  authenticated");
                $rootScope.errorAlert = 'Authentication has failed.';
            });
    };
    // If id is null then boolean value is false -> return false;
    // If id != 0 then boolean value is true -> return true;
    authService.isAuthenticated = function () {
        return Boolean(Session.userId);
    };

    authService.isAuthorized = function (authorizedRoles) {
        if (!angular.isArray(authorizedRoles)) {
            authorizedRoles = [authorizedRoles];
        }
        return (authService.isAuthenticated() &&
        authorizedRoles.indexOf(Session.userRole) != -1);
    };

    authService.logout = function() {
        $rootScope.successAlert = 'User has been logged out.';
        Session.destroy();
    };

    return authService;
});


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


controllers.controller('ApplicationController', function($scope, USER_ROLES, AuthService) {
    // Initialize the scope properties for authentization
    $scope.currentUser = null;
    $scope.userRoles = USER_ROLES;
    $scope.isAuthenticated = AuthService.isAuthenticated;
    $scope.isAuthorized = AuthService.isAuthorized;
    // Setter of currentUser property. We can’t simply assign a new value to it from a child scope,
    // because that would result in a shadow property.
    $scope.setCurrentUser = function(user) {
        $scope.currentUser = user;
    };
    $scope.logout = AuthService.logout;
});


controllers.controller('LoginController', function ($scope, $rootScope, AUTH_EVENTS, AuthService, $location) {
    $scope.credentials = {
        loginName: '',
        password: ''
    };
    $scope.login = function (credentials) {
        AuthService.login(credentials).then(function (user) {
            $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
            $scope.setCurrentUser(user);
            $location.path('/home');
        }, function () {
            $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
        });
    };
});


controllers.controller('CreaturesController', function ($http, $routeParams, $scope) {
    var viewType = $routeParams.viewType;
    console.log('GET creatures request viewType=' + viewType);
    $http.get('/creatures-hunting/rest/creatures/?view=' + viewType).
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
    $http.get('/creatures-hunting/rest/creatures/type/' + type).
            then(function (response) {
                $scope.typeCreatures = response.data['_embedded']['creatures'];
            });
};


controllers.controller('ParticularCreatureController', function ($http, $rootScope, $location, $routeParams, $scope) {
    var creatureId = $routeParams.creatureId;
    console.log('GET particular creature with id=' + creatureId);
    $http.get('/creatures-hunting/rest/creatures/' + creatureId).
            then(function (response) {
                $scope.creature = response.data;
                creaturesOfType(response.data.type, $http, $scope);
                console.log($scope.typeCreatures);
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when load creature ' + response.data.message;
                    });

    console.log('GET most effective weapons to creature with id=' + creatureId);
    $http.get('/creatures-hunting/rest/weapon-efficiencies/most-effective-to/creature/' + creatureId).
            then(function (response) {
                $scope.mostEffective = response.data['_embedded']['weapons'];
            });

    $scope.delete = function (id) {
        console.log('Delete creature with id=' + id);
        $http.delete('/creatures-hunting/rest/creatures/' + id).
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

controllers.controller('NewCreatureController', function ($http, $rootScope, $location, $scope) {
    console.log('New creature controller');
    $scope.types = ['VAMPIRE', 'BEAST', 'UNDEAD'];
    $scope.creature = {
        'name': '',
        'height': 0,
        'weight': 0,
        'type': $scope.types[0],
        'description': ''
    };
    $scope.create = function (creature) {
        console.log('Create creature: ' + creature.name);
        $http.post('/creatures-hunting/rest/creatures/create', creature).
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
    $scope.a = '#areas/'+id+'/addcreature';
     $scope.types = ['VAMPIRE', 'BEAST', 'UNDEAD'];
    $scope.creatureChosenAddCreature = {
        'name': ''
    };
    $scope.creatureChosenMoveCreature = {
        'name': ''
    };
    $scope.areaChosenMoveCreature = {
        'name': ''
    };
    console.log('A is = ' + $scope.a);
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
        console.log('Add creature with name=' + name);        
        $http.post('/creatures-hunting/rest/areas/addcreature', id,name).
                then(function success(response) {
                    console.log('Creature with name=' + name + ' was added.');
                    $location.path('/areas/id');
                }, function error(response) {
                    console.log('Error when adding creature with name=' + name);
                    console.log(response);
                });
       
    };
    
    $scope.moveCreature = function (name, nameArea) {
        console.log('Move creature with name=' + name);
        console.log('Move to area with name=' + nameArea);
        $http.post('/creatures-hunting/rest/areas/movecreature', name,id,nameArea).
                then(function success(response) {
                    console.log('Creature with name=' + name + ' was moved.');
                    $location.path('/areas/id');
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

var usersOfType = function (type, $http, $scope) {
    console.log('GET users of type=' + type);
    $http.get('/creatures-hunting/rest/users/type/' + type).
            then(function (response) {
            	$scope.users = response.data['_embedded']['users'];
            });
};

var usersOfSex = function (sex, $http, $scope) {
    console.log('GET users of type=' + sex);
    $http.get('/creatures-hunting/rest/users/sex/' + sex).
            then(function (response) {
                $scope.users = response.data['_embedded']['users'];
            });
};

controllers.controller('UsersController', function ($http, $routeParams, $scope) {
    var viewType = $routeParams.viewType;
    console.log('GET USERS request viewType=' + viewType);
    if (viewType == 'all') {
    	$http.get('/creatures-hunting/rest/users/?view=' + viewType).
            then(function (response) {
                $scope.users = response.data['_embedded']['users'];
            });
    	$scope.title = "All users";
    }
    else if (viewType == 'ordinary') {
    	usersOfType('ORDINARY', $http, $scope);
    	$scope.title = "Ordinary users";
    }
    else if (viewType == 'admins') {
		usersOfType('ADMIN', $http, $scope);
		$scope.title = "Admins";
    }
    else if (viewType == 'men') {
		usersOfSex('MALE', $http, $scope);
		$scope.title = "Male users";
    }
    else {
		usersOfSex('FEMALE', $http, $scope);
		$scope.title = "Female users";
    }
});

controllers.controller('ParticularUserController', function ($http, $rootScope, $location, $routeParams, $scope) {
	var userId = $routeParams.userId;
	console.log('GET particular user with id=' + userId);
    $http.get('/creatures-hunting/rest/users/' + userId).
            then(function (response) {
                $scope.user = response.data;
                console.log($scope.user.name + " loaded");
            },
                    function error(response) {
                        $rootScope.warningAlert = 'Problem occured when loading user ' + response.data.message;
                    });
    
    $scope.delete = function (id) {
    	console.log('Delete user with id=' + id);
    	$http.delete('/creatures-hunting/rest/users/' + id).
          	then(function success(response) {
          		console.log('User with id=' + id + ' was deleted.');
          		$rootScope.succesAllert = 'User was deleted';
          		$location.path('/users/all');
          	}, function error(response) {
          		console.log('Error when deleting user with id=' + id);
          		console.log(response);
          		$rootScope.errorAlert('Problem has occured when deleting user!');
          	});
    };
});

controllers.controller('NewUserController', function ($http, $rootScope, $location, $scope) {
    console.log('New user controller');
    $scope.types = ['ADMIN', 'ORDINARY'];
    $scope.sexes = ['MALE', 'FEMALE'];
    $scope.user = {
        'name': '',
        'type': $scope.types[0],
        'sex': $scope.sexes[0],
        'dateOfBirth': '',
        'userName': '',
        'password': ''
    };
    $scope.create = function (user) {
        console.log('Create user: ' + user.name);
        $http.post('/creatures-hunting/rest/users/create', user).
                then(function success(response) {
                    $rootScope.succesAllert = 'New user was created.';
                    $location.path('/users/all');
                }, function error(response) {
                    console.log('Error when creating new user');
                    console.log(response);
                    $rootScope.errorAlert = 'Problem has occured, cannot create new user!';
                });
    };
});

