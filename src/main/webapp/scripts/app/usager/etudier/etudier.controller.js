'use strict';

angular.module('sejourApp')
    .controller('EtudierUsagerController', function ($rootScope, $scope, $state, Application) {
    	
        var isRenewal = $state.current.data.isRenewal;
    	
        $scope.createApplication = function() {
            Application.create(isRenewal ? 'renouvellement' : 'premiere', 'sejour_etudiant').then(function(id) {
            	$state.go('identity', {base: isRenewal ? 'renouveler' : 'etudier', id: id});
            });
        }
    });
