'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('issuing/list', {
	            parent: 'agent',
	            url: '/delivrance',
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
	                    return Application.getApplicationsForPermitIssuing();
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('issuing');
	                	$translatePartialLoader.addPart('applicationNature');
	                	$translatePartialLoader.addPart('applicationType');
	                    return $translate.refresh();
	                }]
	            }
	        })
            .state('issuing', {
                parent: 'agent',
                url: '/delivrance/{id}',
                data: {
                    roles: ['ROLE_AGENT']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/issuing/issuing.html',
                        controller: 'IssuingController'
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
                    	$translatePartialLoader.addPart('issuing');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('maritalStatus');
                    	$translatePartialLoader.addPart('activityType');
                    	$translatePartialLoader.addPart('resourceType');
                    	$translatePartialLoader.addPart('documentType');
                        return $translate.refresh();
                    }]
                }
            });
    });
