angular.module('PostsModule', ['ngResource']);

angular.module('PostsModule').factory('PostResource', function($resource) {
			return $resource('/posts/:id', {}, {
				query: {method: 'GET', isArray: true},
				get: {method: 'GET'},
				remove: {method: 'DELETE'},
				edit: {method: 'PUT'},
				add: {method: 'POST'}
			});
		});
angular.module('PostsModule').factory('CommentResource', function($resource) {
			return $resource('/comments/:id', {}, {
				query: {method: 'GET', isArray: true},
				remove: {method: 'DELETE'},
				edit: {method: 'PUT'},
				add: {method: 'POST'}
			});
		});

angular.module('PostsModule').factory('LikeResource', function($resource) {
			return $resource('/likes/:id', {}, {
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
				var myLatlng = new google.maps.LatLng(response.latitude,response.longitude);
				var mapProp = {
					center: myLatlng,
					zoom:5,
					mapTypeId:google.maps.MapTypeId.ROADMAP
				};
				var map=new google.maps.Map(document.getElementById("googleDisplayMap"),mapProp);				
				var marker = new google.maps.Marker({
                	position: myLatlng,
                	map: map
            	}); 
			});
        }
        if ($location.path() === '/posts')
        {
            $scope.posts = PostResource.query();
        }
		if ($location.path()=== '/posts/add')
		{
			var mapProp = {
				center:new google.maps.LatLng(51.508742,-0.120850),
				zoom:5,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			};
			var map=new google.maps.Map(document.getElementById("googleAddMap"),mapProp);
			google.maps.event.addListener(map, 'click', function(event){
				$scope.latitude = event.latLng.lat();
				$scope.longitude = event.latLng.lng();
				var marker = new google.maps.Marker({
                	position: event.latLng, 
                	map: map
            	});       
			});
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

        Scope.addComment = function(id)
        {
            $scope.newComment.entityid = id;
            CommentResource.add({}, $scope.newComment, function()
            {
                $location.path('/posts/'+id);
            });
        };
        Scope.deleteComment = function(id)
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
        // Like mgmt
        Scope.addLike = function(id)
        {
            $scope.newLike.entityid = id;
            LikeResource.add({}, $scope.newLike, function()
            {
                $location.path('/posts/'+id);
            });
        };
        Scope.deleteLike = function(id)
        {
            if (!confirm('Confirm unlike'))
            {
                return;
            }
            LikeResource.remove({id: id}, {}, function(data)
            {
                $location.path('/posts');
            });
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
