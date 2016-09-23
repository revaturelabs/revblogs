/**
 * 
 */

var createBlogModule = angular.module('createBlog', []);

createBlogModule.controller('createBlogCtrl', function($scope) {
	
	$scope.ngTitle = document.getElementById("blogTitle").value;
	$scope.ngSubtitle = document.getElementById("blogSubtitle").value;
	
	$scope.maxReferences = 20;
	$scope.referencesRevealed = 0;
	
	$scope.revealReference = function() {
		if ( $scope.referencesRevealed < $scope.maxReferences ) {
			document.getElementById("ref" + $scope.referencesRevealed).style = "";
			$scope.referencesRevealed ++;
			if ( $scope.referencesRevealed >= $scope.maxReferences ) {
				document.getElementById("addRefButton").style = "display: none;";
			}
		}
	}
	
	$scope.init = function() {
		for ( var i=0; i<$scope.maxReferences; i++ ) {
			if ( document.getElementById("references" + i).value != null &&
				 document.getElementById("references" + i).value.length > 0 ) {
				
				while ( $scope.referencesRevealed < $scope.maxReferences &&
						$scope.referencesRevealed < i+1 ) {
					$scope.revealReference();
				}
			}
		}
		if ( $scope.referencesRevealed === 0 ) {
			$scope.revealReference();
		}
	}
	
	$scope.init();
});
