'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        $scope.needDocuments = currentApplication.type === 'premiere';
        $scope.withFrancisation = currentApplication.type === 'naturalisation';
        
        $scope.identity = currentApplication.identity;
        
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	if(currentApplication.type === 'naturalisation') {
            		$state.go('family', $stateParams);
            	} else {
            		$state.go('address', $stateParams);
            	}
            });
        };
        
    });
