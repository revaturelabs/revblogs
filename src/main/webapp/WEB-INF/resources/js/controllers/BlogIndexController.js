app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	$scope.appUrl = "http://localhost:7001/revblogs";
	
	$scope.getPage = function(page, postsPP)
	{
		$http.get($scope.appUrl+"/api/posts?page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp)
			{
				$scope.posts = resp;
				
				$scope.curPage = page;  //current page
				
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
				
				for (var i = 1; i < $scope.posts.total_pages; i++)
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
			}
		);
	}
	
	$scope.changeView = function(direction)
	{
		if(!$scope.isLoading)	
		{
			console.log("ChangeView " + direction);
			console.log($scope.curPage);
			
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
	
	function preloadPage(page, postsPP)
	{
		console.log("Pre-loading");
		$http.get($scope.appUrl+"/api/posts?page=" + page).success(
		    function(resp)
			{
				var prevPage = $scope.curPage;
				console.log(prevPage);
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

	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
}]);