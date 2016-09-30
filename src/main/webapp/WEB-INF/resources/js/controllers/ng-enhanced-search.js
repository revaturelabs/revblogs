var enhancedSearchModule = angular.module('enhancedSearch', []);

enhancedSearchModule.controller('enhancedSearchCtrl', function($scope, $http) {
	
	$scope.searchText = '';
	$scope.suggestions = [
		{ "displayText":"", "searchText":"" },
		{ "displayText":"d1", "searchText":"s1" },
		{ "displayText":"d2", "searchText":"s2" },
		{ "displayText":"d3", "searchText":"s3" },
		{ "displayText":"d4", "searchText":"s4" },
		{ "displayText":"d5", "searchText":"s5" }
	];
	$scope.maxSuggestions = 6;
	$scope.selectedSuggestionId = 0;
	$scope.selectedSuggestion = $scope.suggestions[0].searchText;
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
		if ( $scope.searchText.length > 0 ) {
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
		if ( $scope.searchText.length > 0 ) {
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
		$scope.submitSearch($scope.searchText);
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.escapeKey = function() {
		$scope.setSuggestionsVisible(false);
	}
	
	$scope.unknownKey = function(keyCode) {
		$scope.setSuggestionsVisible($scope.searchText.length > 0);
	}
	////////////////////////////////
	///// End Key-Up Callbacks /////
	////////////////////////////////
	
	$scope.selectedSuggestionChanged = function() {
		$scope.setSuggestionsVisible(false);
		$scope.setSearchText($scope.selectedSuggestion);
		$scope.submitSearch($scope.selectedSuggestion);
	}
	
	$scope.setSuggestionsVisible = function(visible) {
		if ( !visible )
			$scope.selectedSuggestionId = 0;
		
		$scope.suggestionsShown = !!visible;
	}
	
	$scope.setSearchText = function(newText) {
		$scope.searchText = newText;
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
		return $scope.selectedSuggestion === suggestion.searchText;
	}
	
	$scope.selectSuggestion = function(indexToSelect) {
		angular.forEach($scope.suggestions, function(suggestion, index) {
			if ( index === indexToSelect && !($scope.isSelected(suggestion)) ) {
				$scope.selectedSuggestion = suggestion.searchText;
				$scope.setSearchText(suggestion.searchText);
			}
		});
	}
	
	$scope.searchTextChanged = function() {
		var userEnteredSearchText = $scope.searchText;
		$scope.generateSuggestions(userEnteredSearchText);
		$scope.selectedSuggestion = $scope.suggestions[0].searchText;
	}
	
	$scope.generateSuggestions = function(userEnteredSearchText) {
		$scope.suggestions = [
			{ "displayText":userEnteredSearchText + "", "searchText":userEnteredSearchText + "" },
			{ "displayText":userEnteredSearchText + "d1", "searchText":userEnteredSearchText + "s1" },
			{ "displayText":userEnteredSearchText + "d2", "searchText":userEnteredSearchText + "s2" },
			{ "displayText":userEnteredSearchText + "d3", "searchText":userEnteredSearchText + "s3" },
			{ "displayText":userEnteredSearchText + "d4", "searchText":userEnteredSearchText + "s4" },
			{ "displayText":userEnteredSearchText + "d5", "searchText":userEnteredSearchText + "s5" }
		];
	}
	
	$scope.submitSearch = function(searchText) 
	{
		var fullUrl;
		var ulQuery = $scope.searchQuery.toLowerCase();
		$scope.savedQuery = $scope.searchQuery;
		
		fullUrl = $scope.appUrl + "/api/posts/?page=1&per_page=10&q=" + ulQuery;
		
		$http.get(fullUrl).success(function(resp) {					
			$scope.suggestions = resp.searchFills;
		});
	}	
});
