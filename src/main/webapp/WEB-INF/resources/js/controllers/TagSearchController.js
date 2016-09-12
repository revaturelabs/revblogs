app.controller("TagSearchController", ["$scope", function($scope) {	
	$scope.getPageWithTagsFromBlogPost = function(tagid) {
		sessionStorage.tag = tagid;
		window.location = "all-blogs";
	}
}]);