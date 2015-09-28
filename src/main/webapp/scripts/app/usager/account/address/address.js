'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('account/address', {
                parent: 'usager/account',
                url: '/address',
                data: {
                    roles: ['ROLE_USAGER'],
                    pageTitle: 'address.french.page.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/account/address/address.html',
                        controller: 'AccountAddressController'
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
