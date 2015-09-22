'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/summary', {
                parent: 'usager/etudier',
                url: '/summary',
                data: {
                    roles: ['ROLE_USAGER'],
                    pageTitle: 'etudier.summary.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/summary/summary.html',
                        controller: 'SummaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        return $translate.refresh();
                    }]
                }
            });
    });
