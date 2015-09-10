'use strict';

angular.module('sejourApp')
    .controller('DemandeDetailController', function ($scope, $rootScope, $stateParams, entity, Demande) {
        $scope.demande = entity;
        $scope.load = function (id) {
            Demande.get({id: id}, function(result) {
                $scope.demande = result;
            });
        };
        $rootScope.$on('sejourApp:demandeUpdate', function(event, result) {
            $scope.demande = result;
        });
    });
