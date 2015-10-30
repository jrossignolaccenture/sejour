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
        $scope.identity.currentImg = getImgSrc($scope.identity.documents[0]);
        $scope.identity.currentImgIndex = 0;
    	
        $scope.project = currentApplication.project;
        $scope.project.comingDateTxt = $scope.getFormattedDate(currentApplication.project.comingDate);
        $scope.project.trainingStartTxt = $scope.getFormattedDate(currentApplication.project.trainingStart);
    	$scope.project.currentImg = getImgSrc($scope.project.documents[0]);
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
        
        $scope.validate = function () {
    		Application.validate(currentApplication.id).then(function(result) {
            	$state.go('validation/list')
            });
        }
    });
