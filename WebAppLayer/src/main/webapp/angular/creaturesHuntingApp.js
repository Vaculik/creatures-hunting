
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

app.constant('TYPES', {
    creatureTypes: ['VAMPIRE', 'BEAST', 'UNDEAD']
});


app.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/home', {templateUrl: 'pages/home.html'}).
        when('/creatures/:viewType', {templateUrl: 'pages/creatures.html', controller: 'CreaturesController'}).
        when('/creature/new', {templateUrl: 'pages/new/new-creature.html', controller: 'NewCreatureController'}).
        when('/creature/:creatureId', {
            templateUrl: 'pages/particular/creature.html',
            controller: 'ParticularCreatureController'}).
        when('/creature/:creatureId/edit', {
            templateUrl: 'pages/edit/edit-creature.html',
            controller: 'EditCreatureController'}).
        when('/weapons', {templateUrl: 'pages/weapons.html', controller: 'WeaponsController'}).
        when('/weapons/new', {templateUrl: 'pages/new/new-weapon.html', controller: 'NewWeaponController'}).
        when('/efficiency/new/:weaponId', {templateUrl: 'pages/new/new-efficiency.html', controller: 'NewWeaponEfficiencyController'}).//In WeaponControllers
        when('/weapons/edit/:weaponId', {templateUrl: 'pages/edit/edit-weapon.html', controller: 'EditWeaponController'}).
        when('/weapons/:weaponId', {templateUrl: 'pages/particular/weapon.html', controller: 'ParticularWeaponController'}).
        when('/areas', {templateUrl: 'pages/areas.html', controller: 'AreasController'}).
        when('/areas/nocreature', {templateUrl: 'pages/areas.html', controller: 'NoCreatureAreaController'}).
        when('/areas/anycreature', {templateUrl: 'pages/areas.html', controller: 'AnyCreatureAreaController'}).
        when('/areas/mostcreatures', {templateUrl: 'pages/areas.html', controller: 'MostCreaturesAreaController'}).
        when('/areas/fewestcreatures', {templateUrl: 'pages/areas.html', controller: 'FewestCreaturesAreaController'}).
        when('/areas/new', {templateUrl: 'pages/new/new-area.html', controller: 'NewAreaController'}).
        when('/areas/:areaId/edit', {
            templateUrl: 'pages/edit/edit-area.html',
            controller: 'EditAreaController'}).
        when('/area/:areaId', {
            templateUrl: 'pages/particular/area.html',
            controller: 'ParticularAreaController'}).
        when('/login', {templateUrl: 'pages/login.html', controller: 'LoginController'}).
        when('/users/:viewType', {templateUrl: 'pages/users.html', controller: 'UsersController'}).
        when('/user/new', {templateUrl: 'pages/new/new-user.html', controller: 'NewUserController'}).
        when('/user/edit/:userId', {templateUrl: 'pages/edit/edit-user.html', controller: 'EditUserController'}).
        when('/user/:userId', {templateUrl: 'pages/particular/user.html', controller: 'ParticularUserController'}).
        otherwise({redirectTo: '/home'});
}]);

