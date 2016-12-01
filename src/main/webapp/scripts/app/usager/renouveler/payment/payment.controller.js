'use strict';

angular.module('sejourApp')
    .controller('RenouvelerPaymentController', function ($scope, $state, Principal, Demande) {
        
        
        $scope.save = function () {
        	console.log($scope.demande);
            Demande.prepaid().then(function(result){
            	$state.go('renouveler/final');
            });
        };
        
    });
