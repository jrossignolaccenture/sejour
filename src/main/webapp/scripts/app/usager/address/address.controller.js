'use strict';

angular.module('sejourApp')
    .controller('UsagerAddressController', function ($scope, $state, Principal, Auth) {
    	$scope.error = null;
        
        $scope.datePickerOptions = {
			format: 'DD/MM/YYYY',
			viewMode: 'years', 
			locale: 'fr',
			allowInputToggle: true
        }
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            if($scope.account.comingDate != null){
            	$scope.account.comingDateTxt = moment($scope.account.comingDate).format("DD/MM/YYYY");
            }
        });
        
        $scope.save = function () {
        	$scope.account.comingDate = moment($scope.account.comingDateTxt, "DD/MM/YYYY").toDate();
        	$scope.account.frenchAddress.country = 'FR';
        	console.log($scope.account);
        	Auth.updateAccount($scope.account).then(function(result){
        		$scope.error = null;
            	$state.go('home');
            }).catch(function() {
                $scope.error = 'ERROR';
            });
        };
        
    });
