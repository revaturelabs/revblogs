/**
 * 
 */

var enhancedSearchModule = angular.module('enhancedSearch', []);

enhancedSearchModule.controller('enhancedSearchCtrl', function($scope, $http) {
	
	$scope.searchQuery = '';
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
		$scope.setsearchQuery($scope.selectedSuggestion);
		$scope.submitSearch($scope.selectedSuggestion);
	}
	
	$scope.setSuggestionsVisible = function(visible) {
		if ( !visible )
			$scope.selectedSuggestionId = 0;
		
		$scope.suggestionsShown = !!visible;
	}
	
	$scope.setsearchQuery = function(newText) {
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
				$scope.setsearchQuery(suggestion.searchQuery);
			}
		});
	}
	
	$scope.searchQueryChanged = function() {
		var userEnteredsearchQuery = $scope.searchQuery;
		$scope.generateSuggestions(userEnteredsearchQuery);
		$scope.selectedSuggestion = $scope.suggestions[0].searchQuery;
	}
	
	$scope.generateSuggestions = function(userEnteredsearchQuery) {
		//$scope.suggestions = $scope.posts.searchFills;
		$scope.suggestions = [
			{ "displayText":userEnteredsearchQuery + "", "searchQuery":userEnteredsearchQuery + "" },
			{ "displayText":userEnteredsearchQuery + "d1", "searchQuery":userEnteredsearchQuery + "s1" },
			{ "displayText":userEnteredsearchQuery + "d2", "searchQuery":userEnteredsearchQuery + "s2" },
			{ "displayText":userEnteredsearchQuery + "d3", "searchQuery":userEnteredsearchQuery + "s3" },
			{ "displayText":userEnteredsearchQuery + "d4", "searchQuery":userEnteredsearchQuery + "s4" },
			{ "displayText":userEnteredsearchQuery + "d5", "searchQuery":userEnteredsearchQuery + "s5" }
		];
	}
	
	$scope.submitSearch = function(searchQuery) 
	{
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
});
