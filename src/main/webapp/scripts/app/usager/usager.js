'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager', {
                abstract: true,
                parent: 'site'
            });
    });
