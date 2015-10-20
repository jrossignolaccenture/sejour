'use strict';

angular.module('sejourApp')
    .controller('AccountUsagerController', function ($scope, $state, userApplications) {
    	
    	$scope.nbApplication = userApplications.length;
    });
