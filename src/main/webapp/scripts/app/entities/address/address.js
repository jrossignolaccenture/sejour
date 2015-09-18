'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'entity',
                url: '/addresss',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.address.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/addresss.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('contactType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('address.detail', {
                parent: 'entity',
                url: '/address/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'sejourApp.address.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/address-detail.html',
                        controller: 'AddressDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('address');
                        $translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Address', function($stateParams, Address) {
                        return Address.get({id : $stateParams.id});
                    }]
                }
            })
            .state('address.new', {
                parent: 'address',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {owner: null, number: null, street: null, complement: null, postalCode: null, city: null, country: null, phone: null, email: null, contactType: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('address');
                    })
                }]
            })
            .state('address.edit', {
                parent: 'address',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
