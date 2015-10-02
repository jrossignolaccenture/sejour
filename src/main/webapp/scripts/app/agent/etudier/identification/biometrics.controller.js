'use strict';

angular.module('sejourApp')
    .controller('BiometricsController', function ($scope, $state, $translate, Demande, File, currentDemande) {
    	
    	$scope.studentName = currentDemande.identity.firstName + " " + currentDemande.identity.lastName;
    	
    	$scope.photoValidated = false;
    	$scope.photoOpened = true;
    	$scope.signatureValidated = false;
    	$scope.signatureOpened = true;
    	
    	$scope.photoButtonText = $translate.instant('identification.biometrics.button.photo');
    	$scope.signature = "assets/fileUpload/signature_kim.soon.jeen@gmail.com.png";
    	
    	$scope.validatePhoto = function() {
    		if($scope.webcamphoto){
	    		$scope.photoValidated = !$scope.photoValidated;
	    		if($scope.photoValidated){
	    			var raw_image_data = $scope.webcamphoto.replace(/^data\:image\/\w+\;base64\,/, '');
		    		File.uploadBiometrics(raw_image_data, "photo", currentDemande.id)
				        .success(function(){
				        })
				        .error(function(){
				        });
	    		}
    		}
    	}
    	
    	$scope.clearSignature = function(signaturepad) {
    		signaturepad.clear();
    	}
    	
    	$scope.validateSignature = function(signaturepad) {
    		if (!signaturepad.isEmpty()) {
    			$scope.signatureValidated = !$scope.signatureValidated;
    			console.log($scope.signatureValidated);
    			if($scope.signatureValidated){
    				$scope.padsignature = signaturepad.toDataURL();
    				var raw_image_data = $scope.padsignature.replace(/^data\:image\/\w+\;base64\,/, '');
		    		File.uploadBiometrics(raw_image_data, "signature", currentDemande.id)
				        .success(function(){
				        })
				        .error(function(){
				        });
    			}
    			$scope.$apply();
    		}
    	}
    
    	$scope.certify = function() {
    		Demande.identifyBiometrics(currentDemande).then(function(result){
            	$state.go('etudier/identification')
            });
    	}
    });
