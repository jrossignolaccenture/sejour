'use strict';

angular.module('sejourApp')
    .controller('BiometricsController', function ($scope, $state, Demande, currentDemande) {
    	
    	$scope.studentName = currentDemande.identity.firstName + " " + currentDemande.identity.lastName;
    	
    	$scope.photoOpened = true;
    	$scope.signatureOpened = true;
    	
    	$scope.photo = "assets/fileUpload/photo_kim.soon.jeen@gmail.com.jpg";
    	$scope.signature = "assets/fileUpload/signature_kim.soon.jeen@gmail.com.png";
    	
    	$scope.certify = function() {
    		Demande.identify(currentDemande).then(function(result){
            	$state.go('etudier/identification')
            });
    	}
    });
