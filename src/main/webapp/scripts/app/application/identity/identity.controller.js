'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        var docs = currentApplication.identity.documents.filter(doc => doc.type === 'passport' || doc.type === 'birthAct');
    	$scope.needDocuments = docs.length === 0 || docs.filter(doc => !doc.validation);
    	
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
