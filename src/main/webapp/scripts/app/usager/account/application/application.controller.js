'use strict';

angular.module('sejourApp')
    .controller('ApplicationController', function ($scope, $state, Application, userApplications) {

    	$scope.userApplications = userApplications;
    	
    	openFirstApplication()
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    	
    	$scope.getFormattedHour = function(date){
    		return moment(date).format("HH:mm");
    	}
    	
    	$scope.goToDetail = function(type, id){
    		$state.go('summary', {base: type === 'premiere' ? 'etudier' : 'renouveler', id: id});
    	}
    	
    	$scope.goToDraft = function(type, id){
    		$state.go('identity', {base: type === 'premiere' ? 'etudier' : 'renouveler', id: id});
    	}
    	
    	$scope.goToRdv = function(type, id){
    		$state.go('rdv', {base: type === 'premiere' ? 'etudier' : 'renouveler', id: id});
    	}
    	
    	$scope.delete = function(id) {
    		Application.delete(id).then(function() {
    			for(var i=0; i<$scope.userApplications.length; i++) {
    				if($scope.userApplications[i].id == id) {
    					$scope.userApplications.splice(i, 1);
    					break;
    				}
            	}
    			openFirstApplication();
    		});
    	}
    	
    	function openFirstApplication(){
    		if($scope.userApplications.length > 0) {
				$scope.userApplications[0].opened = true;
			}
    	}
    });
