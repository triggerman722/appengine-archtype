angular.module('PeopleModule', ['ngResource']);

angular.module('PeopleModule').factory('PeopleResource', function($resource) {
			return $resource('/api/people/:id', {}, {
				query: {method: 'GET', isArray: true},
				get: {method: 'GET'},
				remove: {method: 'DELETE'},
				edit: {method: 'PUT'},
				add: {method: 'POST'}
			});
		});
angular.module('PeopleModule').factory('ChangepasswordResource', function($resource) {
			return $resource('/api/people/:id/changepassword/', {id:'@id'}, {
				changepassword: {method: 'POST', params:{oldpassword:'@oldpassword',newpassword:'@newpassword',confirmpassword:'@confirmpassword'}}
			});
		});
angular.module('PeopleModule').factory('ProfileResource', function($resource) {
    return $resource('/api/profile/', {}, {
        get: {method: 'GET'}
    });
});                

angular.module('PeopleModule').controller('PeopleCtrl', function($scope, $routeParams, $location, PeopleResource, ChangepasswordResource) {
    if ($routeParams.id) {
        $scope.person = PeopleResource.get({id: $routeParams.id});
    }
    if ($location.path() === '/people') {
        $scope.people = PeopleResource.query();
    }

    $scope.add = function() {
        PeopleResource.add({}, $scope.newPerson, function(data) {
            $location.path('/people');
        });
    };

    $scope.delete = function(id) {
        if (!confirm('Confirm delete')) {
            return;
        }

        PeopleResource.remove({id: id}, {}, function(data) {
            $location.path('/people');
        });
    };

    $scope.save = function() {
        PeopleResource.edit({id: $scope.person.id}, $scope.person, function(data) {
            $location.path('/people');
        });
    };
    $scope.changepassword = function() {
       ChangepasswordResource.changepassword({id: $routeParams.id, oldpassword:$scope.ChangePassword.oldpassword, newpassword:$scope.ChangePassword.newpassword, confirmpassword:$scope.ChangePassword.confirmpassword}, function(data) {

           $location.path('/people');
       });
    };
});
angular.module('PeopleModule').config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/people/add', {templateUrl: 'lib/People/views/add.html', controller: 'PeopleCtrl'})
                .when('/people/edit/:id', {templateUrl: 'lib/People/views/edit.html', controller: 'PeopleCtrl'})
                .when('/people/:id/changepassword', {templateUrl: 'lib/People/views/changepassword.html', controller: 'PeopleCtrl'})
                .when('/people/:id', {templateUrl: 'lib/People/views/display.html', controller: 'PeopleCtrl'})
                .when('/people', {templateUrl: 'lib/People/views/list.html', controller: 'PeopleCtrl'})

                ;
    }]);
