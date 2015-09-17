'use strict';

angular.module('sejourApp')
    .factory('Identity', function ($resource, DateUtils) {
        return $resource('api/identitys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.birthDate = DateUtils.convertDateTimeFromServer(data.birthDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
