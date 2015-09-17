'use strict';

angular.module('sejourApp')
    .factory('Country', function ($http, Language) {
        return {
            get: function () {
            	return Language.getCurrent().then(function(current) {
                    return $http.get('country/country_'+current+'.json').then(function (response) {
                        return response.data;
                    });
                });
                
            }
        };
    });
