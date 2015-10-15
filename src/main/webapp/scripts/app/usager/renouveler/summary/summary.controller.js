'use strict';

angular.module('sejourApp')
    .controller('RenouvelerSummaryController', function ($scope, $state, Principal, Demande, Country) {
    	
    	$scope.identity = {
			images: [], 
			currentImg: {},
			currentImgIndex: 0
        };
    	$scope.project = {
			images: [], 
			currentImg: {},
			currentImgIndex: 0
    	};
    	
        Country.get().then(function(result) {
        	$scope.countries = result;
        });
        
        $scope.loadAll = function () {
            Demande.getInProgressDemande($scope.account.email).then(function(result) {
            	$scope.demande = result;
            	$scope.demande.project.comingDateTxt = moment(result.project.comingDate).format("DD/MM/YYYY");
            	$scope.demande.project.trainingStartTxt = moment(result.project.trainingStart).format("DD/MM/YYYY");
            	$scope.demande.identity.birthDateTxt = moment(result.identity.birthDate).format("DD/MM/YYYY");
            	$scope.studentName = result.identity.firstName + " " + result.identity.lastName;
            	$scope.project.images.push({src: "assets/fileUpload/inscriptionCertificate_kim.soon.jeen@gmail.com_renouvellement.png", title: "project.form.inscriptionCertificate"});
            	$scope.project.images.push({src: "assets/fileUpload/resourceProof_kim.soon.jeen@gmail.com_renouvellement.png", title: "project.form.resourceProof"});
            	$scope.project.currentImg = $scope.project.images[$scope.project.currentImgIndex];
            	$scope.identity.images.push({src: "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg", title: "identity.form.passport"});
            	$scope.identity.images.push({src: "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png", title: "identity.form.birthAct"});
            	$scope.identity.currentImg = $scope.identity.images[$scope.identity.currentImgIndex];
            });
        };
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.loadAll();
        });
    	
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
        
        $scope.save = function () {
        	console.log($scope.demande);
            Demande.update($scope.demande, true).then(function(result){
            	$state.go('renouveler/payment');
            });
        };
        
    });
