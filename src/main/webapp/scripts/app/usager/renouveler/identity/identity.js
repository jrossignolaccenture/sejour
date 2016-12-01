'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/identity', {
                parent: 'usager/renouveler',
                url: '/identity',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/identity/identity.html',
                        controller: 'RenouvelerIdentityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                        return $translate.refresh();
                    }]
                }
            });
    });
