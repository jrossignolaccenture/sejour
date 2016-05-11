'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
    	// sale
    	$scope.needBrothers = currentApplication.nature === 'naturalisation';
    	// indicateur fumeux
    	$scope.needResidencyCountry = currentApplication.type === 'premiere';
        
        init();
        
        function init() {
        	var docs = currentApplication.identity.documents.filter(function(doc) {
    			return doc.type === 'passport' || doc.type === 'birthAct';
    		});
        	$scope.needDocuments = docs.length == 0 || docs.filter(function(doc) {return !doc.validation}).length > 0;
        	$scope.identity = currentApplication.identity;
        	//used for disabling field... #vomi
        	$scope.identity.modeV2 = currentApplication.nature === 'sejour_tmp_etudiant';
        	$scope.readOnlyMode = $scope.identity.validateOn || ($scope.identity.modeV2 && !$scope.identity.changed && $scope.identity.valid);
        }
        
        $scope.modify = function () {
        	$scope.currentApplicationBackup = JSON.stringify(currentApplication);
        	$scope.identity.changed = true;
        	$scope.readOnlyMode = false;
        }
        
        $scope.cancel = function () {
        	currentApplication = JSON.parse($scope.currentApplicationBackup);
        	$scope.currentApplicationBackup = undefined;
        	init();
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
