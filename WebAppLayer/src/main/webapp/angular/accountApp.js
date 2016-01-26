/**
 * @author Karel Vaculik
 */

var accountApp = angular.module('accountApp', ['ngRoute', 'ngCookies', 'controllers']);
var controllers = angular.module('controllers', []);

accountApp.constant('AUTH_EVENTS', {
    loginSuccess: 'auth-login-success',
    loginFailed: 'auth-login-failed',
    logoutSuccess: 'auth-logout-success',
    sessionTimeout: 'auth-session-timeout',
    notAuthenticated: 'auth-not-authenticated',
    notAuthorized: 'auth-not-authorized'
});

accountApp.constant('USER_ROLES', {
    admin: 'ADMIN',
    user: 'ORDINARY'
});

accountApp.config(['$cookiesProvider', function ($cookiesProvider) {
    $cookiesProvider.defaults.path = '/pa165';
}]);

accountApp.run(function ($rootScope) {
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

accountApp.factory('AuthService', function ($http, $rootScope, USER_ROLES, $cookies, $window, AUTH_EVENTS) {
    var authService = {};

    authService.login = function (credentials) {
        console.log('Authenticate user with loginName=' + credentials.loginName);
        return $http
            .post('/pa165/rest/users/login', credentials)
            .then(function (response) {
                console.log('User has been authenticated');
                var auth = {};
                auth.userId = response.data.userId;
                auth.loginName = response.data.loginName;
                auth.role = USER_ROLES.user;
                if (response.data.admin) {
                    auth.role = USER_ROLES.admin;
                }
                auth.authToken = response.headers('X-AUTH-TOKEN');

                $cookies.putObject('auth', auth);
                $rootScope.$broadcast(AUTH_EVENTS.loginSuccess);
                $window.location.href = '/pa165/index.jsp';
            }, function (response) {
                console.log("User hasn't been  authenticated");
                $rootScope.warningAlert = 'Authentication has failed.';
                $rootScope.$broadcast(AUTH_EVENTS.loginFailed);
            });
    };

    return authService;
});


controllers.controller('LoginController', function ($scope, $rootScope, AUTH_EVENTS, AuthService, $window) {
    $scope.credentials = {
        loginName: '',
        password: ''
    };
    $scope.login = function (credentials) {
        AuthService.login(credentials);
    };
});

controllers.controller('RegisterController', function($http, $rootScope, $location, $scope, $window) {
    console.log('Register new user controller');
    $scope.sexes = ['MALE', 'FEMALE'];
    $scope.user = {
        name: '',
        sex: $scope.sexes[0],
        type: 'ORDINARY',
        dateOfBirth: '',
        userName: '',
        password: ''
    };
    $scope.retypePassword = '';
    $scope.retypePasswordValid = function() {
        return $scope.user.password == $scope.retypePassword;
    };
    $scope.register = function (user) {
        console.log('Register new user: ' + user.userName);
        
        $scope.user.dateOfBirth.setDate($scope.user.dateOfBirth.getDate() + 1); //HACK to prevent 1 day decrease in conversion
        $scope.user.dateOfBirth = $scope.user.dateOfBirth.toISOString().split('T')[0];//String YYYY-MM-DD
        
        $http.post('/pa165/rest/users/create', user).
            then(function success(response) {
                $rootScope.successAlert = 'New user was registered.';
                $window.location.href = '/pa165/pages/login.html';
            }, function error(response) {
                console.log('Error when register new user');
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured, cannot register new user!';
            });
    };
});
