'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('validation/list', {
	            parent: 'agent',
	            url: '/validation',
	            data: {
	                roles: ['ROLE_AGENT'],
	                listType: 'validation'
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/components/application/application_list.html',
	                    controller: 'ApplicationListController'
	                }
	            },
	            resolve: {
	            	applications: ['$stateParams', 'Application', function($stateParams, Application) {
	                    return Application.getByStatus('identity_verified');
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('validation');
	                	$translatePartialLoader.addPart('applicationNature');
	                	$translatePartialLoader.addPart('applicationType');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('validation', {
                parent: 'agent',
                url: '/validation/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/validation/validation.html',
                        controller: 'ValidationController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('validation');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
