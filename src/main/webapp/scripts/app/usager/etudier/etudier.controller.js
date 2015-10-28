'use strict';

angular.module('sejourApp')
    .controller('EtudierUsagerController', function ($rootScope, $scope, $state, Application) {
    	
        $scope.createApplication = function() {
            Application.create($state.current.data.type, $state.current.data.nature).then(function(id) {
            	$state.go('identity', {base: $state.current.data.base, id: id});
            });
        }
    });
