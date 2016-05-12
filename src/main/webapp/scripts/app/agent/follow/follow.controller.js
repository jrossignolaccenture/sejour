'use strict';

angular.module('sejourApp')
    .controller('FollowController', function ($scope, $state, $stateParams, $timeout, Application, applications) {
    	
    	$scope.tab = $stateParams.tab;
    	$scope.applications = [];
    	
    	applications.forEach(function(application){
    		if($scope.tab === 'cours' && application.receiptDate == null){
    			$scope.applications.push(application);
    		} else if($scope.tab === 'recus' && application.receiptDate != null && application.issuingDate == null){
    			$scope.applications.push(application);
    		} else if($scope.tab === 'delivres' && application.receiptDate != null && application.issuingDate != null){
    			$scope.applications.push(application);
    		}
		});
    });
