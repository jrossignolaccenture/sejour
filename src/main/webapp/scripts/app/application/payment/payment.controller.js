'use strict';

angular.module('sejourApp')
    .controller('PaymentController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        
    	$scope.autocomplete = function() {
    		if($scope.payment.cardOwner === 'Kim') {
    			$scope.payment.cardNumber = '4970 1012 3456 7890';
    			$scope.payment.cardExpiringDate = '08 / 16';
    			$scope.payment.cardCV = '123';
    		}
        };
           
        $scope.save = function () {
            Application.pay(currentApplication.id).then(function() {
            	$state.go('final', $stateParams);
            });
        };
        
    });
