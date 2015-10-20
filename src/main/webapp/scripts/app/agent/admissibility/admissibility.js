'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('admissibility/list', {
	            parent: 'agent',
	            url: '/recevabilite',
	            data: {
	                roles: ['ROLE_AGENT']
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/components/application/application_list.html',
	                    controller: 'ApplicationListController'
	                }
	            },
	            resolve: {
	            	listType: function() {
	            		return 'admissibility'
	            	},
	            	applications: ['$stateParams', 'Application', function($stateParams, Application) {
	                    return Application.getByStatus('paid');
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('admissibility');
	                	$translatePartialLoader.addPart('natureDemande');
	                	$translatePartialLoader.addPart('typeDemande');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('admissibility', {
                parent: 'agent',
                url: '/recevabilite/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/admissibility/admissibility.html',
                        controller: 'AdmissibilityController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('admissibility');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
