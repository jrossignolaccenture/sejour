'use strict';

angular.module('sejourApp')
    .controller('SummaryController', function ($scope, $state, $stateParams, Country, currentApplication) {

    	Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
    	
    	$scope.isInDraftMode = currentApplication.statut === 'draft';
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        
        $scope.identity = currentApplication.identity;
        $scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
        $scope.identity.images = [];
        $scope.identity.images.push({src: "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg", title: "identity.form.passport"});
        $scope.identity.images.push({src: "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png", title: "identity.form.birthAct"});
        $scope.identity.currentImg = $scope.identity.images[0];
        $scope.identity.currentImgIndex = 0;
        
        $scope.address = currentApplication.address;
        
        $scope.project = currentApplication.project;
    	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
    	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
    	$scope.project.images = [];
    	$scope.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com.png", title: "project.form.inscriptionCertificate"});
    	$scope.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com.png", title: "project.form.resourceProof"});
    	$scope.project.currentImg = $scope.project.images[0];
    	$scope.project.currentImgIndex = 0;
    	
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
        
        $scope.next = function () {
            $state.go('payment', $stateParams);
        };
        
    });
