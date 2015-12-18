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