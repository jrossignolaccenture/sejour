'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        var docs = currentApplication.identity.documents.filter(function(doc) {
			return doc.type === 'passport' || doc.type === 'birthAct';
		});
        		
    	$scope.documentsToDisplay = docs.filter(function(doc) {
			return !doc.validation;
		});
    	
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
