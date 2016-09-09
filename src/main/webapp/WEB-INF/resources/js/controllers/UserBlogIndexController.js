app.controller("UserBlogIndexController", ["$scope", "$http", function($scope, $http) {
	
	$scope.getPage = function(page) {
		$http.get("/revblogs/api/posts?author=" + $scope.userid + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp) {
				$scope.posts = resp;
				$scope.curPage = page;  //current page
				
				var prevPage = $scope.curPage;
				var nextPage = $scope.curPage;
				
				if($scope.curPage > 1) {
					prevPage = $scope.curPage - 1;
				}
				
				if($scope.curPage < $scope.posts.total_pages) {
					nextPage = $scope.curPage + 1;
				}
				$('#postsDiv').load();
				
				$scope.numOfPages = [];
				$scope.numOfPages[0] = 1;
				
				for (var i = 1; i < $scope.posts.total_pages+1; i++)
				{
					$scope.numOfPages[i - 1] = i;
				}
				
				if($scope.curPage < $scope.posts.total_pages) {
					preloadPage(nextPage);
				}
				
				if($scope.curPage > 1) {
					preloadPage(prevPage);
				}
			}
		);
	}
	
	$scope.changeView = function(direction){
		if($scope.isLoading) {
			
		} else {
			if(direction === 1) {
				$scope.posts = $scope.nextPagePosts;
				$scope.curPage = $scope.curPage + 1;
				
				
				$scope.isLoading = true;
				
				preloadPage($scope.curPage - 1);
				preloadPage($scope.curPage + 1);

		        window.scrollTo(0, $('#postsDiv').offsetTop + 100)
			} else {
				$scope.posts = $scope.prevPagePosts;
				$scope.curPage = $scope.curPage - 1;
				
				$scope.isLoading = true;
				preloadPage($scope.curPage - 1);
				preloadPage($scope.curPage + 1);
				$scope.isLoading = false;
				
		        window.scrollTo(0, $('#postsDiv').offsetTop + 100)
			}
		}
	}
	
	function preloadPage(page) {
		$http.get("/revblogs/api/posts?author=" + $scope.userid + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp) {
				if($scope.curPage > page) {
					$scope.prevPagePosts = resp;
				}
				
				if($scope.curPage < page) {
					$scope.nextPagePosts = resp;
				}
			}
		);
	}	
	$scope.userid = $("#userid").val();
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.getPage($scope.curPage);
	//
}]);