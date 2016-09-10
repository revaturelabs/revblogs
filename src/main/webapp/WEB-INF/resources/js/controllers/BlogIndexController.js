app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	$scope.getFilter = function()
	{
		for (var i = 0; i < $scope.posts.posts.length; i++) 
		{
			$scope.searchPage = true;
			var ulQuery = $scope.searchQuery.toLowerCase();
			var ulTitle = $scope.posts.posts[i].title.toLowerCase();
			var ulSubtitle = $scope.posts.posts[i].subtitle.toLowerCase();
			var ulContent = $scope.posts.posts[i].content.toLowerCase();
			if (ulTitle.includes(ulQuery) || ulSubtitle.includes(ulQuery) || ulContent.includes(ulContent))
			{
				console.log("found");
				$scope.posts.posts[i].title = "e2a3a746c33617187a3a";
				continue;
			}

			/*if (!($scope.posts[i].subtitle.indexOf($scope.searchQuery) >= 0))
			{
				$scope.posts[i].title = "e2a3a746c33617187a3a";
				continue;
			}

			if (!($scope.posts[i].content.indexOf($scope.searchQuery) >= 0))
			{
				$scope.posts[i].title = "e2a3a746c33617187a3a";
				continue;
			}*/
		}
			
		return false;
	}
	
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

	$scope.appUrl = "http://localhost:7001/revblogs";
	$scope.posts = {
			page: 0,
			prev: null,
			next: null,
			posts:[],
			author: null,
			category: null,
			total_pages: 0,
			per_page: 0,
			total_posts: 0
	};
	$scope.searchQuery = "";
	$scope.searchPage = false;
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.author = 0;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
}]);