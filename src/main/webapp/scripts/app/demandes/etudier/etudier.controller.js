'use strict';

angular.module('sejourApp')
    .controller('EtudierController', function ($rootScope, $scope, $state, Principal, Demande) {
    	
        $scope.initRequest = function(){
            Demande.init().then(function(){
            	$state.go('identity');
            });
        }
    });
