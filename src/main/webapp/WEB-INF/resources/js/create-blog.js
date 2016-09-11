/**
 * 
 */

var createBlogModule = angular.module('createBlog', []);

createBlogModule.controller('createBlogCtrl', function($scope) {
	
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
			if ( document.getElementById("ref" + i).value != null &&
				 document.getElementById("ref" + i).value.length > 0 ) {
				
				while ( $scope.referencesRevealed < $scope.maxReferences &&
						$scope.referencesRevealed < i ) {
					$scope.revealReference();
				}
			}
		}
		if ( $scope.referencesRevealed == 0 ) {
			$scope.revealReference();
		}
	}
	
	$scope.init();
	
//	$scope.references = [ {id: 'reference1', name: '' } ];
//	
//	$scope.maxedReference = false;
//	
//	$scope.addNewChoice = function() {
//	  var newItemNo = $scope.references.length+1;
//	  $scope.references.push({'id':'reference'+newItemNo});
//	  if ( newItemNo >= 20 ) {
//		  $scope.maxedReference = true;
//	  }
//	};
//	
//	$scope.showAddChoice = function(reference) {
//	  return reference.id === $scope.references[$scope.references.length-1].id;
//	};
});
