$( function() {
  var availableTags = $scope.autofill;
   $scope.searchText.autocomplete({
   source: availableTags
  });
} );

app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	$scope.getFilter = function()
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		var fullUrl;
		var ulQuery = $scope.searchText.toLowerCase();
		$scope.savedQuery = $scope.searchText;
		
		fullUrl = $scope.appUrl + "/api/posts/?page=1&per_page=10&q=" + ulQuery;
		
		$http.get(fullUrl).success(
			    function(resp)
				{
					$scope.searchPosts = resp;
					
					$scope.curPage = 1;  //current page
					$scope.searchPage = true;
					var prevPage = $scope.curPage;
					var nextPage = $scope.curPage;
					
					if($scope.curPage > 1)
					{
						prevPage = $scope.curPage - 1;
					}
					
					if($scope.curPage < $scope.searchPosts.total_pages)
					{
						nextPage = $scope.curPage + 1;
					}
					
					$scope.numOfPages = [];
					$scope.numOfPages[0] = 1;
					
					for (var i = 1; i < $scope.searchPosts.total_pages+1; i++)
					{
						$scope.numOfPages[i - 1] = i;
					}
					
					if($scope.curPage < $scope.searchPosts.total_pages)
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
	
	$scope.clearSearch = function()
	{
		$scope.searchPosts.posts = [];
		$scope.searchPage = false;
	}
	
	$scope.getPage = function(page, postsPP)
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
		var fullUrl;
		if($scope.author != null && $scope.author > 0){
			fullUrl = $scope.appUrl+"/api/posts?author=" + $scope.author + "&page=" + page + "&per_page=" + $scope.postsPerPage;
		} else if($scope.category != null && sessionStorage.tag > 0){
			fullUrl = $scope.appUrl+"/api/posts?category=" + $scope.category + "&page=" + page + "&per_page=" + $scope.postsPerPage;
		} else if($scope.searchText.length > 1) {
			fullUrl = $scope.appUrl+"/api/posts?page=" + page + "&per_page=" + $scope.postsPerPage + "&q=" + $scope.searchText;
		} else {
			fullUrl = $scope.appUrl+"/api/posts?page=" + page + "&per_page=" + $scope.postsPerPage;
		}
		$http.get(fullUrl).success(
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
		
	$scope.getPageWithAuthor = function(page, authorid)
	{
		$scope.author = authorid;
		$scope.getPage(page,$scope.postsPerPage);
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
	
	function preloadPage(page, postsPP)
	{
		var fullUrl;
		if($scope.author != null && $scope.author > 0){
			fullUrl = $scope.appUrl+"/api/posts?author=" + $scope.author + "&page=" + page + "&per_page=" + postsPP;
		} else if($scope.category != null && $scope.category > 0){
			fullUrl = $scope.appUrl+"/api/posts?category=" + $scope.category + "&page=" + page + "&per_page=" + postsPP;
		} else {
			fullUrl = $scope.appUrl+"/api/posts?page=" + page + "&per_page=" + postsPP;
		}
		
		$http.get(fullUrl).success(
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
	
	window.onbeforeunload = function (e) {
        sessionStorage.clear();
	};
	
	$scope.appUrl = "https://dev.pjw6193.tech:7002/revblogs";
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
	$scope.searchPosts = {
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
	$scope.searchText = "";
	$scope.savedQuery = "";
	$scope.searchPage = false;
	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.author = 0;
	$scope.category = sessionStorage.tag;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
	
	//////////////////////////////////////////////////////////////////
	///////////////////AUTO FILL SEARCHING///////////////////
   //////////////////////////////////////////////////////////////////
}]);