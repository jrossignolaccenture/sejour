'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('receipt', {
	            parent: 'agent',
	            url: '/reception',
	            data: {
	                roles: ['ROLE_AGENT']
	            },
	            views: {
	                'content@': {
	                    templateUrl: 'scripts/app/agent/receipt/receipt.html',
	                    controller: 'ReceiptController'
	                }
	            },
	            resolve: {
	            	applications: ['$stateParams', 'Application', function($stateParams, Application) {
	                    return Application.getApplicationsForPermitReceipt();
	                }],
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('receipt');
	                	$translatePartialLoader.addPart('applicationNature');
	                	$translatePartialLoader.addPart('applicationType');
	                    return $translate.refresh();
	                }]
	            }
	        });
    });
