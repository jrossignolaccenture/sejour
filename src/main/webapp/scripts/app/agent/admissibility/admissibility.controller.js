'use strict';

angular.module('sejourApp')
    .controller('AdmissibilityController', function ($scope, $state, Country, Application, currentApplication) {

    	//  !!!!! BEAUCOUP DE CODE A MUTUALISER AVEC VALIDATION.CONTROLLER.JS !!!!!

    	$scope.identityOpened = true;
    	$scope.addressOpened = true;
    	$scope.projectOpened = true;
    	if(currentApplication.type == 'renouvellement') {
        	$scope.identityOpened = false;
    		$scope.identityValidated = true;
    		$scope.addressOpened = false;
    		$scope.addressValidated = true;
    	}
    	
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
    	
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
        
    	currentApplication.identity.birthDateTxt = moment(currentApplication.identity.birthDate).format("DD/MM/YYYY");
    	currentApplication.identity.images = [];
    	currentApplication.identity.images.push({src: "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg", title: "identity.form.passport"});
    	currentApplication.identity.images.push({src: "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png", title: "identity.form.birthAct"});
    	currentApplication.identity.currentImg = currentApplication.identity.images[0];
    	currentApplication.identity.currentImgIndex = 0
    	
    	currentApplication.project.comingDateTxt = moment(currentApplication.project.comingDate).format("DD/MM/YYYY");
    	currentApplication.project.trainingStartTxt = moment(currentApplication.project.trainingStart).format("DD/MM/YYYY");
    	currentApplication.project.images = [];
    	if(currentApplication.type == 'premiere'){
    		currentApplication.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com.png", title: "project.form.inscriptionCertificate"});
        	currentApplication.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com.png", title: "project.form.resourceProof"});
        } else {
    		currentApplication.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com_renouvellement.png", title: "project.form.inscriptionCertificate"});
    		currentApplication.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com_renouvellement.png", title: "project.form.resourceProof"});
    	}
    	currentApplication.project.currentImg = currentApplication.project.images[0];
    	currentApplication.project.currentImgIndex = 0
    	
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
            Application.admissibility($scope.application.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
