'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('campus', {
                parent: 'demandes',
                url: '/etudier',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/etudier.html',
                        controller: 'EtudierController'
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
