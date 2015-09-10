'use strict';

angular.module('sejourApp')
    .controller('DemandeController', function ($scope, Demande) {
        $scope.demandes = [];
        $scope.loadAll = function() {
            Demande.query(function(result) {
               $scope.demandes = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Demande.get({id: id}, function(result) {
                $scope.demande = result;
                $('#deleteDemandeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Demande.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDemandeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.demande = {nature: null, type: null, statut: null, demandeComplementaire: null, id: null};
        };
    });
