'use strict';

angular.module('sejourApp')
    .controller('RenouvelerUsagerController', function ($rootScope, $scope, $state, Demande) {
    	
        $scope.initRequest = function(){
            Demande.initRenewal().then(function(){
            	$state.go('renouveler/identity');
            });
        }
    });
