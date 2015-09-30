'use strict';

angular.module('sejourApp')
    .controller('MainController', function ($scope, $state, Principal, Demande) {
    	
        $scope.isAuthenticated = Principal.isAuthenticated;
        
        $scope.nbConsult = 0;
        $scope.nbRecevability = 0;
        $scope.nbIdentification = 0;
        $scope.nbDecision = 0;
        
        if(Principal.isAuthenticated) {
        	Principal.identity().then(function(account) {
        		$scope.account = account;
        		if(account) {
        			$scope.userType = account.type;
        			if(account.type == 'agent'){
        				Demande.getCount().then(function(result) {
	        		        $scope.nbRecevability = result.nbRecevability;
	        		        $scope.nbRdv = result.nbIdentification;
	        		        $scope.nbDecision = result.nbDecision;
        				});
        			}
        		}
        	});
        }
        
        $scope.choose = function(userType) {
        	$scope.userType = userType;
        }
    });
