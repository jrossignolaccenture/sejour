'use strict';

angular.module('sejourApp')
    .controller('EtudierUsagerController', function ($rootScope, $scope, $state, Demande) {
    	
        $scope.initRequest = function(){
            Demande.init().then(function(){
            	$state.go('etudier/identity');
            });
        }
    });
