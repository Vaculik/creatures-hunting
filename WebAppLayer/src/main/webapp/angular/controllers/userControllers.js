var usersOfType = function (type, $http, $scope) {
    console.log('GET users of type=' + type);
    $http.get('rest/users/type/' + type).
        then(function (response) {
            $scope.users = response.data['_embedded']['users'];
        });
};

var usersOfSex = function (sex, $http, $scope) {
    console.log('GET users of type=' + sex);
    $http.get('rest/users/sex/' + sex).
        then(function (response) {
            $scope.users = response.data['_embedded']['users'];
        });
};

controllers.controller('UsersController', function ($http, $routeParams, $scope) {
    var viewType = $routeParams.viewType;
    console.log('GET USERS request viewType=' + viewType);
    if (viewType == 'all') {
        $http.get('rest/users/?view=' + viewType).
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

controllers.controller('ParticularUserController', function ($http, $rootScope, $route, $location, $routeParams, $scope, AuthService) {
    var userId = $routeParams.userId;
    console.log('GET particular user with id=' + userId);
    $http.get('rest/users/' + userId).
        then(function (response) {
            $scope.user = response.data;
            console.log($scope.user.name + " loaded");
        },
        function error(response) {
            $rootScope.warningAlert = 'Problem occured when loading user ' + response.data.message;
        });

    $scope.makeAdmin = function (id) {
        console.log('Promote user with id=' + id + ' to admin.');
        $http.post('rest/users/make-admin/' + id).
            then(function success(response) {
                console.log('User with id=' + id + ' has been promoted to admin.');
                $rootScope.successAlertToDisplay = 'User has been promoted to admin';
                $route.reload();
            }, function error(response) {
                console.log('Error when promoting user with id=' + id + ' to admin.');
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured when promoting user to admin!';
            })
    };

    $scope.delete = function (id) {
        console.log('Delete user with id=' + id);
        $http.delete('rest/users/' + id).
            then(function success(response) {
                console.log('User with id=' + id + ' was deleted.');
                AuthService.logout();
                $rootScope.successAlertToDisplay = 'User was deleted';
                $location.path('/users/all');
            }, function error(response) {
                console.log('Error when deleting user with id=' + id);
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured when deleting user!';
            });
    };
});


controllers.controller('EditUserController', function ($http, $routeParams, $route, $rootScope, $location,
                                                       $scope, AuthService) {
    var userId = $routeParams.userId;
    $scope.types = ['ADMIN', 'ORDINARY'];
    $scope.sexes = ['MALE', 'FEMALE'];
    console.log('GET user by id=' + userId);
    $http.get('rest/users/' + userId).
        then(function (response) {
            $scope.user = response.data;
            $scope.userName = $scope.user.userName;
            $scope.user.dateOfBirth = new Date($scope.user.dateOfBirth);
        },
        function error(response) {
            $rootScope.warningAlert = 'Problem occured when loading user ' + response.data.message;
        });

    $scope.edit = function (user) {
        console.log("EDIT user " + user.name);
        $http.post('rest/users/edit/' + user.id, user).then(function (response) {//Request successful
            $rootScope.successAlertToDisplay = 'User was changed.';
            $location.path('/user/' + user.id);
        }, function (response) {//Request failed
            console.log("EDIT user failed");
            console.log(response);
            $rootScope.errorAlert = "User could not be edited.";
        });
    };

    $scope.makeUser = function (id) {
        console.log('Degrade user with id=' + id + ' from admin to user.');
        $http.post('rest/users/make-user/' + id).
            then(function success(response) {
                console.log('User with id=' + id + ' has been degraded to user.');
                $rootScope.successAlertToDisplay = 'User has been degraded to user';
                AuthService.degradeToUser();
                $route.reload();
            }, function error(response) {
                console.log('Error when degrading user with id=' + id + ' to user.');
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured when degrading user from admin to user!';
            })
    };
});

controllers.controller('ChangePasswordController', function($http, $rootScope, $location, $scope) {
    console.log('Change password controller');
    $scope.changePasswordDTO = {
        userId: $rootScope.currentUser.userId,
        userName: $rootScope.currentUser.loginName,
        originalPassword: '',
        newPassword: ''
    };
    $scope.retypePassword = '';
    $scope.retypePasswordValid = function() {
        return $scope.changePasswordDTO.newPassword == $scope.retypePassword;
    };
    $scope.changePassword = function (changePasswordDTO) {
        console.log(changePasswordDTO);
        console.log('Change password of user: ' + changePasswordDTO.userName);
        $http.post('rest/users/change-password', changePasswordDTO).
            then(function success(response) {
                $rootScope.successAlertToDisplay = 'Password has been changed.';
                $location.path('user/' + changePasswordDTO.userId)
            }, function error(response) {
                console.log('Error when changing password');
                console.log(response);
                $rootScope.errorAlert = 'Problem has occured, cannot change password!';
            });
    };
});