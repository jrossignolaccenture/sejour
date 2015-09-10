'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('demandes', {
                abstract: true,
                parent: 'site'
            });
    });
