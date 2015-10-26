'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, $stateParams, Country, File, Application, currentApplication) {
        $scope.passportFile = {};
        $scope.birthActFile = {};
        
        $scope.birthDatePickerOptions = {
			format: 'DD/MM/YYYY',
			maxDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        $scope.isRenewal = currentApplication.type === 'renouvellement';
        
        $scope.identity = currentApplication.identity;
        if($scope.identity != null && $scope.identity.birthDate != null) {
        	$scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
        }
        
        $scope.countries = [];
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });

        
        $scope.uploadPassportFile = function(element) {
        	$scope.$apply(function(scope) {
                if(element.files.length > 0) {
                	File.upload(element.files[0], "passport")
				        .success(function() {
				        	scope.passportFile = element.files[0];
		                	scope.identity.passport = element.files[0].name;
				        })
				        .error(function() {
				        });
                } else {
                	scope.passportFile = {};
                	scope.identity.passport = "";
                }
			});
        };
        
        $scope.uploadBirthActFile = function(element) {
        	$scope.$apply(function(scope) {
                if(element.files.length > 0) {
                	File.upload(element.files[0], "birthAct")
				        .success(function() {
	                		scope.birthActFile = element.files[0];
	                    	scope.identity.birthAct = element.files[0].name;
				        })
				        .error(function() {
				        });
                	
                } else {
                	scope.birthActFile = {};
                	scope.identity.birthAct = "";
                }
			});
        };
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	currentApplication.identity = $scope.identity;
            Application.update(currentApplication).then(function() {
            	$state.go('address', $stateParams);
            });
        };
        
    });
