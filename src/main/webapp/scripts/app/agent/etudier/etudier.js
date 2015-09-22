'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('agent/Agent', {
                parent: 'agent',
                url: '/etudier',
                data: {
                    roles: ['ROLE_AGENT'],
                    pageTitle: 'etudier.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/etudier.html',
                        controller: 'EtudierAgentController'
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
