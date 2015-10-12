'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/detail', {
                parent: 'usager/renouveler',
                url: '/detail/{id}',
                data: {
                    roles: ['ROLE_USAGER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/detail/detail.html',
                        controller: 'RenouvelerDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('summary');
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('contactType');
                    	$translatePartialLoader.addPart('project');
                    	$translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }],
                    currentDemande: ['$stateParams', 'Demande', function($stateParams, Demande) {
                        return Demande.get({id : $stateParams.id});
                    }]
                }
            });
    });
