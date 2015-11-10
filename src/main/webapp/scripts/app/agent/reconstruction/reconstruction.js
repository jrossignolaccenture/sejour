'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('reconstruction/list', {
	            parent: 'agent',
	            url: '/reconstitution',
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
	                    return Application.getApplicationsToReconstruct();
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('reconstruction');
	                	$translatePartialLoader.addPart('applicationNature');
	                	$translatePartialLoader.addPart('applicationType');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('reconstruction', {
                parent: 'agent',
                url: '/reconstruction/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/reconstruction/reconstruction.html',
                        controller: 'ReconstructionController'
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
                    	$translatePartialLoader.addPart('reconstruction');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                        return $translate.refresh();
                    }]
                }
            });
    });
