'use strict';

angular.module('sejourApp')
    .controller('ProjectController', function ($scope, $state, $stateParams, File, Application, currentApplication) {
        $scope.resourceProofFile = {};
        $scope.inscriptionCertificateFile = {};
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			minDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';

        $scope.project = currentApplication.project;
        if($scope.project != null && $scope.project.comingDate != null) {
        	$scope.project.comingDateTxt = moment($scope.project.comingDate).format("DD/MM/YYYY");
        }
        if($scope.project != null && $scope.project.trainingStart != null) {
        	$scope.project.trainingStartTxt = moment($scope.project.trainingStart).format("DD/MM/YYYY");
        }
        
        $scope.uploadResourceProofFile = function(element) {
        	$scope.$apply(function(scope) {
                if(element.files.length > 0) {
                	File.upload(element.files[0], "resourceProof")
				        .success(function() {
				        	scope.resourceProofFile = element.files[0];
		                	scope.project.resourceProof = element.files[0].name;
				        })
				        .error(function() {
				        });
                } else {
                	scope.resourceProofFile = {};
                	scope.project.resourceProof = "";
                }
			});
        };
        
        $scope.uploadInscriptionCertificateFile = function(element) {
        	$scope.$apply(function(scope) {
                if(element.files.length > 0) {
                	File.upload(element.files[0], "inscriptionCertificate")
				        .success(function() {
	                		scope.inscriptionCertificateFile = element.files[0];
	                    	scope.project.inscriptionCertificate = element.files[0].name;
				        })
				        .error(function() {
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
            currentApplication.project = $scope.project;
            Application.update(currentApplication).then(function() {
            	$state.go('summary', $stateParams);
            });
        };
        
    });
