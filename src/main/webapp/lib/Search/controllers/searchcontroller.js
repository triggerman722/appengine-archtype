angular.module('SearchModule', ['ngResource']);

angular.module('SearchModule').factory('SearchResource', function($resource) {
			return $resource('/api/search/:query', {}, {
				lookup: {method: 'GET', isArray: true}
			});
		});

angular.module('SearchModule').controller('SearchCtrl', function($scope, $routeParams, $location, SearchResource, $http) {
		if ($routeParams.query)
		{
            $scope.posts = SearchResource.lookup({query: $routeParams.query});
		}
		$scope.find = function() {
			$location.path('/search/'+$scope.query);			
		}
});

angular.module('SearchModule').config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/search/:query', {templateUrl: 'lib/Posts/views/list.html', controller: 'SearchCtrl'})
                ;
    }]);
