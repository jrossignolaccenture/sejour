'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/etudierTmp', {
                parent: 'usager',
                url: '/etudierTmp',
                data: {
                    type: 'premiere',
                    nature: 'sejour_tmp_etudiant'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudierTmp/etudierTmp.html',
                        controller: 'EtudierTmpUsagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.deletePart('etudierTmp', true); // tips to get high priority to this part
                        $translatePartialLoader.addPart('etudierTmp');
                        return $translate.refresh();
                    }]
                }
            });
    });
