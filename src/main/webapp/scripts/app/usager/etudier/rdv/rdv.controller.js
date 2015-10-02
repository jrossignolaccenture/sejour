'use strict';

angular.module('sejourApp')
	.constant('HOUR_VALUES',["09:00", "09:15", "09:30", "09:45",
	                     	"10:00", "10:15", "10:30", "10:45",
	                    	"11:00", "11:15", "11:30", "11:45",
	                    	"14:00", "14:15", "14:30", "14:45",
	                    	"15:00", "15:15", "15:30", "15:45",
	                    	"16:00", "16:15", "16:30", "16:45",
	                    	"17:00", "17:15", "17:30", "17:45",])
    .controller('RdvController', function ($scope, $state, Principal, Demande, Rdv, HOUR_VALUES) {
        
    	$scope.days = [];
    	$scope.currentDay = "";
    	$scope.hours = [];
    	$scope.currentHours = [];
    	$scope.currentHour = "";
    	
    	/** génération d'heure aléatoires **/
    	var getRandomHours = function() {
    		var hours = [];
    		var hour;
    		for(var i=0; i<10; i++) {
    			hour = HOUR_VALUES[Math.floor((Math.random() * (HOUR_VALUES.length - 1)) + 1)];
    			if(hours.indexOf(hour) == -1){
    				hours.push(hour);
    			}
        	}
    		hours.sort()
    		return hours;
    	}
    	
    	/** génération de dates aléatoires avec 1ere date sélectionnée par défaut (currentDay) **/
    	var getRandomDates = function() {
    		var dates = [];
    		var current = moment().millisecond(0).second(0).minute(0).hour(0).add(5, 'd');
    		for(var i=0; i<15; i++) {
    			current.add(Math.floor((Math.random() * 4) + 1), 'd');
    			if(current.days() == 0) { current.add(1, 'd')} else if(current.days() == 6) { current.add(2, 'd')}
    			dates.push(current);
    			$scope.days.push(current.valueOf());
        		$scope.hours.push(getRandomHours());
    			current = moment(current);
    		}
    		$scope.currentDay = dates[0].format("DD/MM/YYYY");
    		$scope.currentHours = $scope.hours[0];
    		
    		return dates;
    	}
    	
    	$scope.datePickerOptions = {
    		inline: true,
			format: 'DD/MM/YYYY',
			locale: 'fr',
			minDate: "moment",
			enabledDates: getRandomDates()
        }
    	
    	$scope.changeHours = function(d) {
    		$scope.currentHour = "";
    		if(d) {
	    		$scope.currentHours = $scope.hours[$scope.days.indexOf(d.valueOf())];
    		}
    	}
    	
    	$scope.selectHour = function(h) {
    		$scope.currentHour = h;
    	}
        

        $scope.loadAll = function () {
// TROP LONG A INTEGRER CAR BINDING BIZARRE AVEC ENABLEDDATES
//    		Rdv.getRdvTimeSlot($scope.account.email).then(function(result) {
//    			var first = true;
//    			for(var day in result){
//    				$scope.enabledDates.push(moment(day).format("YYYY-MM-DD"));
//    				if(first) {
//    					$scope.currentDay = moment(day).format("DD/MM/YYYY");
//    					$scope.hours = result[day];
//    					first = false
//    				}
//    			}
//    			console.log($scope.enabledDates);
//            	console.log(result);
//            });
            Demande.getDemandeForRdv($scope.account.email).then(function(result) {
            	$scope.demande = result;
            });
        };
        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.loadAll();
        });
        
        $scope.save = function () {
        	$scope.demande.rdvDate = moment($scope.currentDay + " " + $scope.currentHour, "DD/MM/YYYY hh:mm").toDate();
        	console.log($scope.demande);
            Demande.rdv($scope.demande).then(function(result){
            	$state.go('account/application');
            });
        };
        
    });
