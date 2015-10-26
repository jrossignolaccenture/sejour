'use strict';

angular.module('sejourApp')
    .controller('RenouvelerUsagerController', function ($scope, $state) {
    	
        $scope.initRequest = function(){
            Demande.initRenewal().then(function(){
            	$state.go('renouveler/identity');
            });
        }
    });
