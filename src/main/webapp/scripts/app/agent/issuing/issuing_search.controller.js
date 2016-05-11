'use strict';

angular.module('sejourApp')
    .controller('IssuingSearchController', function ($scope, $state, $timeout, Application, applications) {
    	
    	$scope.tab = 'fingerprint';
    	$scope.currentApplication = undefined;
    	
    	function getCurrentApplication(foreignerNumber) {
    		return applications.find(function(application) {
				return application.identity.foreignerNumber === foreignerNumber;
			});
    	}
    	
    	function startFingerprint(currentApplication) {
    		$scope.fingerprints = true;
    		$timeout(function(){
        		if(currentApplication) {
        			$scope.fingerprintsMatched = true;
        			$timeout(function(){
        				$state.go('issuing', {id: currentApplication.id});
        			}, 750);
        		} else {
        			$scope.fingerprintsError = true;
        		}
    		}, 2300);
    	}
    	
    	$scope.searchByFingerprint = function() {
    		var currentApplication = getCurrentApplication('0123456789');
    		startFingerprint(currentApplication);
    	}
    	
    	$scope.searchByForeignerNumber = function(toto) {
    		$scope.currentApplication = getCurrentApplication($scope.foreignerNumber);
    	}
    	
    	$scope.goToIssuing = function() {
    		$state.go('issuing', {id: $scope.currentApplication.id});
    	}
    });
