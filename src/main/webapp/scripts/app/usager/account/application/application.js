'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('account/application', {
	            parent: 'usager/account',
	            url: '/application',
	            data: {
	                roles: ['ROLE_USAGER'],
                    pageTitle: 'application.page.title'
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/usager/account/application/application.html',
	                    controller: 'ApplicationController'
	                }
	            },
	            resolve: {
	            	userApplications: ['Application', function(Application) {
                        return Application.get();
                    }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('application');
	                    return $translate.refresh();
	                }]
	            }
	        });
    });
