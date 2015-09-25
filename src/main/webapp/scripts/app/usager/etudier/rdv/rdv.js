'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/rdv', {
                parent: 'usager/etudier',
                url: '/rdv',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/rdv/rdv.html',
                        controller: 'RdvController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('rdv');
                        return $translate.refresh();
                    }]
                }
            });
    });
