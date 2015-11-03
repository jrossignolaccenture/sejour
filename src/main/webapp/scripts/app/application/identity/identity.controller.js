'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        
        $scope.needDocuments = currentApplication.type === 'premiere';
        
        $scope.identity = currentApplication.identity;
        
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	if(currentApplication.type === 'naturalisation') {
            		$state.go('identity-family', $stateParams);
            	} else {
            		$state.go('address', $stateParams);
            	}
            });
        };
        
    })
    .controller('IdentityFamilyController', function ($scope, $state, $stateParams, File, Application, currentApplication) {

    	$scope.view = 'father';
    	
    	$scope.father = currentApplication.identity.family['father'];
    	$scope.father = $scope.father ? $scope.father[0] : {};
    	
    	$scope.mother = currentApplication.identity.family['mother'];
    	$scope.mother = $scope.mother ? $scope.mother[0] : {};

        $scope.back = function () {
        	$state.go('identity', $stateParams);
        };
        
        $scope.save = function () {
        	currentApplication.identity.family['father'] = [$scope.father];
        	currentApplication.identity.family['mother'] = [$scope.mother];
        	
        	Application.update(currentApplication).then(function() {
            	$state.go('address', $stateParams);
            });
        };
        
    });
