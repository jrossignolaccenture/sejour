'use strict';

angular.module('sejourApp')
    .controller('BiometricsController', function ($scope, $state, $translate, Demande, File, currentDemande) {
    	
    	$scope.studentName = currentDemande.identity.firstName + " " + currentDemande.identity.lastName;
    	
    	$scope.photoValidated = false;
    	$scope.photoOpened = true;
    	$scope.signatureOpened = true;
    	
    	$scope.photoButtonText = $translate.instant('identification.biometrics.button.photo');
    	$scope.signature = "assets/fileUpload/signature_kim.soon.jeen@gmail.com.png";
    	
    	$scope.validatePhoto = function() {
    		if($scope.webcamphoto){
	    		$scope.photoValidated = !$scope.photoValidated;
	    		if($scope.photoValidated){
	    			var raw_image_data = $scope.webcamphoto.replace(/^data\:image\/\w+\;base64\,/, '');
		    		File.uploadPhoto(raw_image_data, currentDemande.id)
				        .success(function(){
				        })
				        .error(function(){
				        });
	    		}
    		}
    	}
    
    	$scope.certify = function() {
    		Demande.identify(currentDemande).then(function(result){
            	$state.go('etudier/identification')
            });
    	}
    });
