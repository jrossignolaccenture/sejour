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
            	scope.zoomButtonClick = function(zoomOut) {
             	   var scaleToAdd = 0.4;
             	   if(zoomOut)
             	      scaleToAdd = -scaleToAdd;
             	   $("#img"+scope.documents[0].id).smartZoom("zoom", scaleToAdd);
             	}
                scope.$watch("model",function(newValue,oldValue) {
                	if(oldValue != newValue) {
                		$timeout(function() {
                		scope.model = newValue;
                		scope.refresh();
                		});
                	}
                });
                scope.$watch("currentIndex",function(newValue,oldValue) {
                	if(oldValue != newValue) {
                		$("#img"+scope.documents[0].id).smartZoom("zoom", -3);
                	}
                });
                $timeout(function() {
                	$("#img"+scope.documents[0].id).smartZoom({
                		'scrollEnabled': false,
                		'dblClickEnabled' : false
                	});
                });
             }
        };
    });
