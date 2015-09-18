'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('recap', {
                parent: 'etudier',
                url: '/recap',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.recap.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/recap/recap.html',
                        controller: 'RecapController'
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
