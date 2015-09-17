'use strict';

angular.module('sejourApp').controller('IdentityDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Identity',
        function($scope, $stateParams, $modalInstance, entity, Identity) {

        $scope.identity = entity;
        $scope.load = function(id) {
            Identity.get({id : id}, function(result) {
                $scope.identity = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('sejourApp:identityUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.identity.id != null) {
                Identity.update($scope.identity, onSaveFinished);
            } else {
                Identity.save($scope.identity, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
