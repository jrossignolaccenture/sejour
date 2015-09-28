'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('etudier/validation/list', {
	            parent: 'agent/etudier',
	            url: '/validation',
	            data: {
	                roles: ['ROLE_AGENT']
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/agent/etudier/validation/validation_list.html',
	                    controller: 'ValidationListController'
	                }
	            },
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('validation');
	                	$translatePartialLoader.addPart('natureDemande');
	                	$translatePartialLoader.addPart('typeDemande');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('etudier/validation', {
                parent: 'agent/etudier',
                url: '/validation/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/validation/validation.html',
                        controller: 'ValidationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('validation');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }],
                    currentDemande: ['$stateParams', 'Demande', function($stateParams, Demande) {
                        return Demande.get({id : $stateParams.id});
                    }]
                }
            });
    });
