'use strict';

angular.module('sejourApp')
    .directive('photoViewer', function() {
        return {
            restrict: 'E',
            required: ['^ngModel'],
            scope: {
            	model: "=ngModel",
            	typeFilter: "@"
            },
            templateUrl: 'scripts/components/form/photoViewer.html',
            controller: ['$scope', function($scope) {
            	$scope.refresh = function() {
            		if($scope.typeFilter) {
            			$scope.documents = $scope.model.filter(function(doc) {
            				return doc.type === $scope.typeFilter;
            			});
            		} else {
            			$scope.documents = $scope.model;
            		}
            		
            		$scope.currentIndex = 0;
            	}
            	
            	$scope.refresh();
            }],
            link: function(scope, element, attrs) {
                scope.$watch("model",function(newValue,oldValue) {
                	if(oldValue != newValue) {
                		scope.model = newValue;
                		scope.refresh();
                	}
                });
             }
        };
    });
