'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/identity', {
                parent: 'usager/etudier',
                url: '/identity',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.identity.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/identity/identity.html',
                        controller: 'IdentityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('sexType');
                        return $translate.refresh();
                    }]
                }
            });
    });
