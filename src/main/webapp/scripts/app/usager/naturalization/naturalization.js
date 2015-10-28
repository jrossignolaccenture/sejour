'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/naturalization', {
                parent: 'usager',
                url: '/naturalisation',
                data: {
                    pageTitle: 'naturalization.page.title',
                    type: 'premiere',
                    nature: 'naturalisation',
                    base: 'naturalization'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/etudier.html',
                        controller: 'EtudierUsagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.deletePart('naturalization', true); // tips to get high priority to this part
                        $translatePartialLoader.addPart('naturalization');
                        return $translate.refresh();
                    }]
                }
            });
    });
