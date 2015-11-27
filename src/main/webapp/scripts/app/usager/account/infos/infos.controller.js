'use strict';

angular.module('sejourApp')
    .controller('AccountInfosController', function ($scope, $state, Account, Auth) {
    	$scope.error = null;

    	Account.get().$promise.then(function (account) {
            $scope.account = account.data;
            $scope.identity = account.data.identity;
            $scope.address = account.data.address;
        });
        
        $scope.save = function () {
        	Auth.updateAccount($scope.account).then(function(result){
        		$scope.error = null;
            	$state.go('usager/account');
            }).catch(function() {
                $scope.error = 'ERROR';
            });
        };
        
    });
