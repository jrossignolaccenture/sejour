'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('summary', {
                parent: 'site',
                url: '/{base}/{id}/resume',
                data: {
                    roles: ['ROLE_USAGER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/summary/summary.html',
                        controller: 'SummaryController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.deletePart($stateParams.base, true); // tips to get high priority to this part
                    	$translatePartialLoader.addPart($stateParams.base);
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
