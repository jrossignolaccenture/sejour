'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('agent', {
                abstract: true,
                parent: 'site'
            });
    });
