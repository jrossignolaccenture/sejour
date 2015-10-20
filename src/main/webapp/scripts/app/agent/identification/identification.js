'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('identification/list', {
	            parent: 'agent',
	            url: '/identification',
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
	            		return 'identification'
	            	},
	            	applications: ['$stateParams', 'Application', function($stateParams, Application) {
	                    return Application.getByStatus('scheduled');
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('identification');
                    	$translatePartialLoader.addPart('natureDemande');
                    	$translatePartialLoader.addPart('typeDemande');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('documents', {
                parent: 'agent',
                url: '/identification/{id}/documents',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/identification/documents.html',
                        controller: 'DocumentsController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('identification');
                        return $translate.refresh();
                    }]
                }
            })
            .state('biometrics', {
                parent: 'agent',
                url: '/identification/{id}/biometrie',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/identification/biometrics.html',
                        controller: 'BiometricsController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('identification');
                        return $translate.refresh();
                    }]
                }
            });
    });
