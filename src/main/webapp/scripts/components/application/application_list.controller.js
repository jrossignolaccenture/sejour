'use strict';

angular.module('sejourApp')
    .controller('ApplicationListController', function ($scope, $state, Country, applications) {

        $scope.listType = $state.current.data.listType;
    	$scope.applications = applications;
    	
    	$scope.countries = [];
        Country.get().then(function(countries) {
        	for (var i=0; i<countries.length; i++) {
        		$scope.countries[countries[i].key] = countries[i].name;
        	}
        });
    	
    	$scope.getNationality = function(key){
    		return $scope.countries[key];
    	}
    	
    	$scope.getFormattedDate = function(date){
    		return moment(date).format("DD/MM/YYYY");
    	}
    	
    	// TODO revoir toutes les function goTo
    	$scope.goToDetail = function(application) {
    		if(application.statut === 'paid') {
    			$state.go('admissibility', {id: application.id});
    		} else if(application.statut === 'identity_verified') {
    			$state.go('validation', {id: application.id});
    		}
    	}
    	
    	$scope.goToDocuments = function(application) {
    		$state.go('documents', {id: application.id});
    	}
    	
    	$scope.goToBiometrics = function(application) {
    		$state.go('biometrics', {id: application.id});
    	}
    });
