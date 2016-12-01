'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/project', {
                parent: 'usager/renouveler',
                url: '/project',
                data: {
                    roles: ['ROLE_USAGER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/project/project.html',
                        controller: 'RenouvelerProjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('project');
                        $translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
