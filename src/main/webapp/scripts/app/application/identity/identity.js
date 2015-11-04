'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('identity', {
                parent: 'site',
                url: '/{base}/{id}/etatcivil',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/identity/identity.html',
                        controller: 'IdentityController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart($stateParams.base);
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('maritalStatus');
                    	$translatePartialLoader.addPart('activityType');
                        return $translate.refresh();
                    }]
                }
            });
    })
    .directive('identityFormLight', function() {
        return {
            restrict: 'E',
            required: '^ngModel',
            scope: {
            	identity: "=ngModel",
            	withFrancisation: "=",
            	withTooltip: "="
            },
            templateUrl: 'scripts/app/application/identity/identity-light.html',
            controller: ['$scope', function($scope) {
            	$scope.resetFrancisation = function() {
            		$scope.identity.lastNameFrancise = null;
            		$scope.identity.firstNameFrancise = null;
            	}
            }],
            link: function postLink(scope, iElement, iAttrs, ctrl) {
            	$('#field_lastName').popover(scope.withTooltip === true ? undefined : 'disable');
            }
        };
    });
