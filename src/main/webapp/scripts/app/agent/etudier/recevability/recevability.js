'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/recevability/list', {
                parent: 'agent/etudier',
                url: '/recevability',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/recevability/recevability_list.html',
                        controller: 'RecevabilityListController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('recevability');
                    	$translatePartialLoader.addPart('natureDemande');
                    	$translatePartialLoader.addPart('typeDemande');
                        return $translate.refresh();
                    }]
                }
            })
            .state('etudier/recevability', {
                parent: 'agent/etudier',
                url: '/recevability/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/etudier/recevability/recevability.html',
                        controller: 'RecevabilityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('recevability');
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
