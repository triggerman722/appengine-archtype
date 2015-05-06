angular.module('SecurityModule', ['ngResource']);

angular.module('SecurityModule').factory('SecurityResource', function($resource) {
    return $resource('/login/', {}, {
        signin: {method: 'POST'}
    });
});

angular.module('SecurityModule').factory('JoinResource', function($resource) {
    return $resource('/join/', {}, {
        joinup: {method: 'POST'}
    });
});

angular.module('SecurityModule').factory('LogoutResource', function($resource) {
    return $resource('/logout/', {}, {
        signout: {method: 'GET'}
    });
});


angular.module('SecurityModule').controller('SecurityCtrl', function($rootScope, $scope, $window, $routeParams, $location, JoinResource, SecurityResource, LogoutResource, $http, PeopleResource) {
    

	if ($location.path() == '/login') {

	    $window.localStorage.removeItem('authenticated');
	    $window.localStorage.removeItem('personid');
	    $window.localStorage.removeItem('personname');

		$rootScope.personid = "";
		$rootScope.personname = "";
		$rootScope.authenticated = false;
	}

    $scope.login = function() {
        SecurityResource.signin({}, $scope.credential, function success(data) {

			$window.localStorage.removeItem('authentication_error');
            
            $window.localStorage.setItem('authenticated', true);
            PeopleResource.get({id: data.personId}, function success(person) {
		        $window.localStorage.setItem('personid', person.id);
		        $window.localStorage.setItem('personname', person.name);
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
