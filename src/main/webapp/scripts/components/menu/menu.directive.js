'use strict';

angular.module('sejourApp')
	.directive('boxMenu', function() {
	    return {
	        restrict: 'E',
	        scope: {
	        	boxtitle: "@",
				boxlogo: "@"
	        },
	        templateUrl: 'scripts/components/menu/boxMenu.html'
	    };
	})
    .directive('stepsMenu', function() {
        return {
            restrict: 'E',
            scope: {
            	
            },
            templateUrl: 'scripts/components/menu/stepsMenu.html'
        };
    });
