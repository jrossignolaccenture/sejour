'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('family', {
                parent: 'site',
                url: '/{base}/{id}/famille',
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
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart($stateParams.base);
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('personType');
                        return $translate.refresh();
                    }]
                }
            });
    });
