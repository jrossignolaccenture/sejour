'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('summary', {
                parent: 'site',
                url: '/{id}/resume',
                data: {
                    roles: ['ROLE_USAGER', 'ROLE_AGENT'],
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
                    translatePartialLoader: ['$translate', '$translatePartialLoader', 'I18N_APPLICATION', 'currentApplication',
                                             function ($translate, $translatePartialLoader, I18N_APPLICATION, currentApplication) {
                    	// tips to get high priority to the I18N_APPLICATION part
                    	$translatePartialLoader.deletePart(I18N_APPLICATION[currentApplication.type][currentApplication.nature], true);
                    	$translatePartialLoader.addPart(I18N_APPLICATION[currentApplication.type][currentApplication.nature]);
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('personType');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('maritalStatus');
                    	$translatePartialLoader.addPart('activityType');
                    	$translatePartialLoader.addPart('contactType');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('resourceType');
                    	$translatePartialLoader.addPart('documentType');
                        return $translate.refresh();
                    }]
                }
            });
    });
