'use strict';

angular.module('sejourApp')
    .controller('NavbarController', function ($scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';
        
        $scope.accountRetrieved = false;
        $scope.getAccountName = function () {
        	if(Principal.isAuthenticated && !$scope.accountRetrieved) {
            	Principal.identity().then(function(account) {
            		$scope.account = account;
            		$scope.accountRetrieved = true;
            	});
            } else {
            	$scope.accountRetrieved = false;
            }
        }

        $scope.logout = function () {
            Auth.logout();
            $state.reload();
        };
    });
