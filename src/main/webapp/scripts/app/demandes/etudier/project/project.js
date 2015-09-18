'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('project', {
                parent: 'etudier',
                url: '/project',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.project.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/project/project.html',
                        controller: 'ProjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etudier');
                        $translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
