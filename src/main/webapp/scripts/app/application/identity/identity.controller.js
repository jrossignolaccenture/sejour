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
        
        $scope.save = function () {
        	$scope.identity.birthDate = moment($scope.identity.birthDateTxt, "DD/MM/YYYY").toDate();
        	currentApplication.identity = $scope.identity;
            Application.update(currentApplication).then(function() {
            	$state.go('address', $stateParams);
            });
        };
        
    });
