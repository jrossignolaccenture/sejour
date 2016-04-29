'use strict';

angular.module('sejourApp')
    .controller('RdvController', function ($scope, $state, Application, Rdv, currentApplication) {

    	$scope.datePickerOptions = {
    		inline: true,
			format: 'DD/MM/YYYY',
			locale: 'fr',
			minDate: "moment",
			enabledDates: Rdv.getRandomDates()
        }
    	
    	$scope.isRdvInFrance = currentApplication.nature === 'naturalisation' || currentApplication.nature === 'sejour_tmp_etudiant';

    	$scope.days = [];
    	$scope.hours = [];
    	$scope.datePickerOptions.enabledDates.forEach(function(date) {
    		$scope.days.push(date.valueOf());
    		$scope.hours.push(Rdv.getRandomHours());
    	});
    	$scope.currentDay = $scope.datePickerOptions.enabledDates[0].format("DD/MM/YYYY");
		$scope.currentHours = $scope.hours[0];
		$scope.currentHour = '';
    	
    	$scope.onSelectDate = function(date) {
    		$scope.currentHour = '';
    		$scope.currentHours = date ? $scope.hours[$scope.days.indexOf(date.valueOf())] : '';
    	}
    	
    	$scope.onSelectHour = function(hour) {
    		$scope.currentHour = hour;
    	}
        
        $scope.save = function () {
        	currentApplication.rdvDate = moment($scope.currentDay + " " + $scope.currentHour, "DD/MM/YYYY hh:mm").toDate();
            Application.schedule(currentApplication).then(function(result) {
            	$state.go('account/application');
            });
        };
        
    });
