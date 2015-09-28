'use strict';

angular.module('sejourApp')
    .controller('ValidationListController', function ($scope, $state, Demande, Country) {

    	$scope.demandes = [];
    	$scope.countries = [];
    	
        Country.get().then(function(result) {
        	for (var i=0; i<result.length; i++){
        		$scope.countries[result[i].key] = result[i].name;
        	}
        });
    	
    	Demande.getValidableDemande().then(function(result) {
        	$scope.demandes = result;
        });
    	
    	$scope.getNationality = function(key){
    		return $scope.countries[key];
    	}
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    });
