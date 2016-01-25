/**
 * @author Karel Vaculik
 */

var app = angular.module('creaturesHuntingApp', ['ngRoute', 'ngCookies', 'controllers']);
var controllers = angular.module('controllers', []);

app.constant('USER_ROLES', {
    admin: 'ADMIN',
    user: 'ORDINARY'
});

app.constant('TYPES', {
    creatureTypes: ['VAMPIRE', 'BEAST', 'UNDEAD']
});


app.config(['$routeProvider', '$httpProvider', '$cookiesProvider',
    function ($routeProvider, $httpProvider, $cookiesProvider) {
        $routeProvider.
            when('/home', {
                templateUrl: 'pages/home.html'
            }).
            when('/creatures/:viewType', {
                templateUrl: 'pages/creatures.html',
                controller: 'CreaturesController'
            }).
            when('/creature/new', {
                templateUrl: 'pages/new/new-creature.html',
                controller: 'NewCreatureController'
            }).
            when('/creature/:creatureId', {
                templateUrl: 'pages/particular/creature.html',
                controller: 'ParticularCreatureController'
            }).
            when('/creature/:creatureId/edit', {
                templateUrl: 'pages/edit/edit-creature.html',
                controller: 'EditCreatureController'
            }).
            when('/weapons', {
                templateUrl: 'pages/weapons.html',
                controller: 'WeaponsController'
            }).
            when('/weapons/new', {
                templateUrl: 'pages/new/new-weapon.html',
                controller: 'NewWeaponController'
            }).
            when('/efficiency/new/:weaponId', {
                templateUrl: 'pages/new/new-efficiency.html',
                controller: 'NewWeaponEfficiencyController'
            }). //In weaponControllers.js
            when('/weapons/edit/:weaponId', {
                templateUrl: 'pages/edit/edit-weapon.html',
                controller: 'EditWeaponController'
            }).
            when('/weapons/:weaponId', {
                templateUrl: 'pages/particular/weapon.html',
                controller: 'ParticularWeaponController'
            }).
            when('/areas', {
                templateUrl: 'pages/areas.html',
                controller: 'AreasController'
            }).
            when('/areas/nocreature', {
                templateUrl: 'pages/areas.html',
                controller: 'NoCreatureAreaController'
            }).
            when('/areas/anycreature', {
                templateUrl: 'pages/areas.html',
                controller: 'AnyCreatureAreaController'
            }).
            when('/areas/mostcreatures', {
                templateUrl: 'pages/areas.html',
                controller: 'MostCreaturesAreaController'
            }).
            when('/areas/fewestcreatures', {
                templateUrl: 'pages/areas.html',
                controller: 'FewestCreaturesAreaController'
            }).
            when('/areas/new', {
                templateUrl: 'pages/new/new-area.html',
                controller: 'NewAreaController'
            }).
            when('/areas/:areaId/edit', {
                templateUrl: 'pages/edit/edit-area.html',
                controller: 'EditAreaController'
            }).
            when('/area/:areaId', {
                templateUrl: 'pages/particular/area.html',
                controller: 'ParticularAreaController'
            }).
            when('/login', {
                templateUrl: 'pages/login.html',
                controller: 'LoginController'
            }).
            when('/users/:viewType', {
                templateUrl: 'pages/users.html',
                controller: 'UsersController'
            }).
            when('/user/edit/:userId', {
                templateUrl: 'pages/edit/edit-user.html',
                controller: 'EditUserController'
            }).
            when('/user/:userId', {
                templateUrl: 'pages/particular/user.html',
                controller: 'ParticularUserController'
            }).
            when('/user/change-password/:userId', {
                templateUrl: 'pages/edit/change-password.html',
                controller: 'ChangePasswordController'
            }). // In userControllers.js
            otherwise({redirectTo: '/home'});


        /* Registers auth token interceptor, auth token is passed by header as soon as there is an authenticated user */
        $httpProvider.interceptors.push(function ($q, $rootScope, Session) {
            return {
                'request': function (config) {
                    var isRestCall = config.url.indexOf('rest') == 0;
                    if (isRestCall && angular.isDefined(Session.authToken)) {
                        console.log('Add token: '+Session.authToken);
                        config.headers['X-AUTH-TOKEN'] = Session.authToken;
                    }
                    return config || $q.when(config);
                }
            };
        });

        $cookiesProvider.defaults.path = '/pa165';
    }]);

app.run(function ($rootScope, $cookies, Session, USER_ROLES, AuthService) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };

    $rootScope.currentUser = null;
    $rootScope.userRoles = USER_ROLES;
    $rootScope.isAuthenticated = AuthService.isAuthenticated;
    $rootScope.isAuthorized = AuthService.isAuthorized;
    $rootScope.isAuthenticatedUser = AuthService.isAuthenticatedUser;

    $rootScope.logout = AuthService.logout;

    var auth = $cookies.getObject('auth');
    if (auth != undefined) {
        Session.create(auth.userId, auth.role, auth.authToken);
        $rootScope.currentUser = {
            userId: auth.userId,
            loginName: auth.loginName
        };
    }
});

app.service('Session', function (USER_ROLES) {
    this.create = function (userId, userRole, token) {
        console.log('Create session:', userId, userRole);
        this.userId = userId;
        this.userRole = userRole;
        this.authToken = token;
    };
    this.destroy = function () {
        console.log('Destroy session');
        this.userId = null;
        this.userRole = null;
        this.authToken = null;
    };
    this.degradeToUser = function () {
        if (this.userRole == USER_ROLES.admin) {
            this.userRole = USER_ROLES.user;
        }
    };
});

app.factory('AuthService', function ($http, $rootScope, USER_ROLES, Session, $cookies) {
    var authService = {};

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

    authService.logout = function () {
        $rootScope.successAlert = 'User has been logged out.';
        Session.destroy();
        $rootScope.currentUser = null;
        $cookies.remove('auth');
    };

    authService.degradeToUser = function () {
        Session.degradeToUser();

        var auth = $cookies.getObject('auth');
        if (auth != undefined) {
            if (auth.role == USER_ROLES.admin) {
                auth.role = USER_ROLES.user;
                $cookies.putObject('auth', auth);
            }
        }
    };

    authService.isAuthenticatedUser = function(loginName) {
        if (authService.isAuthenticated && $rootScope.currentUser != null) {
            return $rootScope.currentUser.loginName == loginName;
        }
        return false;
    };

    return authService;
});

