angular.module('PostsModule', ['ngResource']);

angular.module('PostsModule').factory('PostResource', function($resource) {
			return $resource('/api/posts/:id', {}, {
				query: {method: 'GET', isArray: true},
				get: {method: 'GET'},
				remove: {method: 'DELETE'},
				edit: {method: 'PUT'},
				add: {method: 'POST'}
			});
		});
angular.module('PostsModule').factory('CommentResource', function($resource) {
			return $resource('/api/comments/:id', {}, {
				query: {method: 'GET', isArray: true},
				remove: {method: 'DELETE'},
				edit: {method: 'PUT'},
				add: {method: 'POST'}
			});
		});

angular.module('PostsModule').factory('LikeResource', function($resource) {
			return $resource('/api/likes/:id', {}, {
				query: {method: 'GET', isArray: true},
				remove: {method: 'DELETE'},
				add: {method: 'POST'}
			});
		});

angular.module('PostsModule').controller('PostsCtrl', function($scope, $routeParams, $location,
                                        PostResource, CommentResource, LikeResource) {
        if ($routeParams.id && $routeParams.id!=null)
        {
            $scope.post = PostResource.get({id: $routeParams.id}, function(response){

            	LikeResource.get({id:$routeParams.id}, function(response){
            	    //http 200
            	    $scope.ilikethis = true;
            	}, function (response){
            	    //http 404
            	    $scope.ilikethis = false;
            	});
			});
        }
        if ($location.path() === '/posts')
        {
            $scope.posts = PostResource.query();
        }
		if ($location.path()=== '/posts/add')
		{

		}

        $scope.add = function()
        {
			$scope.newPost.latitude = $scope.latitude;
			$scope.newPost.longitude = $scope.longitude;
            PostResource.add({}, $scope.newPost, function(data)
            {
                $location.path('/posts');
            });
        };
        $scope.save = function()
        {
            PostResource.edit(
                    {
                        id: $scope.post.id
                    }, $scope.post, function(data)
            {
                $location.path('/posts');
            });
        };
        $scope.delete = function(id)
        {
            if (!confirm('Confirm delete'))
            {
                return;
            }

            PostResource.remove({id: id}, {}, function(data)
            {
                $location.path('/posts');
            });
        };

        //Comment handling

        $scope.addComment = function(id)
        {
            $scope.newComment.entityid = id;
            CommentResource.add({}, $scope.newComment, function()
            {
                $location.path('/posts/'+id);
            });
        };
        $scope.deleteComment = function(id)
        {
            if (!confirm('Confirm delete'))
            {
                return;
            }
            CommentResource.remove({id: id}, {}, function(data)
            {
                $location.path('/posts');
            });
        };
        $scope.editComment = function()
        {
            CommentResource.edit(
                    {
                        id: $scope.comment.id
                    }, $scope.comment, function(data)
            {
                $location.path('/posts');
            });
        };


        $scope.liketoggle = function(id)
        {
            if ($scope.ilikethis) {
                LikeResource.remove({id:id});
            } else {
                var thisLike = {"entityid": id};
                LikeResource.add({}, thisLike);
            }
            $scope.ilikethis = !$scope.ilikethis;
        };
		
});

angular.module('PostsModule').config(['$routeProvider', function($routeProvider) {
        $routeProvider.when('/posts/add', {templateUrl: 'lib/Posts/views/add.html', controller: 'PostsCtrl'})
                .when('/posts/edit/:id', {templateUrl: 'lib/Posts/views/edit.html', controller: 'PostsCtrl'})
                .when('/posts/:id', {templateUrl: 'lib/Posts/views/display.html', controller: 'PostsCtrl'})
                .when('/posts/', {templateUrl: 'lib/Posts/views/list.html', controller: 'PostsCtrl'})
                .when('/posts', {templateUrl: 'lib/Posts/views/list.html', controller: 'PostsCtrl'})
      
                ;
    }]);
