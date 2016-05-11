'use strict';

angular.module('sejourApp')
    .controller('FollowController', function ($scope, $state, $timeout, Application, applications) {
    	
    	var inProgressApplications = [],
    		receiptedApplications = [],
    		issuedApplications = [];
    	
    	applications.forEach(function(application){
    		if(application.receiptDate == null){
    			inProgressApplications.push(application);
    		} else if(application.receiptDate != null && application.issuingDate == null){
    			receiptedApplications.push(application);
    		} else if(application.receiptDate != null && application.issuingDate != null){
    			issuedApplications.push(application);
    		}
		});
    	
    	$scope.changeTab = function(newTab){
    		$scope.tab = newTab;
    		if(newTab === 'inProgress'){
    			$scope.applications = inProgressApplications;
    		} else if(newTab === 'receipted'){
    			$scope.applications = receiptedApplications;
    		} else if(newTab === 'issued'){
    			$scope.applications = issuedApplications;
    		}
    	}
    	
    	$scope.changeTab('inProgress');
    	
    });
