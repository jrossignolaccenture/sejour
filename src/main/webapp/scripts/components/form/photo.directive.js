'use strict';

angular.module('sejourApp')
    .directive('photoViewer', function($timeout) {
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
            	scope.previousPhoto = function() {
            		scope.currentIndex = scope.currentIndex == scope.documents.length - 1 ? 0 : scope.currentIndex+1;
               		$("#img"+scope.documents[0].id).panzoom('reset');
            	}
            	scope.nextPhoto = function(previous) {
            		scope.currentIndex = scope.currentIndex == 0 ? scope.documents.length - 1 : scope.currentIndex-1;
               		$("#img"+scope.documents[0].id).panzoom('reset');
            	}
                scope.$watch("model",function(newValue,oldValue) {
                	if(oldValue != newValue) {
                		$timeout(function() {
                			$("#img"+scope.documents[0].id).panzoom('reset');
	                		scope.model = newValue;
	                		scope.refresh();
                		});
                	}
                });
                if(scope.documents.length > 0) {
                	$timeout(function() {
                		$("#img"+scope.documents[0].id).panzoom({
                			eventNamespace: ".panzoom"+scope.documents[0].id,
                			$zoomIn: $("#zoomInButton"+scope.documents[0].id),
                			$zoomOut: $("#zoomOutButton"+scope.documents[0].id),
                			minScale: 1,
                			maxScale: 3,
                			increment: 0.5,
                			duration: 500
                		});
                	});
	            }
             }
        };
    });
