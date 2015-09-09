'use strict';

angular.module('sejourApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


