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
                        return $translate.refresh();
                    }]
                }
            })
            .state('identity-family', {
                parent: 'site',
                url: '/{base}/{id}/etatcivilfamille',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/identity/identity-family.html',
                        controller: 'IdentityFamilyController'
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
            	withTooltip: "="
            },
            templateUrl: 'scripts/app/application/identity/identity-light.html',
            link: function postLink(scope, iElement, iAttrs, ctrl) {
            	if(scope.withTooltip === true) {
            		$('[data-toggle="popover"]').popover();
            	}
            }
        };
    });;
