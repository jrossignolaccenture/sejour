'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/address', {
                parent: 'usager/etudier',
                url: '/address',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/address/address.html',
                        controller: 'AddressController'
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
