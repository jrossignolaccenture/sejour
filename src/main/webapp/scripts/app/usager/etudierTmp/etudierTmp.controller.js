'use strict';

angular.module('sejourApp')
    .controller('EtudierTmpUsagerController', function ($rootScope, $scope, $state, Application) {

    	var account = $rootScope.account;
		$scope.hasIdentityData = account !== null && account !== undefined 
									&& account.identity !== null && account.identity !== undefined
									&& (account.identity.valid === true || (account.identity.validateOn !== null && account.identity.validateOn !== undefined));
		$scope.hasAddressData = account !== null && account !== undefined 
									&& account.address !== null && account.address !== undefined
									&& (account.address.valid === true || (account.address.validateOn !== null && account.address.validateOn !== undefined));
    	
    	
        $scope.createApplication = function() {
            Application.create($state.current.data.type, $state.current.data.nature).then(function(id) {
            	$state.go('identity', {id: id});
            });
        }
    });
