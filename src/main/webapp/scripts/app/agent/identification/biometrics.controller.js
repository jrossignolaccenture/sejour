'use strict';

angular.module('sejourApp')
    .controller('BiometricsController', function ($scope, $state, $translate, Application, File, currentApplication) {
    	
    	$scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
    	$scope.application = currentApplication;
    	
    	$scope.photoValidated = false;
    	$scope.fingerprintsValidated = false;
    	$scope.signatureValidated = false;
    	
    	$scope.photoButtonText = $translate.instant('identification.biometrics.button.photo');
    	$scope.signature = "assets/fileUpload/signature_kim.soon.jeen@gmail.com.png";
    	
    	$scope.validatePhoto = function() {
    		if($scope.webcamphoto){
	    		$scope.photoValidated = !$scope.photoValidated;
	    		if($scope.photoValidated){
	    			var raw_image_data = $scope.webcamphoto.replace(/^data\:image\/\w+\;base64\,/, '');
		    		File.uploadBiometrics(raw_image_data, "photo", currentApplication.id)
				        .success(function(){
				        })
				        .error(function(){
				        });
	    		}
    		}
    	}
    	
    	$scope.activateFingerprints = function() {
    		$scope.fingerprints = true;
    	}
    	
    	$scope.validateFingerprints = function() {
    		if($scope.fingerprints) {
    			$scope.fingerprintsValidated = !$scope.fingerprintsValidated;
    		}
    	}
    	
    	$scope.clearSignature = function(signaturepad) {
    		signaturepad.clear();
    	}
    	
    	$scope.validateSignature = function(signaturepad) {
    		if (!signaturepad.isEmpty()) {
    			$scope.signatureValidated = !$scope.signatureValidated;
    			if($scope.signatureValidated){
    				$scope.padsignature = signaturepad.toDataURL();
    				var raw_image_data = $scope.padsignature.replace(/^data\:image\/\w+\;base64\,/, '');
		    		File.uploadBiometrics(raw_image_data, "signature", currentApplication.id)
				        .success(function(){
				        })
				        .error(function(){
				        });
    			}
    			$scope.$apply();
    		}
    	}
    
    	$scope.certify = function() {
    		Application.identify(currentApplication.id, 'biometrics').then(function(result){
            	$state.go('identification/list')
            });
    	}
    });
