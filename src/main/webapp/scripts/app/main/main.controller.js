'use strict';

angular.module('sejourApp')
    .controller('MainController', function ($scope, $state, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });
        
        
        $scope.goTo = function (stateName) {
            $state.go(stateName);
        };
    });
