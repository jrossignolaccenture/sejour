'use strict';

angular.module('sejourApp')
    .controller('IdentificationController', function ($scope, $state, Demande) {
    	
    	Demande.getIdentificableDemande().then(function(result) {
        	$scope.demandes = result;
        });
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    });
