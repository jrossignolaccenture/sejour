'use strict';

angular.module('sejourApp')
    .controller('FinalController', function ($scope, $state, $stateParams) {
        
        $scope.isRenewal = $stateParams.base === 'renouveler';
    });
