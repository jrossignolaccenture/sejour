'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('family', {
                parent: 'site',
                url: '/{base}/{id}/famille',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/family/family.html',
                        controller: 'FamilyController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$stateParams', '$translate', '$translatePartialLoader', function ($stateParams, $translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart($stateParams.base);
                    	$translatePartialLoader.addPart('identity');
                    	$translatePartialLoader.addPart('address');
                    	$translatePartialLoader.addPart('sexType');
                    	$translatePartialLoader.addPart('personType');
                        return $translate.refresh();
                    }]
                }
            });
    })
    .directive('familyView', function() {
        return {
            restrict: 'E',
            required: '^ngModel',
            scope: {
            	identity: "=ngModel"
            },
            templateUrl: 'scripts/app/application/family/family-view.html',
            controller: ['$scope', '$timeout', function($scope, $timeout) {
            	
            	$scope.parents = $scope.identity.family['parents'];
            	$scope.currentDocuments = $scope.parents[0].identity.documents;
            	$scope.panelOpen = 0;
            	
            	$scope.updatePanelOpen = function (index) {
            		$scope.currentDocuments = $scope.panelOpen===index ? [] : $scope.parents[index].identity.documents;
            		$scope.panelOpen = $scope.panelOpen===index ? '' : index;
            		$timeout(function() {
            			$('html, body').animate({ scrollTop: $("#Panel"+index).offset().top }, "slow");
            	    }, 50);
            	}
            }]
        };
    });
