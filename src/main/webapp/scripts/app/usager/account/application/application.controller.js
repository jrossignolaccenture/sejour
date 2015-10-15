'use strict';

angular.module('sejourApp')
    .controller('ApplicationController', function ($scope, $state, Demande) {

    	$scope.demandes = [];

        Demande.getAll().then(function(result){
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
    	
    	$scope.getFormattedHour = function(date){
    		return moment(date).format("HH:mm");
    	}
    	
    	$scope.goToDetail = function(type, id){
    		if(type == 'premiere'){
    			$state.go('etudier/detail', {id: id});
    		} else{
    			$state.go('renouveler/detail', {id: id});
    		}
    	}
    	
    	$scope.remove = function(id){
    		Demande.remove(id).then(function(){
            	var length = $scope.demandes.length;
    			for(var i=0; i<length; i++){
    				if($scope.demandes[i].id == id){
    					$scope.demandes.splice(i, 1);
    					if($scope.demandes.length>0){
    						$scope.demandes[0].opened = true;
    					}
    					break;
    				}
            	}
    		});
    	}
    	
    	$scope.goToDraft = function(type){
    		if(type == 'premiere'){
    			$state.go('etudier/identity');
    		} else {
    			$state.go('renouveler/identity');
    		}
    	}
    	
    	$scope.goToPayment = function(type){
    		if(type == 'premiere'){
    			$state.go('etudier/payment');
    		} else {
    			$state.go('renouveler/payment');
    		}
    	}
    });
