'use strict';

angular.module('sejourApp')
    .controller('NavbarController', function ($rootScope, $scope, $location, $state, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';
        
        if(Principal.isAuthenticated) {
        	Principal.identity().then(function(account) {
        		$scope.account = account;
        	});
        }
        
        $rootScope.$watch('account', function(newValue, oldValue) {
        	  if(newValue && !$scope.account){
        		 $scope.account = newValue;
        	  }
        });

        $scope.logout = function () {
        	$scope.account = null;
            Auth.logout();
            $state.reload();
        };
    });
