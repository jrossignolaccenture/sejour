'use strict';

angular.module('sejourApp')
    .controller('ApplicationListController', function ($scope, $state, Country, applications) {

        $scope.listType = $state.current.data.listType;
    	$scope.applications = applications;
    	
    	$scope.countries = [];
        Country.get().then(function(countries) {
        	countries.forEach(function(country){
        		$scope.countries[country.key] = country.name;
        	});
        });
        
        /** Verify if at least one document is not validated **/
        var hasFamilyDocumentToCertify = function(application) {
        	return Object.keys(application.identity.family).find(function(personType) {
        		var persons = application.identity.family[personType];
        		return persons.find(function(person) {
    	    		return person.identity.documents.find(function(document) {
    	        		return !document.validation;
    	        	});
    	    	});
        	});
        }
        // TODO un peu violent si la liste d'application contient beaucoup d'élément non ??
        $scope.hasDocumentToCertify = function(application) {
        	var hasDocToCertify = application.identity.documents.find(function(document) {
        		return !document.validation;
        	});
        	if(hasDocToCertify || hasFamilyDocumentToCertify(application)) {
        		return true;
        	}
        }
    	
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
    	
    	$scope.goToInterview = function(application) {
    		$state.go('interview', {id: application.id});
    	}
    });
