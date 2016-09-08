app.controller("UserBlogIndexController", ["$scope", "$http", function($scope, $http) {
	
	$scope.getPage = function(page) {
		$http.get("/revblogs/api/posts?author=" + $scope.userid + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp) {
				$scope.posts = resp;
				console.log($scope.posts);
				$scope.curPage = page;  //current page
				
				var prevPage = $scope.curPage;
				var nextPage = $scope.curPage;
				
				if($scope.curPage > 1) {
					prevPage = $scope.curPage - 1;
					console.log("Previous page: " + prevPage);
				}
				
				if($scope.curPage < $scope.posts.total_pages) {
					console.log("Current page: " + $scope.curPage);
					nextPage = $scope.curPage + 1;
					console.log("Next page: " + nextPage);
				}
				
				$('#postsDiv').load();
				
				$scope.numOfPages = [];
				
				for (var i = 1; i < $scope.posts.total_pages; i++) {
					$scope.numOfPages[i] = i;
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
			console.log("HEY! I'm loading!");
		} else {
			console.log("ChangeView " + direction);
			console.log($scope.curPage);
			if(direction == 1) {
				$scope.posts = $scope.nextPagePosts;
				$scope.curPage = $scope.curPage + 1;
				
				console.log("Forward direction, curPage = " + $scope.curPage);
				
				$scope.isLoading = true;
				console.log("Loading? " + $scope.isLoading);
				
				preloadPage($scope.curPage - 1);
				preloadPage($scope.curPage + 1);

				//$anchorScroll($('#postsDiv'));
		        window.scrollTo(0, $('#postsDiv').offsetTop + 100)
			} else {
				if($scope.curPage == 1) {
					
				}
				
				else {
					$scope.posts = $scope.prevPagePosts;
					$scope.curPage = $scope.curPage - 1;
					
					console.log("Reverse direction, curPage = " + $scope.curPage);

					$scope.isLoading = true;
					preloadPage($scope.curPage - 1);
					preloadPage($scope.curPage + 1);
					$scope.isLoading = false;
					
					//$anchorScroll($('#postsDiv'));
			        window.scrollTo(0, $('#postsDiv').offsetTop + 100)
				}
			}
		}
	}
	
	function preloadPage(page) {
		$http.get("/revblogs/api/posts?author=" + $scope.userid + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp) {
				var prevPage = $scope.curPage;
				console.log(prevPage);
				var nextPage = $scope.curPage;
				
				if($scope.curPage > page) {
					console.log("Pre-loading prevPage = " + page)
					$scope.prevPagePosts = resp;
				}
				
				if($scope.curPage < page) {
					console.log("Pre-loading nextPage = " + page)
					$scope.nextPagePosts = resp;
					$scope.isLoading = false;
				}
			}
		);
	}	
	$scope.userid = $("#userid").val();
	console.log($scope.userid);
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.getPage($scope.curPage);
	//
}]);