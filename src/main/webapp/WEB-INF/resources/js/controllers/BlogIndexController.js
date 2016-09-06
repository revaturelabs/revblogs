app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{
	// Domain DTO mockup
	$scope.posts = {				/* 10 posts per page is default; can be changed by using ?perPage=25 */
		page: 1,					/* current page */
		totalPages: 7,				/* total number of pages */
		totalPosts: 65,				/* total number of posts */
		prev: null,					/* link to previous set of posts */
		next: "/api/posts?page=2",	/* link to next set of posts */
		posts: [					/* array of post objects */
					{
/* post id */			id: 1,
/* post link */			link: "/post/yearMonthDayOfCreation/blogTitle.html",
/* post title */		title: "The Joy of Coding",
/* post subtitle */		subtitle: "40 ways to enhance your productivity whilst coding",
/* post content, plain 
   text (use JS to 
   clamp length) */		content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
/* post tag array */	tags: ["Java", ".NET", "General Programming", "Productivity"],
/* post author
   object */			author: {
   							id: 1,
							name: "Trey McDeane",
/* link to user posts */	link: "/author/1/posts"
						},
/* post date as ms */	postDate: 1472588905064
					},
					{
						id: 2,
						title: "Blah Man Blah",
						subtitle: "This is a thing",
						content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
						tags: ["Java", ".NET", "General Programming", "Productivity"],
						author: {
   							id: 1,
							name: "Trey McDeane",
							link: "/author/1/posts"
						},
						postDate: 1472588905064
					},
			]
		}
	
	$scope.numOfPages = [];
	var totalPages = 10;
	
	for (var i = 0; i < totalPages; i++) 
	{
		$scope.numOfPages[i] = i+1		
	}
	
	$scope.getPage = function(page, postsPP)
	{
		$http.get("/revblogs/api/posts?page=" + page + "&per_page=" + $scope.postsPerPage).success(
		    function(resp)
			{
				console.log("getPage");
				
				$scope.posts = resp;
				
				console.log($scope.posts.total_pages);
				var postsPerPage = 10;
				
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
				
				for (var i = 1; i < $scope.posts.total_pages; i++)
				{
					$scope.numOfPages[i] = i;
				}
				
				if($scope.curPage < $scope.posts.total_pages)
				{
					preloadPage(nextPage, $scope.postsPerPage);
					console.log("Next page preloaded!");
				}
				
				if($scope.curPage > 1)
				{
					preloadPage(prevPage, $scope.postsPerPage);
					console.log("Prev page preloaded!");
				}
				
				$('#postsDiv').load();
			}
		);
	}
	
	function changeView(direction)
	{
		if(direction == 1)
		{
			$scope.posts = $scope.nextPagePosts;
			$scope.curPage = $scope.curPage + 1;
			preloadPage($scope.curPage, $scope.postsPerPage);
			$('#postsDiv').load();
		}
		
		else
		{
			$scope.posts = $scope.prevPagePosts;
			$scope.curPage = $scope.curpage - 1;
			preloadPage($scope.curPage, $scope.postsPerPage);
			$('#postsDiv').load();
		}
	}
	
	function preloadPage(page, postsPP)
	{
		$http.get("/revblogs/api/posts?page=" + page).success(
		    function(resp)
			{
				console.log("getPage");
				
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
				}
			}
		);
	}	

	$scope.curPage = 1;
	$scope.postsPerPage = 10;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
	//
}]);



















