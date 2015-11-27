'use strict';

angular.module('sejourApp')
    .controller('ApplicationListController', function ($scope, $state, applications) {

        $scope.listType = $state.current.data.listType;
    	$scope.applications = applications;
        
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
    	
    	// TODO revoir toutes les function goTo
    	$scope.goToDetail = function(application) {
    		if(application.statut === 'paid') {
    			$state.go('admissibility', {id: application.id});
    		} else if(application.statut === 'identity_verified' || application.statut === 'favorable_proposal') {
    			$state.go('validation', {id: application.id});
    		} else if(application.statut === 'validated') {
    			$state.go('reconstruction', {id: application.id});
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
