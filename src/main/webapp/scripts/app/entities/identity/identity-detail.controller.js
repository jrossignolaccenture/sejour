'use strict';

angular.module('sejourApp')
    .controller('IdentityDetailController', function ($scope, $rootScope, $stateParams, entity, Identity) {
        $scope.identity = entity;
        $scope.load = function (id) {
            Identity.get({id: id}, function(result) {
                $scope.identity = result;
            });
        };
        $rootScope.$on('sejourApp:identityUpdate', function(event, result) {
            $scope.identity = result;
        });
    });
