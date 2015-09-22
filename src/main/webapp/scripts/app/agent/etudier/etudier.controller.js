'use strict';

angular.module('sejourApp')
    .controller('EtudierAgentController', function ($rootScope, $scope, $state, Demande) {
    	
        $scope.initRequest = function(){
            Demande.init().then(function(){
            	$state.go('etudier/validation');
            });
        }
    });
