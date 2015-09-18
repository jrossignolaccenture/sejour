'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'etudier',
                url: '/address',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.address.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/address/address.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etudier');
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }]
                }
            });
    });
