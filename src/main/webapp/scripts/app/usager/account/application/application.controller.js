'use strict';

angular.module('sejourApp')
    .controller('ApplicationController', function ($scope, $state, Application, userApplications) {

    	$scope.userApplications = userApplications;
    	
    	$scope.delete = function(id) {
    		Application.delete(id).then(function() {
    			for(var i=0; i<$scope.userApplications.length; i++) {
    				if($scope.userApplications[i].id == id) {
    					$scope.userApplications.splice(i, 1);
    					break;
    				}
            	}
    		});
    	}
    });
