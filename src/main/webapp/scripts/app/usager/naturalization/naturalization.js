'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/naturalization', {
                parent: 'usager',
                url: '/naturalisation',
                data: {
                    type: 'naturalisation',
                    nature: 'naturalisation'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/etudier.html',
                        controller: 'EtudierUsagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.deletePart('naturalisation', true); // tips to get high priority to this part
                        $translatePartialLoader.addPart('naturalisation');
                        return $translate.refresh();
                    }]
                }
            });
    });
