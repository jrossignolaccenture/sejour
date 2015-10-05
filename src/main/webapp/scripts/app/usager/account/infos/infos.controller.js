'use strict';

angular.module('sejourApp')
    .controller('AccountInfosController', function ($scope, $state, Principal, Auth, Country) {
    	$scope.error = null;
    	
        $scope.countries = [];

        $scope.birthDatePickerOptions = {
			format: 'DD/MM/YYYY',
			maxDate: 'moment', 
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.identity = account.identity;
            if($scope.identity != null){
            	$scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
            }
            
            if($scope.account.comingDate != null){
            	$scope.account.comingDateTxt = moment($scope.account.comingDate).format("DD/MM/YYYY");
            }
            
            Country.get().then(function(result) {
            	$scope.countries = result;
            });
        });
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	$scope.account.comingDate = moment($scope.account.comingDateTxt, "DD/MM/YYYY").toDate();
        	console.log($scope.account);
        	Auth.updateAccount($scope.account).then(function(result){
        		$scope.error = null;
            	$state.go('usager/account');
            }).catch(function() {
                $scope.error = 'ERROR';
            });
        };
        
    });
