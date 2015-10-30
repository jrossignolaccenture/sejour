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
        $scope.identity.currentImg = getImgSrc($scope.identity.documents[0]);
        $scope.identity.currentImgIndex = 0;

    	$scope.address = currentApplication.address;
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = moment(currentApplication.project.comingDate).format("DD/MM/YYYY");
        $scope.project.trainingStartTxt = moment(currentApplication.project.trainingStart).format("DD/MM/YYYY");
    	$scope.project.currentImg = getImgSrc($scope.project.documents[0]);
    	$scope.project.currentImgIndex = 0;
    	
    	$scope.application = currentApplication;
    	
    	function getImgSrc(document) {
    		var extension = document.name.substring(document.name.lastIndexOf("."));
    		return {title: 'sejourApp.DocumentType.'+document.type, src: "assets/fileUpload/" + document.type + "_" + currentApplication.email + document.id + extension};
    	}
    	
    	$scope.nextImg = function (element) {
            element.currentImgIndex++;
            if (element.currentImgIndex >= element.documents.length) {
                element.currentImgIndex = 0;
            }
            element.currentImg = getImgSrc(element.documents[element.currentImgIndex]);
        },
        $scope.previousImg = function (element) {
            element.currentImgIndex--;
            if (element.currentImgIndex < 0) {
                element.currentImgIndex = element.documents.length - 1;
            }
            element.currentImg = getImgSrc(element.documents[element.currentImgIndex]);
        }
        
        $scope.verify = function () {
            Application.admissibility(currentApplication.id).then(function(result) {
            	$state.go('admissibility/list');
            });
        }
    });
