'use strict';

angular.module('sejourApp')
    .controller('AddressDetailController', function ($scope, $rootScope, $stateParams, entity, Address) {
        $scope.address = entity;
        $scope.load = function (id) {
            Address.get({id: id}, function(result) {
                $scope.address = result;
            });
        };
        $rootScope.$on('sejourApp:addressUpdate', function(event, result) {
            $scope.address = result;
        });
    });
