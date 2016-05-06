'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        var docs = currentApplication.identity.documents.filter(function(doc) {
			return doc.type === 'passport' || doc.type === 'birthAct';
		});

    	$scope.needDocuments = docs.length == 0 || docs.filter(function(doc) {return !doc.validation}).length > 0;
    	
    	$scope.showForeignerNumber = currentApplication.nature === 'sejour_tmp_etudiant';
        $scope.identity = currentApplication.identity;
        
        // indicateur fumeux
        $scope.needResidencyCountry = currentApplication.type === 'premiere';
        
        $scope.report = function () {
        	$scope.identity.validateOn = undefined;
        	$scope.needDocuments = true;
        	docs.forEach(function(doc) {
        		doc.validation = undefined;
        	});
        }
        
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
