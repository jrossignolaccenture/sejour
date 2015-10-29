'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($rootScope, $scope, $state, Country, Application, currentApplication) {

    	//  !!!!! BEAUCOUP DE CODE A MUTUALISER AVEC VALIDATION.CONTROLLER.JS !!!!!
    	$scope.isAgent = true;
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';

    	$scope.identityOpened = true;
    	$scope.addressOpened = true;
    	$scope.projectOpened = true;
    	if($scope.isRenewal) {
        	$scope.identityOpened = false;
    		$scope.identityValidated = true;
    		$scope.addressOpened = false;
    		$scope.addressValidated = true;
    	}
    	
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
    	
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = moment(currentApplication.identity.birthDate).format("DD/MM/YYYY");
        $scope.identity.images = [];
        $scope.identity.images.push({src: "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg", title: "identity.form.passport"});
        $scope.identity.images.push({src: "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png", title: "identity.form.birthAct"});
        $scope.identity.currentImg = currentApplication.identity.images[0];
        $scope.identity.currentImgIndex = 0;

    	$scope.address = currentApplication.address;
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = moment(currentApplication.project.comingDate).format("DD/MM/YYYY");
        $scope.project.trainingStartTxt = moment(currentApplication.project.trainingStart).format("DD/MM/YYYY");
    	var photoSuffix = $scope.isRenewal ? '_renouvellement' : '';
    	$scope.project.images = [];
		$scope.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com"+photoSuffix+".png", title: "project.form.inscriptionCertificate"});
		$scope.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com"+photoSuffix+".png", title: "project.form.resourceProof"});
    	$scope.project.currentImg = currentApplication.project.images[0];
    	$scope.project.currentImgIndex = 0;
    	
    	$scope.application = currentApplication;
    	
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
        
        $scope.verify = function () {
            Application.admissibility(currentApplication.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
