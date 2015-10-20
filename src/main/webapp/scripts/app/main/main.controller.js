'use strict';

angular.module('sejourApp')
    .controller('MainController', function ($rootScope, $scope, $state, Principal, Application) {
    	
        $scope.isAuthenticated = Principal.isAuthenticated;
        
        $scope.nbPaid = 0;
        $scope.nbScheduled = 0;
        $scope.nbIdentityVerified = 0;
        
        if(Principal.isAuthenticated) {
        	Principal.identity().then(function(account) {
        		$scope.account = account;
        		if(account) {
        			$rootScope.userType = account.type;
        			if(account.type === 'agent'){
        				Application.count().then(function(applicationCount) {
	        		        $scope.nbPaid = applicationCount.nbPaid;
	        		        $scope.nbScheduled = applicationCount.nbScheduled;
	        		        $scope.nbIdentityVerified = applicationCount.nbIdentityVerified;
        				});
        			}
        		} else {
        			if($rootScope.userType === 'agent') {
        				$state.go('login');
        			}
        		}
        	});
        }
        
        $scope.choose = function(userType) {
        	$rootScope.userType = userType;
        }
    });
