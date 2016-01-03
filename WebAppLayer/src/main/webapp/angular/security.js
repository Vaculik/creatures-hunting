/**
 * Created by vaculik on 18.12.15.
 */

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
        console.log('Authenticate user with loginName=' + credentials.loginName);
        return $http
            .post('rest/users/login', credentials)
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
    AuthService.login({loginName: 'Karel', password: 'admin'});
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