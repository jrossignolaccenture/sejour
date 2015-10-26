'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/etudier', {
                parent: 'usager',
                url: '/etudier',
                data: {
                    pageTitle: 'etudier.page.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/etudier.html',
                        controller: 'EtudierUsagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.deletePart('etudier', true); // tips to get high priority to this part
                        $translatePartialLoader.addPart('etudier');
                        return $translate.refresh();
                    }]
                }
            });
    });
