'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('agent', {
                abstract: true,
                parent: 'site',
	            resolve: {
	                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
	                	$translatePartialLoader.addPart('agent');
	                    return $translate.refresh();
	                }]
	            }
            });
    });
