'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('etudier/identification', {
	            parent: 'agent/etudier',
	            url: '/identification',
	            data: {
	                roles: ['ROLE_AGENT']
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/agent/etudier/identification/identification.html',
	                    controller: 'IdentificationController'
	                }
	            },
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('identification');
                    	$translatePartialLoader.addPart('natureDemande');
                    	$translatePartialLoader.addPart('typeDemande');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('etudier/documents', {
                parent: 'agent/etudier',
                url: '/documents/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/identification/documents.html',
                        controller: 'DocumentsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('identification');
                        return $translate.refresh();
                    }],
                    currentDemande: ['$stateParams', 'Demande', function($stateParams, Demande) {
                        return Demande.get({id : $stateParams.id});
                    }]
                }
            })
            .state('etudier/biometrics', {
                parent: 'agent/etudier',
                url: '/biometrics/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/identification/biometrics.html',
                        controller: 'BiometricsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('identification');
                        return $translate.refresh();
                    }],
                    currentDemande: ['$stateParams', 'Demande', function($stateParams, Demande) {
                        return Demande.get({id : $stateParams.id});
                    }]
                }
            });
    });
