'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rdv', {
                parent: 'application',
                url: '/{id}/rdv',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/rdv/rdv.html',
                        controller: 'RdvController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', 'I18N_APPLICATION', 'currentApplication', 
                                             function ($translate, $translatePartialLoader, I18N_APPLICATION, currentApplication) {
                    	$translatePartialLoader.addPart(I18N_APPLICATION[currentApplication.type][currentApplication.nature]);
                    	$translatePartialLoader.addPart('rdv');
                        return $translate.refresh();
                    }]
                }
            });
    });
