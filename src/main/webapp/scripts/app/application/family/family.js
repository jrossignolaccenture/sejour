'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('family', {
                parent: 'site',
                url: '/{id}/famille',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/family/family.html',
                        controller: 'FamilyController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', 'I18N_APPLICATION', 'currentApplication',
                                             function ($translate, $translatePartialLoader, I18N_APPLICATION, currentApplication) {
                    	$translatePartialLoader.addPart(I18N_APPLICATION[currentApplication.type][currentApplication.nature]);
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('personType');
                        return $translate.refresh();
                    }]
                }
            });
    });
