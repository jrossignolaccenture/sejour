'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'site',
                url: '/{id}/adresse',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/address/address.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', 'I18N_APPLICATION', 'currentApplication',
                                             function ($translate, $translatePartialLoader, I18N_APPLICATION, currentApplication) {
                    	$translatePartialLoader.addPart(I18N_APPLICATION[currentApplication.type][currentApplication.nature]);
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('contactType');
                        return $translate.refresh();
                    }]
                }
            });
    })
    .directive('addressFormLight', function() {
        return {
            restrict: 'E',
            required: '^ngModel',
            scope: {
            	address: "=ngModel",
            	withTooltip: "="
            },
            templateUrl: 'scripts/app/application/address/address-light.html',
            link: function postLink(scope, iElement, iAttrs, ctrl) {
            	$('[id=field_owner]').popover(scope.withTooltip === true ? undefined : 'disable');
            }
        };
    });
