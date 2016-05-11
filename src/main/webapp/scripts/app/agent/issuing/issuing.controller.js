'use strict';

angular.module('sejourApp')
    .controller('IssuingController', function ($scope, $state, $timeout, Application, currentApplication) {

        $scope.identityInFrance = currentApplication.nature === 'sejour_tmp_etudiant';
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
    	
        $scope.application = currentApplication;
    	
    	$scope.clearSignature = function(signaturepad) {
    		signaturepad.clear();
    	}
        
        $scope.issue = function () {
    		Application.issuing(currentApplication.id).then(function(result) {
            	$state.go('issuing/list')
            });
        }
    });
