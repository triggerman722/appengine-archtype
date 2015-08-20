angular.module('SecurityModule', ['ngResource']);

angular.module('SecurityModule').factory('SecurityResource', function($resource) {
    return $resource('/api/login/', {}, {
        signin: {method: 'POST', params:{username:'@username',password:'@password'}}
    });
});

angular.module('SecurityModule').factory('JoinResource', function($resource) {
    return $resource('/join/', {}, {
        joinup: {method: 'POST'}
    });
});

angular.module('SecurityModule').factory('LogoutResource', function($resource) {
    return $resource('/api/logout/', {}, {
        signout: {method: 'GET'}
    });
});


angular.module('SecurityModule').controller('SecurityCtrl', function($rootScope, $scope, $window,
                    $routeParams, $location, JoinResource, SecurityResource,
                    LogoutResource, $http, PeopleResource, ProfileResource) {
    

	if ($location.path() == '/login') {

	    $window.localStorage.removeItem('authenticated');
	    $window.localStorage.removeItem('personid');
	    $window.localStorage.removeItem('personname');

		$rootScope.personid = "";
		$rootScope.personname = "";
		$rootScope.authenticated = false;
	}

    $scope.login = function() {
        SecurityResource.signin({username:$scope.credential.username,password:$scope.credential.password}, function success(data) {

			$window.localStorage.removeItem('authentication_error');
            $window.localStorage.setItem('authenticated', true);
            //TODO: Call /profile...the below will not work because the response will not have a personid
            ProfileResource.get({}, function success(profile) {
                $window.localStorage.setItem('personid', profile.id_str);
            	$window.localStorage.setItem('personname', profile.name);
            	$rootScope.personid = $window.localStorage.getItem('personid');
                $rootScope.personname = $window.localStorage.getItem('personname');
            });
            
            
            $location.path('/posts');
        }, function error(data) {
            // error
            
            $window.localStorage.removeItem('authenticated');
            $window.localStorage.setItem('authentication_error', true);
            $location.path('/login');
        });
    };
    $scope.join = function() {
        JoinResource.joinup({}, $scope.newPerson, function(data) {
            $location.path('/login');
        });
    };
    $scope.logout = function() {
        LogoutResource.signout({}, function(data) {
            $location.path('/login');
        });
    };

});

angular.module('SecurityModule').config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/login', {templateUrl: 'lib/Security/views/login.html', controller: 'SecurityCtrl'})
                .when('/join', {templateUrl: 'lib/Security/views/join.html', controller: 'SecurityCtrl'})
                ;
    }]);
