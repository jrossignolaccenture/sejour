'use strict';

angular.module('sejourApp')
    .controller('MainController', function ($scope, $state, Principal) {
    	
        $scope.isAuthenticated = Principal.isAuthenticated;
        
        if(Principal.isAuthenticated) {
        	Principal.identity().then(function(account) {
        		$scope.account = account;
        		if(account) {
        			$scope.userType = account.type;
        		}
        	});
        }
        
        $scope.choose = function(userType) {
        	$scope.userType = userType;
        }
    });
