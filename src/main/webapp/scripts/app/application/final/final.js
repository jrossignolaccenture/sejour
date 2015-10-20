'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('final', {
                parent: 'site',
                url: '/{base}/{id}/fin',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/final/final.html',
                        controller: 'FinalController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart($stateParams.base);
                    	$translatePartialLoader.addPart('final');
                        return $translate.refresh();
                    }]
                }
            });
    });
