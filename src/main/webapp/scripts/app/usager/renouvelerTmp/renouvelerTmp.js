'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/renouvelerTmp/etudes', {
                parent: 'usager',
                url: '/renouvelerTmp/etudes',
                data: {
                    type: 'renouvellement',
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
                    	$translatePartialLoader.deletePart('renouvelerTmp', true); // tips to get high priority to this part
                        $translatePartialLoader.addPart('renouvelerTmp');
                        return $translate.refresh();
                    }]
                }
            });
    });
