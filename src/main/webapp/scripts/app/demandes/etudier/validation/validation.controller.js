'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Demande) {
    	$scope.studentName;
    	
    	Demande.getDemandeToValidate("kimsoonjeen@gmail.com").then(function(result) {
        	$scope.demande = result;
        	$scope.studentName = result.identity.firstName + " " + result.identity.lastName;
        });
    	
    });
