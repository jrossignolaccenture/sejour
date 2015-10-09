'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/renouveler', {
                parent: 'usager',
                url: '/renouveler/etudes',
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
            })
            .state('usager/renouveler/choice', {
                parent: 'usager',
                url: '/renouveler',
                data: {
                    pageTitle: 'renouveler.page.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/renouvelerChoice.html'
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
