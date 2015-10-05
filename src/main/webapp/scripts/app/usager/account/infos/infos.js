'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('account/infos', {
                parent: 'usager/account',
                url: '/infos',
                data: {
                    roles: ['ROLE_USAGER'],
                    pageTitle: 'infos.page.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/account/infos/infos.html',
                        controller: 'AccountInfosController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('infos');
                        $translatePartialLoader.addPart('identity');
                        $translatePartialLoader.addPart('sexType');
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }]
                }
            });
    });
