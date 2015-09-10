'use strict';

angular.module('sejourApp')
    .factory('Demande', function ($resource, DateUtils) {
        return $resource('api/demandes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
