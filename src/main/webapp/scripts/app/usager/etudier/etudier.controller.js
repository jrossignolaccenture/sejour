'use strict';

angular.module('sejourApp')
    .controller('EtudierUsagerController', function ($rootScope, $scope, $state, Application) {
    	
        $scope.initRequest = function() {
            Application.create('premiere', 'sejour_etudiant').then(function(id) {
            	$state.go('identity', {base: 'etudier', id: id});
            });
        }
    });
