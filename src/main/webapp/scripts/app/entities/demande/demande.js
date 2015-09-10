'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('demande', {
                parent: 'entity',
                url: '/demandes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.demande.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/demande/demandes.html',
                        controller: 'DemandeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('demande');
                        $translatePartialLoader.addPart('natureDemande');
                        $translatePartialLoader.addPart('typeDemande');
                        $translatePartialLoader.addPart('statutDemande');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('demande.detail', {
                parent: 'entity',
                url: '/demande/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.demande.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/demande/demande-detail.html',
                        controller: 'DemandeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('demande');
                        $translatePartialLoader.addPart('natureDemande');
                        $translatePartialLoader.addPart('typeDemande');
                        $translatePartialLoader.addPart('statutDemande');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Demande', function($stateParams, Demande) {
                        return Demande.get({id : $stateParams.id});
                    }]
                }
            })
            .state('demande.new', {
                parent: 'demande',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/demande/demande-dialog.html',
                        controller: 'DemandeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nature: null, type: null, statut: null, demandeComplementaire: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('demande', null, { reload: true });
                    }, function() {
                        $state.go('demande');
                    })
                }]
            })
            .state('demande.edit', {
                parent: 'demande',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/demande/demande-dialog.html',
                        controller: 'DemandeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Demande', function(Demande) {
                                return Demande.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('demande', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
