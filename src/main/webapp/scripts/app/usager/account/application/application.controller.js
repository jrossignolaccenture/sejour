'use strict';

angular.module('sejourApp')
    .controller('ApplicationController', function ($scope, $state, Demande) {

    	$scope.demandes = [];

        Demande.getAll().then(function(result) {
        	$scope.demandes = result;
        });
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    });
