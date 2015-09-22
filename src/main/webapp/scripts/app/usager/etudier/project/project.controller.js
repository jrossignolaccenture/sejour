'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, Principal, Demande, File) {
        $scope.project = {};
        $scope.resourceProofFile = {};
        $scope.project.resourceProof = '';
        $scope.inscriptionCertificateFile = {};
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			viewMode: 'years', 
			allowInputToggle: true
        }

        $scope.loadAll = function () {
            Demande.getInProgressDemande($scope.account.email).then(function(result) {
            	$scope.demande = result;
                $scope.project = result.project;
                console.log($scope.demande);
                if($scope.project.comingDate != null){
                	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
                }
                if($scope.project.trainingStart != null){
                	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
                }
            });
        };
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.loadAll();
        });
        
        $scope.uploadResourceProofFile = function(element){
        	$scope.$apply(function(scope) {
                if(element.files.length > 0){
                	File.upload(element.files[0], "resourceProof")
				        .success(function(){
				        	scope.resourceProofFile = element.files[0];
		                	scope.project.resourceProof = element.files[0].name;
				        })
				        .error(function(){
				        });
                } else {
                	scope.resourceProofFile = {};
                	scope.project.resourceProof = "";
                }
			});
        };
        
        $scope.uploadInscriptionCertificateFile = function(element){
        	$scope.$apply(function(scope) {
                if(element.files.length > 0){
                	File.upload(element.files[0], "inscriptionCertificate")
				        .success(function(){
	                		scope.inscriptionCertificateFile = element.files[0];
	                    	scope.project.inscriptionCertificate = element.files[0].name;
				        })
				        .error(function(){
				        });
                	
                } else {
                	scope.inscriptionCertificateFile = {};
                	scope.project.inscriptionCertificate = "";
                }
			});
        };
        
        $scope.save = function () {
            $scope.project.comingDate = moment($scope.project.comingDateTxt, "DD/MM/YYYY").toDate();
            $scope.project.trainingStart = moment($scope.project.trainingStartTxt, "DD/MM/YYYY").toDate();
        	console.log($scope.demande);
            Demande.update($scope.demande, true).then(function(result){
            	$state.go('etudier/payment');
            });
        };
        
    });
