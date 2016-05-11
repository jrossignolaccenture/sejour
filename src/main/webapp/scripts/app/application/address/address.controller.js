'use strict';

angular.module('sejourApp')
    .controller('AddressController', function ($scope, $state, $stateParams, Application, currentApplication) {

        init();
        
        function init() {
        	var docs = currentApplication.identity.documents.filter(function(doc) {
    			return doc.type === 'residency';
    		});
        	$scope.needDocuments = currentApplication.nature === 'sejour_tmp_etudiant' && (docs.length == 0 || docs.filter(function(doc) {return !doc.validation}).length > 0);
        	$scope.address = currentApplication.address;
        	$scope.address.modeV2 = currentApplication.nature === 'sejour_tmp_etudiant';
        	$scope.readOnlyMode = $scope.address.validateOn || ($scope.address.modeV2 && !$scope.address.changed && $scope.address.valid);
        }
        
        $scope.back = function () {
        	if(currentApplication.nature === 'naturalisation') {
        		$state.go('family', $stateParams);
        	} else {
        		$state.go('identity', $stateParams);
        	}
        }
        
        $scope.modify = function () {
        	$scope.currentApplicationBackup = JSON.stringify(currentApplication);
        	$scope.address.changed = true;
        	$scope.readOnlyMode = false;
        }
        
        $scope.cancel = function () {
        	currentApplication = JSON.parse($scope.currentApplicationBackup);
        	$scope.currentApplicationBackup = undefined;
        	init();
        }
        
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('project', $stateParams);
            });
        };
        
    });
