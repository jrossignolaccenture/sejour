'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/address', {
                parent: 'usager/renouveler',
                url: '/address',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/address/address.html',
                        controller: 'RenouvelerAddressController'
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
