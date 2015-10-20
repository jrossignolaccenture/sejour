'use strict';

angular.module('sejourApp')
	.constant('HOUR_VALUES',["09:00", "09:15", "09:30", "09:45",
	                     	 "10:00", "10:15", "10:30", "10:45",
	                    	 "11:00", "11:15", "11:30", "11:45",
	                    	 "14:00", "14:15", "14:30", "14:45",
	                    	 "15:00", "15:15", "15:30", "15:45",
	                    	 "16:00", "16:15", "16:30", "16:45",
	                    	 "17:00", "17:15", "17:30", "17:45",])
    .factory('Rdv', function ($http, HOUR_VALUES) {
        return {
            getRdvTimeSlot: function (email) {
            	return $http.get('api/rdv', {params: {email: email}}).then(function (response) {
                    return response.data;
                });
            },
            /** génération d'heure aléatoires **/
            getRandomHours: function() {
            	var hours = [];
            	var hour;
            	for(var i=0; i<10; i++) {
            		hour = HOUR_VALUES[Math.floor((Math.random() * (HOUR_VALUES.length - 1)) + 1)];
            		if(hours.indexOf(hour) == -1) {
            			hours.push(hour);
            		}
            	}
            	hours.sort();
            	return hours;
            },
            /** génération de dates aléatoires avec 1ere date sélectionnée par défaut (currentDay) **/
            getRandomDates: function() {
            	var dates = [];
            	var current = moment().millisecond(0).second(0).minute(0).hour(0).add(5, 'd');
            	for(var i=0; i<15; i++) {
            		current.add(Math.floor((Math.random() * 4) + 1), 'd');
            		if(current.days() == 0) {
            			current.add(1, 'd')
            		} else if(current.days() == 6) {
            			current.add(2, 'd')
            		}
            		dates.push(current);
            		current = moment(current);
            	}
            	
            	return dates;
            }
        };
    });