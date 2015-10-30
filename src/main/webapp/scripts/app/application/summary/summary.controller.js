'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, Country, currentApplication) {

    	Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        $scope.viewSuffix = currentApplication.type === 'naturalisation' ? '-naturalization' : '';
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
        $scope.identity.currentImg = getImgSrc($scope.identity.documents[0]);
        $scope.identity.currentImgIndex = 0;
        
        $scope.address = currentApplication.address;
        
        $scope.project = currentApplication.project;
    	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
    	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
    	$scope.project.currentImg = getImgSrc($scope.project.documents[0]);
    	$scope.project.currentImgIndex = 0;
    	
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

        $scope.back = function () {
        	var stateToGo = currentApplication.statut === 'draft' ? 'project' : 'account/application';
        	$state.go(stateToGo, $stateParams);
        };
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
