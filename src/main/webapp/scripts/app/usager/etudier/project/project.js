'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/project', {
                parent: 'usager/etudier',
                url: '/project',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.project.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/project/project.html',
                        controller: 'ProjectController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('resourceType');
                        return $translate.refresh();
                    }]
                }
            });
    });
