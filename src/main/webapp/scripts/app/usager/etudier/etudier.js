'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/etudier', {
                parent: 'usager',
                url: '/etudier',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/etudier.html',
                        controller: 'EtudierUsagerController'
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
