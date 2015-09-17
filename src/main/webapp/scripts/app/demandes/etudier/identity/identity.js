'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('identity', {
                parent: 'etudier',
                url: '/identity',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.identity.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/identity/identity.html',
                        controller: 'IdentityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etudier');
                        $translatePartialLoader.addPart('sexType');
                        return $translate.refresh();
                    }]
                }
            });
    });
