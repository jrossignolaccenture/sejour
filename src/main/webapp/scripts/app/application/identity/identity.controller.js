'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        $scope.needDocuments = currentApplication.identity.documents.length == 0;
        
        $scope.identity = currentApplication.identity;
        
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	if(currentApplication.nature === 'naturalisation') {
            		$state.go('family', $stateParams);
            	} else {
            		$state.go('address', $stateParams);
            	}
            });
        };
        
    });
