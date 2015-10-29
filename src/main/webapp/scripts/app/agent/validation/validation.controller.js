'use strict';

angular.module('sejourApp')
    .controller('ValidationController', function ($scope, $state, Application, Country, currentApplication) {

        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';
    	
        Country.get().then(function(result) {
        	$scope.countries = result;
        });

    	$scope.getFormattedDate = function(date) {
    		return moment(date).format("DD/MM/YYYY");
    	}
        
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = $scope.getFormattedDate(currentApplication.identity.birthDate);
        $scope.identity.images = [];
        $scope.identity.images.push({src: "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg", title: "identity.form.passport"});
        $scope.identity.images.push({src: "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png", title: "identity.form.birthAct"});
        $scope.identity.currentImg = currentApplication.identity.images[0];
        $scope.identity.currentImgIndex = 0;
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = $scope.getFormattedDate(currentApplication.project.comingDate);
        $scope.project.trainingStartTxt = $scope.getFormattedDate(currentApplication.project.trainingStart);
    	var photoSuffix = $scope.isRenewal ? '_renouvellement' : '';
    	$scope.project.images = [];
		$scope.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com"+photoSuffix+".png", title: "project.form.inscriptionCertificate"});
		$scope.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com"+photoSuffix+".png", title: "project.form.resourceProof"});
    	$scope.project.currentImg = currentApplication.project.images[0];
    	$scope.project.currentImgIndex = 0;
    	
        $scope.application = currentApplication;
    	
    	$scope.applicationArchived = {};
    	if($scope.application.type == 'premiere') {
    		$scope.identityValidationDate = $scope.application.rdvDate;
    	} else {
    		Application.getByStatus('validated', currentApplication.email).then(function(result) {
            	$scope.applicationArchived = result[0];
            	$scope.identityValidationDate = $scope.applicationArchived.rdvDate;
            	$scope.historyStartDate = $scope.getFormattedDate(moment($scope.applicationArchived.project.trainingStart));
            	$scope.historyEndDate = $scope.getFormattedDate(moment($scope.applicationArchived.project.trainingStart).add($scope.applicationArchived.project.trainingLength, 'M').subtract(1, 'd'));
            });
    	}
    	
    	$scope.nextImg = function (element) {
            element.currentImgIndex++;
            if (element.currentImgIndex >= element.images.length) {
                element.currentImgIndex = 0;
            }
            element.currentImg = element.images[element.currentImgIndex];
        },
        $scope.previousImg = function (element) {
            element.currentImgIndex--;
            if (element.currentImgIndex < 0) {
                element.currentImgIndex = element.images.length - 1;
            }
            element.currentImg = element.images[element.currentImgIndex];
        }
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id).then(function(result) {
            	$state.go('validation/list')
            });
        }
    });
