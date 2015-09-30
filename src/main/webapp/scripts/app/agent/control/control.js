'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
	        .state('agent/control', {
	            parent: 'agent',
	            url: '/control',
                data: {
                    roles: ['ROLE_AGENT'],
                    pageTitle: 'control.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/agent/control/control.html',
                        controller: 'ControlAgentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('control');
                        return $translate.refresh();
                    }]
                }
            });
    });
