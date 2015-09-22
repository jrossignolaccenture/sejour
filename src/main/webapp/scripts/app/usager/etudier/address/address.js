'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/address', {
                parent: 'usager/etudier',
                url: '/address',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.address.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/address/address.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }]
                }
            });
    });
