'use strict';

angular.module('sejourApp')
    .controller('FamilyController', function ($scope, $state, $stateParams, $timeout, Application, currentApplication) {

    	$scope.parents = currentApplication.identity.family['parents'];
    	if(!$scope.parents || $scope.parents.length == 0) {
    		$scope.parents = [{identity: {documents: []}, address: {}}, {identity: {documents: []}, address: {}}];
    	}

        $scope.back = function () {
        	$state.go('identity', $stateParams);
        };
        
        $scope.save = function () {
        	currentApplication.identity.family['parents'] = $scope.parents;
        	
        	Application.update(currentApplication).then(function() {
            	$state.go('address', $stateParams);
            });
        };
        
    });
