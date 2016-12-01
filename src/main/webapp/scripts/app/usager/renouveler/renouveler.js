'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/renouveler', {
                parent: 'usager',
                url: '/renouveler',
                data: {
                    pageTitle: 'renouveler.page.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/renouveler.html',
                        controller: 'RenouvelerUsagerController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('renouveler');
                        return $translate.refresh();
                    }]
                }
            });
    });
