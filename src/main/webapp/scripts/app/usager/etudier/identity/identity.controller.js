'use strict';

angular.module('sejourApp')
    .controller('IdentityController', function ($scope, $state, Principal, Demande, Country, File) {
        $scope.identity = {};
        $scope.countries = [];
        $scope.passportFile = {};
        $scope.birthActFile = {};
        
        $scope.birthDatePickerOptions = {
			format: 'DD/MM/YYYY',
			maxDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        

        $scope.loadAll = function () {
            Demande.getInProgressDemande($scope.account.email).then(function(result) {
            	$scope.demande = result;
                $scope.identity = result.identity;
                if($scope.identity != null && $scope.identity.birthDate != null){
                	console.log("ici");
                	$scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
                }
            });
            
            Country.get().then(function(result) {
            	$scope.countries = result;
            });
        };
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.loadAll();
        });
        
        $scope.uploadPassportFile = function(element){
        	$scope.$apply(function(scope) {
                if(element.files.length > 0){
                	File.upload(element.files[0], "passport")
				        .success(function(){
				        	scope.passportFile = element.files[0];
		                	scope.identity.passport = element.files[0].name;
				        })
				        .error(function(){
				        });
                } else {
                	scope.passportFile = {};
                	scope.identity.passport = "";
                }
			});
        };
        
        $scope.uploadBirthActFile = function(element){
        	$scope.$apply(function(scope) {
                if(element.files.length > 0){
                	File.upload(element.files[0], "birthAct")
				        .success(function(){
	                		scope.birthActFile = element.files[0];
	                    	scope.identity.birthAct = element.files[0].name;
				        })
				        .error(function(){
				        });
                	
                } else {
                	scope.birthActFile = {};
                	scope.identity.birthAct = "";
                }
			});
        };
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	$scope.demande.identity = $scope.identity;
        	console.log($scope.demande);
            Demande.update($scope.demande, false).then(function(result){
            	$state.go('etudier/address');
            });
        };
        
    });
