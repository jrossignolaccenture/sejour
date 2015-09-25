'use strict';

angular.module('sejourApp')
    .factory('Rdv', function ($http) {
        return {
            getRdvTimeSlot: function (email) {
            	return $http.get('api/rdv', {params: {email: email}}).then(function (response) {
                    return response.data;
                });
            }
        };
    });
