'use strict';

angular.module('sejourApp')
    .controller('AccountUsagerController', function ($scope, $state, Demande) {
    	
    	$scope.nbApplication = 0;
    	Demande.getCurrentCount().then(function(result) {
	        $scope.nbApplication = result;
		});
    });
