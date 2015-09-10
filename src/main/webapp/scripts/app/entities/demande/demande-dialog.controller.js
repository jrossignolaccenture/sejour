'use strict';

angular.module('sejourApp').controller('DemandeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Demande',
        function($scope, $stateParams, $modalInstance, entity, Demande) {

        $scope.demande = entity;
        $scope.load = function(id) {
            Demande.get({id : id}, function(result) {
                $scope.demande = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('sejourApp:demandeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.demande.id != null) {
                Demande.update($scope.demande, onSaveFinished);
            } else {
                Demande.save($scope.demande, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
