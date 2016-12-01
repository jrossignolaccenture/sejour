'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/summary', {
                parent: 'usager/renouveler',
                url: '/summary',
                data: {
                    roles: ['ROLE_USAGER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/summary/summary.html',
                        controller: 'RenouvelerSummaryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('summary');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('contactType');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
