'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/final', {
                parent: 'usager/renouveler',
                url: '/final',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/final/final.html',
                        controller: 'RenouvelerFinalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('final');
                        return $translate.refresh();
                    }]
                }
            });
    });
