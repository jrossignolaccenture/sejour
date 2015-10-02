'use strict';

angular.module('sejourApp')
    .controller('ApplicationController', function ($scope, $state, Demande) {

    	$scope.demandes = [];

        Demande.getAll().then(function(result) {
        	$scope.demandes = result;
        	var length = $scope.demandes.length;
        	for(var i=0; i<length; i++){
        		if(i==0){
        			$scope.demandes[i].opened = true;
        		}
        	}
        });
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    	
    	$scope.remove = function(id){
    		Demande.remove(id).then(function() {
            	var length = $scope.demandes.length;
    			for(var i=0; i<length; i++){
            		$scope.demandes.splice(i, 1);
            	}
    		});
    	}
    });
