'use strict';

angular.module('sejourApp')
    .controller('RenouvelerPaymentController', function ($scope, $state, Principal, Demande) {
        

		$scope.autocomplete = function() {
			if ($scope.payment.cardOwner === 'kim') {
				$scope.payment.cardNumber = '4970 1012 3456 7890';
				$scope.payment.cardExpiringDate = '08 / 16';
				$scope.payment.cardCV = '123';
			}
		};
    		
        $scope.save = function () {
        	console.log($scope.demande);
            Demande.prepaid().then(function(result){
            	$state.go('renouveler/final');
            });
        };
        
    });
