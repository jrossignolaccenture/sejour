'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/validation', {
                parent: 'agent/etudier',
                url: '/validation',
                data: {
                    roles: ['ROLE_AGENT'],
                    pageTitle: 'etudier.validation.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/validation/validation.html',
                        controller: 'ValidationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        return $translate.refresh();
                    }]
                }
            });
    });
