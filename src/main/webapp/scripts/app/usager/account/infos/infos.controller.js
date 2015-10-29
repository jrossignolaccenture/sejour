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
        
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.identity = account.identity;
            if($scope.identity != null){
            	$scope.identity.birthDateTxt = moment($scope.identity.birthDate).format("DD/MM/YYYY");
            }
            if($scope.account.comingDate != null){
            	$scope.account.comingDateTxt = moment($scope.account.comingDate).format("DD/MM/YYYY");
            }
            if($scope.account.frenchAddress == null) {
            	$scope.account.frenchAddress = { contactType: [] };
            }
            //TODO  A factoriser avec le code de address et peut etre summary
            $scope.contactTypeValues = [ { value: 'email', checked: $scope.account.frenchAddress.contactType.indexOf('email') != -1 },
                                         { value: 'sms', checked: $scope.account.frenchAddress.contactType.indexOf('sms') != -1 },
                                         { value: 'postal', checked: $scope.account.frenchAddress.contactType.indexOf('postal') != -1 },
                                   ];
        	$scope.updateContactType = function(contactType) {
        		var index = $scope.account.frenchAddress.contactType.indexOf(contactType.value);
        		if(contactType.checked && index == -1) {
        			$scope.account.frenchAddress.contactType.push(contactType.value);
                }else if(!contactType.checked && index != -1) {
                	$scope.account.frenchAddress.contactType.splice(index, 1);
                }
        	}
        });
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	$scope.account.comingDate = moment($scope.account.comingDateTxt, "DD/MM/YYYY").toDate();
        	Auth.updateAccount($scope.account).then(function(result){
        		$scope.error = null;
            	$state.go('usager/account');
            }).catch(function() {
                $scope.error = 'ERROR';
            });
        };
        
    });
