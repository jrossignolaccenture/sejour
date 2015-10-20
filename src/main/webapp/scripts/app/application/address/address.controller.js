'use strict';

angular.module('sejourApp')
    .controller('AddressController', function ($scope, $state, $stateParams, Country, Application, currentApplication) {
        
    	$scope.address = currentApplication.address;

        $scope.countries = [];
        Country.get().then(function(countries) {
        	$scope.countries = countries;
        });
        
        $scope.save = function () {
        	currentApplication.address = $scope.address;
            Application.update(currentApplication).then(function() {
            	$state.go('project', $stateParams);
            });
        };
        
    });
