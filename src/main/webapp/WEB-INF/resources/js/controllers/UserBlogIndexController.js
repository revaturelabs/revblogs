app.controller("UserBlogIndexController", ["$scope", "$http", function($scope, $http) {
	
	$scope.getPage = function(page, authorid) {
		
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		
		$http.get("/revblogs/api/posts?author=" + $scope.author + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
			function(resp)
			{
				$scope.posts = resp;
				
				$scope.curPage = page;  //current page
				$scope.searchPage = false;
				var prevPage = $scope.curPage;
				var nextPage = $scope.curPage;
				
				if($scope.curPage > 1)
				{
					prevPage = $scope.curPage - 1;
				}
				
				if($scope.curPage < $scope.posts.total_pages)
				{
					nextPage = $scope.curPage + 1;
				}
				
				$scope.numOfPages = [];
				$scope.numOfPages[0] = 1;
				
				for (var i = 1; i < $scope.posts.total_pages+1; i++)
				{
					$scope.numOfPages[i - 1] = i;
				}
				
				if($scope.curPage < $scope.posts.total_pages)
				{
					preloadPage(nextPage, $scope.postsPerPage);
				}
				
				if($scope.curPage > 1)
				{
					preloadPage(prevPage, $scope.postsPerPage);
				}
				$('#postsDiv').load();
				$("#loading").hide();
				$('#postsDiv').css('visibility', 'visible');
			}
		);
	}
	
	$scope.changeView = function(direction)
	{
		if(!$scope.isLoading)	
		{
			if(direction === 1)
			{
				if($scope.curPage < $scope.posts.total_pages)		
				{
					$scope.posts = $scope.nextPagePosts;
					$scope.curPage = $scope.curPage + 1;
					
					$scope.isLoading = true;
					
					preloadPage($scope.curPage - 1, $scope.postsPerPage);
					preloadPage($scope.curPage + 1, $scope.postsPerPage);

			        window.scrollTo(0, $('#postsDiv').offsetTop + 100);
				}
			}
			
			else
			{
				if($scope.curPage > 1)
				{
					$scope.posts = $scope.prevPagePosts;
					$scope.curPage = $scope.curPage - 1;

					$scope.isLoading = true;
					preloadPage($scope.curPage - 1, $scope.postsPerPage);
					preloadPage($scope.curPage + 1, $scope.postsPerPage);
					$scope.isLoading = false;
					
			        window.scrollTo(0, $('#postsDiv').offsetTop + 100)
				}
			}
		}
	}
	
	function preloadPage(page, postsPP) {
		$http.get("/revblogs/api/posts?author=" + $scope.author + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
			function(resp)
			{
				var prevPage = $scope.curPage;
				var nextPage = $scope.curPage;
				
				if($scope.curPage > page)
				{
					$scope.prevPagePosts = resp;
				}
				
				if($scope.curPage < page)
				{
					$scope.nextPagePosts = resp;
					$scope.isLoading = false;
				}
			}
		);
	}	
	
	$scope.author = $("#userid").val();
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.getPage($scope.curPage, $scope.author);
}]);