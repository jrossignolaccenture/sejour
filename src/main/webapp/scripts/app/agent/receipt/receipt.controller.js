'use strict';

angular.module('sejourApp')
    .controller('ReceiptController', function ($scope, $state, $timeout, Application, applications) {
    	
    	function calculateRandomApplications() {
    		$scope.randomApplications = [];
    		var index = Math.floor(Math.random() * 1);
    		for(var i=0; i< 7 && index < applications.length; i++) {
    			var app = applications[index];
    			if(app.identity.foreignerNumber !== "0123456789") {
    				$scope.randomApplications.push(app);
    			}
    			index += Math.floor(Math.random() * 2) + 1;
    		}
			var currentApplication = applications.filter(function(application) {
				return application.identity.foreignerNumber === "0123456789";
			});
    		if(currentApplication.length > 0){
    			$scope.randomApplications.push(currentApplication[0]);
    		}
    	}
    	
    	$scope.launchScan = function() {
    		$scope.scan = true;
    		$scope.scanMatched = false;
    		$timeout(function(){
    			calculateRandomApplications();
        		$scope.scanMatched = true;
    		}, 2000);
    	}
    	
    	$scope.receipt = function() {
    		var ids = [];
    		$scope.randomApplications.forEach(function(application) {
    			ids.push(application.id);
    		});
    		Application.receipt(ids).then(function(){
    			$state.go('follow', {tab: 'recus'});
    		});
    	}
    });
