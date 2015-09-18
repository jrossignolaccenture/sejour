'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('summary', {
                parent: 'etudier',
                url: '/summary',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.summary.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/summary/summary.html',
                        controller: 'SummaryController'
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
