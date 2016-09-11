app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	$scope.getFilter = function()
	{
		var ulQuery = $scope.searchQuery.toLowerCase();
		console.log(ulQuery);
		
		$scope.searchPosts = $scope.posts;
		
		for (var i = 0; i < $scope.searchPosts.posts.length; i++) 
		{
			$scope.searchPage = true;
			var ulTitle = $scope.searchPosts.posts[i].title.toLowerCase();
			console.log(ulTitle);
			if (!ulTitle.includes(ulQuery))
			{
				console.log("found");
				$scope.searchPosts.posts[i].title = "e2a3a746c33617187a3a";
				continue;
			}
		}
			
		return false;
	}
	
	$scope.getPage = function(page, postsPP)
	{
		if($scope.author != null){
			var fullUrl = $scope.appUrl+"/api/posts?author=" + $scope.author + "&page=" + page + "&per_page=" + $scope.postsPerPage;
		} else {
			var fullUrl = $scope.appUrl+"/api/posts?page=" + page + "&per_page=" + $scope.postsPerPage;
		}
		$http.get(fullUrl).success(
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
	
	$scope.getPageWithAuthor = function(page, authorid)
	{
		$http.get($scope.appUrl+"/api/posts?author=" + authorid + "&page=" + page + "&per_page=" + $scope.postsPerPage).success(
			function(resp)
			{
				$scope.author = authorid;
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
					preloadPageWithAuthor(nextPage, authorid, $scope.postsPerPage);
				}
				
				if($scope.curPage > 1)
				{
					preloadPageWithAuthor(prevPage, authorid, $scope.postsPerPage);
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
		
		$http.get($scope.appUrl+"/api/posts?page=" + page  + "&per_page" + postsPP).success(
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
	

	$scope.appUrl = "https://localhost:7002/revblogs";
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