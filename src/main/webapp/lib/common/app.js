//So what I hate about this is I have to touch this file for every new module.
angular.module('RhinodromeApp', [
    'ngResource',
    'PostsModule',
    'PeopleModule',
    'SecurityModule',
    'SearchModule'

]);

angular.module('RhinodromeApp').config(function($httpProvider) {
    
    $httpProvider.responseInterceptors.push(function($q, $location, $window, $rootScope ) {
        function success(response) {
            //if the rootScope.person is null, need to re-get it.
            //need to get token from cookies, and then get a person via a token.
			var er = $window.localStorage.getItem('authentication_error');
			if (er === null)
	            $rootScope.authentication_error = false;
			else
				$rootScope.authentication_error = true;
				
            $rootScope.authenticated = $window.localStorage.getItem('authenticated');
            $rootScope.personid = $window.localStorage.getItem('personid');
            $rootScope.personname = $window.localStorage.getItem('personname');
            return response;
        }

        function error(response) {
            if (response.status === 401) {
				if ($location.path() === '/login') {
					$window.localStorage.setItem('authentication_error', true);
					$rootScope.authentication_error = true;
				} else {
                	$location.path('/login');
				}
			}
            return $q.reject(response);
        }

        return function(promise) {
            // same as above
            return promise.then(success, error);
        };
    });
});


angular.module('ng').filter('cut', function() {
    return function(value, wordwise, max, tail) {
        if (!value)
            return '';

        max = parseInt(max, 10);
        if (!max)
            return value;
        if (value.length <= max)
            return value;

        value = value.substr(0, max);
        if (wordwise) {
            var lastspace = value.lastIndexOf(' ');
            if (lastspace != -1) {
                value = value.substr(0, lastspace);
            }
        }

        return value + (tail || ' …');
    };
});

