'use strict';

angular.module('sejourApp')
    .controller('PaymentController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
    	$scope.autocomplete = function() {
    		if($scope.payment.cardOwner === 'Kim') {
    			$scope.payment.cardNumber = '4970 1012 3456 7890';
    			$scope.payment.cardExpiringDate = '08 / 16';
    			$scope.payment.cardCV = '123';
    		} else if($scope.payment.cardOwner === 'Zayat') {
    			$scope.payment.cardNumber = '4970 1012 3456 7890';
    			$scope.payment.cardExpiringDate = '08 / 16';
    			$scope.payment.cardCV = '123';
    		}
        };
        
        $scope.back = function () {
        	$state.go('summary', $stateParams);
        };
        $scope.save = function () {
            Application.pay(currentApplication.id).then(function() {
            	$state.go('final', $stateParams);
            });
        };
        
    });
