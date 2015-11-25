'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        var docs = currentApplication.identity.documents.filter(function(doc) {
			return doc.type === 'passport' || doc.type === 'birthAct';
		});
        		
    	$scope.needDocuments = docs.length == 0 || docs.filter(function(doc) {return !doc.validation}).length > 0;
    	
        $scope.identity = currentApplication.identity;
        $scope.nature = currentApplication.nature; //TODO temporaire pour faire suite à un retour pour la démo (à virer après)
        
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
