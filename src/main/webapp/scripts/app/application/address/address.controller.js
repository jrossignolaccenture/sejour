'use strict';

angular.module('sejourApp')
    .controller('AddressController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
    	$scope.address = currentApplication.address;

        $scope.back = function () {
        	if(currentApplication.nature === 'naturalisation') {
        		$state.go('family', $stateParams);
        	} else {
        		$state.go('identity', $stateParams);
        	}
        };
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('project', $stateParams);
            });
        };
        
    });
