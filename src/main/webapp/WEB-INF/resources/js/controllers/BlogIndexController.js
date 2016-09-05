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
		$http.get("/revblogs/api/posts?page=" + page).success(
		    function(resp)
			{
				console.log("getPage");
				var postsPerPage = 10;
				var curPage = page;  //current page
				
				var prevPage = curPage;
				var nextPage = curPage;
				
				if(curPage > 1)
				{
					prevPage = curPage - 1;
				}
				
				if(curPage < tPages)
				{
					nextPage = curPage + 1;
				}
				
				$scope.posts = resp;
				
				$scope.numOfPages = [];
				
				for (var i = 0; i < $scope.posts.total_pages; i++)
				{
					$scope.numOfPages[i] = i;
				}
				
				/*$scope.postPage = 
				{									 10 posts per page is default; can be changed by using ?perPage=25 
						page: page,					 current page 
						totalPages: tPages,	 total number of pages 
						totalPosts: tPages,	 total number of posts 
						prev: prevPage,				 link to previous set of posts 
						next: nextPage,				 link to next set of posts 
						posts: resp.postsList
				};*/
				
				if(curPage < tPages)
				{
					preloadPage(nextPage);
					console.log("Next page preloaded!");
				}
				
				if(curPage > 1)
				{
					preloadPage(prevPage);
					console.log("Prev page preloaded!");
				}
			}
		);
	}
	
	function changeView(direction)
	{
		if(direction == 1)
		{
			$scope.posts = $scope.nextPagePosts;
			$scope.curPage++;
			preloadPage($scope.curPage, $scope.postsPerPage);
		}
		
		else
		{
			$scope.posts = $scope.prevPagePosts;
			$scope.curPage--;
			preloadPage($scope.curPage, $scope.postsPerPage);
		}
	}
	
	function preloadPage(page, postsPP)
	{
		$http.get("/revblogs/api/posts?page=" + page).success(
		    function(resp)
			{
				console.log("getPage");
				//var postsPerPage = postsPP;  //postsPerPage, if implemented will be tacked onto url
				var postsPerPage = 10;
				var postsToDisplay = resp.posts;
				var tPages = resp.tPages;  //totalPages
				var tPosts = resp.tPosts;  //totalPosts
				var curPage = page;
				
				var prevPage = curPage;
				console.log(prevPage);
				var nextPage = curPage;
				
				if($scope.curPage > page)
				{
					$scope.prevPagePosts = resp;
				}
				
				if(curPage < page)
				{
					$scope.nextPagePosts = resp;
				}
			}
		);
	}	
}]);



















