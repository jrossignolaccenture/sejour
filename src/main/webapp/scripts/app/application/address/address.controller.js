'use strict';

angular.module('sejourApp')
    .controller('AddressController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
    	$scope.address = currentApplication.address;
    	
    	var docs = currentApplication.identity.documents.filter(function(doc) {
			return doc.type === 'residency';
		});
        		
    	$scope.needDocuments = currentApplication.nature === 'sejour_tmp_etudiant' && (docs.length == 0 || docs.filter(function(doc) {return !doc.validation}).length > 0);

        $scope.back = function () {
        	if(currentApplication.nature === 'naturalisation') {
        		$state.go('family', $stateParams);
        	} else {
        		$state.go('identity', $stateParams);
        	}
        };
        
        $scope.report = function () {
        	$scope.address.validateOn = undefined;
        	$scope.needDocuments = true;
        }
        
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('project', $stateParams);
            });
        };
        
    });
