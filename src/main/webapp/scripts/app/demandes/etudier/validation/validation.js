'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('validation', {
                parent: 'etudier',
                url: '/validation',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.validation.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/validation/validation.html',
                        controller: 'ValidationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etudier');
                        return $translate.refresh();
                    }]
                }
            });
    });
