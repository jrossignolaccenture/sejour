'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'usager',
                url: '/address',
                data: {
                    roles: ['ROLE_USAGER'],
                    pageTitle: 'etudier.address.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/address/address.html',
                        controller: 'UsagerAddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }]
                }
            });
    });
