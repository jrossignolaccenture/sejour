'use strict';

angular.module('sejourApp')
    .controller('MainController', function ($rootScope, $scope, $state, Principal, Demande) {
    	
        $scope.isAuthenticated = Principal.isAuthenticated;
        
        $scope.nbRecevability = 0;
        $scope.nbIdentification = 0;
        $scope.nbDecision = 0;
        
        if(Principal.isAuthenticated) {
        	Principal.identity().then(function(account) {
        		$scope.account = account;
        		if(account) {
        			$scope.userType = account.type;
        			$rootScope.userType = account.type;
        			if(account.type == 'agent'){
        				Demande.getCount().then(function(result) {
	        		        $scope.nbRecevability = result.nbRecevability;
	        		        $scope.nbRdv = result.nbIdentification;
	        		        $scope.nbDecision = result.nbDecision;
        				});
        			}
        		} else {
        			if($rootScope.userType == 'agent') {
        				$state.go('login');
        			}
        		}
        	});
        }
        
        $rootScope.$watch('userType', function(newValue, oldValue) {
        	$scope.userType = newValue;
        });
        
        $scope.choose = function(userType) {
        	$scope.userType = userType;
        	$rootScope.userType = userType;
        }
    });
