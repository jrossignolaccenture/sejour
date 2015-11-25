'use strict';

angular.module('sejourApp')
    .controller('AccountInfosController', function ($scope, $state, Principal, Auth) {
    	$scope.error = null;

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
            if($scope.account.address == null) {
            	$scope.account.address = { contactType: [] };
            }
            // TODO  A factoriser avec le code de address et peut etre summary
            $scope.contactTypeValues = [ { value: 'email', checked: $scope.account.address.contactType.indexOf('email') != -1 },
                                         { value: 'sms', checked: $scope.account.address.contactType.indexOf('sms') != -1 },
                                         { value: 'postal', checked: $scope.account.address.contactType.indexOf('postal') != -1 },
                                   ];
        	$scope.updateContactType = function(contactType) {
        		var index = $scope.account.address.contactType.indexOf(contactType.value);
        		if(contactType.checked && index == -1) {
        			$scope.account.address.contactType.push(contactType.value);
                }else if(!contactType.checked && index != -1) {
                	$scope.account.address.contactType.splice(index, 1);
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
