'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('identityEntity', {
                parent: 'entity',
                url: '/identitysEntity',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.identity.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/identity/identitys.html',
                        controller: 'IdentityEntityController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('identity');
                        $translatePartialLoader.addPart('sexType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('identityEntity.detail', {
                parent: 'entity',
                url: '/identity/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.identity.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/identity/identity-detail.html',
                        controller: 'IdentityDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('identity');
                        $translatePartialLoader.addPart('sexType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Identity', function($stateParams, Identity) {
                        return Identity.get({id : $stateParams.id});
                    }]
                }
            })
            .state('identityEntity.new', {
                parent: 'identityEntity',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/identity/identity-dialog.html',
                        controller: 'IdentityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {lastName: null, usedLastName: null, firstName: null, sex: null, birthDate: null, birthCity: null, birthCountry: null, nationality: null, passportNumber: null, passport: null, birthAct: null, test: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('identity', null, { reload: true });
                    }, function() {
                        $state.go('identity');
                    })
                }]
            })
            .state('identityEntity.edit', {
                parent: 'identityEntity',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/identity/identity-dialog.html',
                        controller: 'IdentityDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Identity', function(Identity) {
                                return Identity.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('identity', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
