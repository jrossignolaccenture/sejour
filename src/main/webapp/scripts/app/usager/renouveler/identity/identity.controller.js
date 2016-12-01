'use strict';

angular.module('sejourApp')
    .controller('RenouvelerIdentityController', function ($scope, $state, Principal, Demande, Country, File) {
        $scope.identity = {};
        $scope.countries = [];
        
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
                $scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
            });
            
            Country.get().then(function(result) {
            	$scope.countries = result;
            });
        };
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.loadAll();
        });
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	console.log($scope.demande);
            Demande.update($scope.demande, false).then(function(result){
            	$state.go('renouveler/address');
            });
        };
        
    });
