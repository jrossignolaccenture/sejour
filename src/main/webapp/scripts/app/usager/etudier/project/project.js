'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/project', {
                parent: 'usager/etudier',
                url: '/project',
                data: {
                    roles: ['ROLE_USAGER'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/project/project.html',
                        controller: 'ProjectController'
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
