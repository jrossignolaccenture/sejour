'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'site',
                url: '/{base}/{id}/adresse',
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
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart($stateParams.base);
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
            	$('#field_owner').popover(scope.withTooltip === true ? undefined : 'disable');
            }
        };
    });
