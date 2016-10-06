app.controller("BlogIndexController", ["$scope", "$http", function($scope, $http) 
{	
	$scope.doSearch = function()
	{
		$scope.needsChanged = true;
		$scope.searchPage = true;
		$scope.getPage($scope.curPage, $scope.postsPerPage);
	}
	
//	$scope.getFilter = function()
//	{
//		$('#postsDiv').css('visibility', 'hidden');
//		$("#loading").show();
//		window.scrollTo(0, $('#postsDiv').offsetTop + 100);
//		var ulQuery = $scope.searchQuery.toLowerCase().replace('/s', '+');
//		$scope.savedQuery = $scope.searchQuery;
//		
//		var searchUrl = $scope.getUrl + "&q=" + ulQuery;
//		
//		$http.get(searchUrl).success(
//			    function(resp)
//				{
//			    	$scope.loadedPosts = [];
//			    	var page = 1;
//			    	
//					$scope.searchPosts = resp;
//					
//					for (var i = 0; i < $scope.searchPosts.posts.length/$scope.postsPerPage; i++) 
//					{
//						$scope.loadedPosts[i] = [];
//					}
//					
//					for (var j = 0; j < $scope.searchPosts.posts.length/$scope.postsPerPage; j++) 
//					{
//						$scope.loadedPosts[j][0] = page + j;
//						
//						for (var k = 0; k < $scope.postsPerPage; k++) 
//						{
//							if ($scope.posts.posts[k+(j*$scope.postsPerPage)] != null)
//							{
//								$scope.loadedPosts[j][k+1] = $scope.posts.posts[k+(j*$scope.postsPerPage)];							
//							}
//						}
//					}
//					
//					for (var m = 0; m < Math.ceil($scope.posts.total_posts/$scope.postsPerPage); m++) 
//					{
//						$scope.numOfPages[m] = m+1;
//					}
//					
//					if($scope.needsChanged)
//					{
//						$scope.needsChanged = false;
//						$scope.changeView(1);
//					}
//					
//					$('#postsDiv').load();
//					$("#loading").hide();
//					$('#postsDiv').css('visibility', 'visible');
//				}
//			);
//		}
	
	$scope.clearSearch = function()
	{
		$scope.searchPosts.posts = [];
		$scope.searchQuery = "";
		$scope.searchPage = false;
		$scope.needsChanged = true;
		$scope.getPage(1, $scope.postsPerPage);
	}
	
	$scope.getPage = function(page, postsPP)
	{
		$('#postsDiv').css('visibility', 'hidden');
		$("#loading").show();
		window.scrollTo(0, $('#postsDiv').offsetTop + 100);

		$scope.searchPage = false;
		
		$scope.paramPage = page;
		$scope.postsPerPage = postsPP;
		
		var url = $scope.getUrl;
		
		if($scope.author != null && $scope.author > 0){
			url = $scope.getUrl+"&author=" + $scope.author;
		} else if($scope.category != null && sessionStorage.tag > 0){
			url = $scope.getUrl+"&category=" + $scope.category;
		} else if($scope.searchQuery.length > 1) {
			url = $scope.getUrl+"&q=" + $scope.searchQuery;
			$scope.searchPage = true;
		}
		$http.get(url).success(
			    function(resp)
				{
					$scope.posts = resp;

					console.log("Total posts: " + $scope.posts.total_posts);
					
					for (var index = 0; index < $scope.posts.posts.length/postsPP; index++) 
					{
						$scope.loadedPosts[index] = [];
					}

					/*
					 * Ok. This is what this needs to do:
					 * 		--First for loop is setting each page (loadedPosts[i][0] will hold the page number)
					 * 		--The second for is dropping posts into the arrays at each index (loadingPosts[0][1] will be a post and so on...)
					 */
					
					for (var i = 0; i < $scope.posts.posts.length/$scope.postsPerPage; i++) 
					{
						$scope.loadedPosts[i][0] = page + i;
						
						for (var j = 0; j < $scope.postsPerPage; j++) 
						{
							if ($scope.posts.posts[j+(i*$scope.postsPerPage)] != null)
							{
								$scope.loadedPosts[i][j+1] = $scope.posts.posts[j+(i*$scope.postsPerPage)];								
							}
						}
					}
					
					for (var k = 0; k < Math.ceil($scope.posts.total_posts/$scope.postsPerPage); k++) 
					{
						$scope.numOfPages[k] = k+1;
					}
					
					if($scope.needsChanged)
					{
						$scope.needsChanged = false;
						$scope.changeView(1);
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
	
	$scope.changePostsPP = function(postsPP)
	{
		$scope.postsPerPage = postsPP;
		var page = $scope.loadedPosts[0][0];
		
		for (var index = 0; index < $scope.posts.posts.length/postsPP; index++) 
		{
			$scope.loadedPosts[index] = [];
		}
		
		for (var i = 0; i < $scope.posts.posts.length/$scope.postsPerPage; i++) 
		{
			$scope.loadedPosts[i][0] = page + i;
			
			for (var j = 0; j < $scope.postsPerPage; j++) 
			{
				if ($scope.posts.posts[j+(i*$scope.postsPerPage)] != null)
				{
					$scope.loadedPosts[i][j+1] = $scope.posts.posts[j+(i*$scope.postsPerPage)];					
				}
			}
		}
		
		$scope.numOfPages = [];
		
		for (var k = 0; k < Math.ceil($scope.posts.posts.length/$scope.postsPerPage); k++) 
		{
			$scope.numOfPages[k] = k+1;
		}

		$scope.changeView($scope.curPage);
	}

	$scope.changeView = function(page)
	{
		$scope.curPage = page;
		var pageCheck = false;
		var displayIndex;

		console.log("In change view");
		
		if(!$scope.isLoading)	
		{
			$scope.isLoading = true;	
			
			for (var i = 0; i < $scope.loadedPosts.length; i++) 
			{
				if (page === $scope.loadedPosts[i][0]) 
				{
					pageCheck = true;
					displayIndex = i;
					console.log("Display check" + pageCheck);
				}
			}
			
			if(pageCheck)
			{
				$scope.displayPosts = $scope.loadedPosts[displayIndex];
				console.log("Display posts: " + $scope.displayPosts);
				
				/*
				 * Above assigns the posts for the desired page to the displayPosts
				 * Below checks to see if we've reached the end (or beginning) of the pre-loaded posts
				 */
				
				if(displayIndex === $scope.loadedPosts.length | $scope.loadedPosts[displayIndex] == null)		
				{				
					var pageToLoad = page - (MAX_PP/$scope.postsPerPage/2);
					
					if(pageToLoad < 1)
					{
						pageToLoad = 1;
					}
					
					$scope.getPage(pageToLoad, $scope.postsPerPage)
				}
			}
			
			/*
			 *	This else is for when a user clicks a page outside of pre-loaded range
			 *	It makes a get request to get more pages, starting PRIOR to the requested page (for fast loading of prev pages)
			 *	It then calls this method again with the request page.  Yes.  Recursion. 
			 */
			
			else
			{			
				var pageToLoad = page - (MAX_PP/$scope.postsPerPage/2);
				
				if(pageToLoad < 1)
				{
					pageToLoad = 1;
				}
				
				$scope.getPage(pageToLoad, $scope.postsPerPage)
				
				$scope.changeView(page);
			}
			
	        window.scrollTo(0, $('#postsDiv').offsetTop + 100);
			$scope.isLoading = false;
		}
	}
	
 	window.onbeforeunload = function (e) {
 		sessionStorage.clear();
 	};
	
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
	const MAX_PP = 20;
	$scope.numOfPages = [];
	$scope.loadedPosts = [];
	$scope.displayPosts = [];
	$scope.savedQuery = "";
	$scope.searchPage = false;
	$scope.needsChanged = true;
	$scope.curPage = 1;
	$scope.paramPage = 1;
	$scope.postsPerPage = 10;
	$scope.isLoading = false;
	$scope.author = 0;
	$scope.category = sessionStorage.tag;
	$scope.appUrl = "https://dev.pjw6193.tech:7002/revblogs";
	$scope.getUrl = "https://dev.pjw6193.tech:7002/revblogs/api/posts?page=" + $scope.paramPage;
	$scope.getPage($scope.curPage, $scope.postsPerPage);
	$scope.searchQuery = "";
	
	
	
	
	
	
	
	/////////////////////////////
	///// Start Suggestions /////
	/////////////////////////////
	
	$scope.suggestions = [
		{ "displayText":"", "searchQuery":"" },
		{ "displayText":"d1", "searchQuery":"s1" },
		{ "displayText":"d2", "searchQuery":"s2" },
		{ "displayText":"d3", "searchQuery":"s3" },
		{ "displayText":"d4", "searchQuery":"s4" },
		{ "displayText":"d5", "searchQuery":"s5" }
	];
	$scope.maxSuggestions = 6;
	$scope.selectedSuggestionId = 0;
	$scope.selectedSuggestion = $scope.suggestions[0].searchQuery;
	$scope.suggestionsShown = false;
	
	$scope.keyup = function(keyUpEvent) {
		var keyCode = keyUpEvent.which || keyUpEvent.keyCode;
		if ( keyCode === 38 ) {
			$scope.upArrow();
		} else if ( keyCode === 40 ) {
			$scope.downArrow();
		} else if ( keyCode === 13 ) {
			$scope.enterKey();
		} else if ( keyCode === 27 ) {
			$scope.escapeKey();
		} else {
			$scope.unknownKey(keyCode);
		}
	}
	
	//////////////////////////////////
	///// Start Key-Up Callbacks /////
	//////////////////////////////////
	$scope.upArrow = function() {
		if ( $scope.searchQuery.length > 0 ) {
			if ( $scope.selectedSuggestionId <= 0 ) {
				$scope.selectedSuggestionId = 0;
			} else {
				$scope.selectedSuggestionId -= 1;
			}
			$scope.selectSuggestion($scope.selectedSuggestionId);
			$scope.setSuggestionsVisible(true);
		}
	}
	
	$scope.downArrow = function() {
		if ( $scope.searchQuery.length > 0 ) {
			if ( $scope.selectedSuggestionId >= $scope.maxSuggestions-1 ) {
				$scope.selectedSuggestionId = $scope.maxSuggestions-1;
			} else {
				$scope.selectedSuggestionId += 1;
			}
			$scope.selectSuggestion($scope.selectedSuggestionId);
			$scope.setSuggestionsVisible(true);
		}
	}
	
	$scope.enterKey = function() {
		$scope.submitSearch($scope.searchQuery);
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.escapeKey = function() {
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.unknownKey = function(keyCode) {
		$scope.setSuggestionsVisible($scope.searchQuery.length > 0);
	}
	////////////////////////////////
	///// End Key-Up Callbacks /////
	////////////////////////////////
	
	$scope.selectedSuggestionChanged = function() {
		$scope.setSuggestionsVisible(false);
		$scope.setSearchQuery($scope.selectedSuggestion);
		//$scope.submitSearch($scope.selectedSuggestion);
	}
	
	$scope.setSuggestionsVisible = function(visible) {
		if ( !visible )
			$scope.selectedSuggestionId = 0;
		
		$scope.suggestionsShown = !!visible;
	}
	
	$scope.setSearchQuery = function(newText) {
		$scope.searchQuery = newText;
	}
	
	$scope.searchLostFocus = function(focusLostEvent) {
		var itemBeingFocused = '';
		if ( focusLostEvent != null && focusLostEvent.relatedTarget != null ) {
			itemBeingFocused = focusLostEvent.relatedTarget.id;
		}
		
		if ( itemBeingFocused !== 'searchBox' && itemBeingFocused !== 'selections' ) {
			$scope.setSuggestionsVisible(false);
		}
	}
	
	$scope.isSelected = function(suggestion) {
		return $scope.selectedSuggestion === suggestion.searchQuery;
	}
	
	$scope.selectSuggestion = function(indexToSelect) {
		angular.forEach($scope.suggestions, function(suggestion, index) {
			if ( index === indexToSelect && !($scope.isSelected(suggestion)) ) {
				$scope.selectedSuggestion = suggestion.searchQuery;
				$scope.setSearchQuery(suggestion.searchQuery);
			}
		});
	}
	
	$scope.searchQueryChanged = function() {
		console.log('searchQueryChanged');
		var userEnteredSearchQuery = $scope.searchQuery;
		$scope.generateSuggestions(userEnteredSearchQuery);
		$scope.selectedSuggestion = $scope.suggestions[0].searchQuery;
	}
	
	$scope.generateSuggestions = function(userEnteredsearchQuery) {
		console.log('generate');
		//$scope.suggestions = $scope.posts.searchFills;
		$scope.suggestions = [
			{ "displayText":userEnteredSearchQuery + "", "searchQuery":userEnteredSearchQuery + "" },
			{ "displayText":userEnteredSearchQuery + "d1", "searchQuery":userEnteredSearchQuery + "s1" },
			{ "displayText":userEnteredSearchQuery + "d2", "searchQuery":userEnteredSearchQuery + "s2" },
			{ "displayText":userEnteredSearchQuery + "d3", "searchQuery":userEnteredSearchQuery + "s3" },
			{ "displayText":userEnteredSearchQuery + "d4", "searchQuery":userEnteredSearchQuery + "s4" },
			{ "displayText":userEnteredSearchQuery + "d5", "searchQuery":userEnteredSearchQuery + "s5" }
		];
	}
	
	$scope.submitSearch = function(searchQuery) {
		var fullUrl;
		var ulQuery = $scope.searchQuery.toLowerCase();
		$scope.savedQuery = $scope.searchQuery;
		
		fullUrl = $scope.appUrl + "/api/posts/?page=1&per_page=10&q=" + ulQuery;
		
		$http.get(fullUrl).success(
			    function(resp)
				{		
					$scope.suggestions = resp.searchFills;
				});
	}	

}]);