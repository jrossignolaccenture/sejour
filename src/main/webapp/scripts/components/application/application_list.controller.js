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
        $scope.hasDocumentToCertify = function(application) {
        	if(application.identity.validateOn && application.identity.familyValidateOn) {
        		// Identity already certified so no documents to validate
        		return false;
        	}
        	
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
    	
    	$scope.goToValidationDetail = function(application) {
    		if(application.statut === 'validated') {
    			$state.go('reconstruction', {id: application.id});
    		} else {
    			$state.go('validation', {id: application.id});
    		}
    	}
    });
