'use strict';

angular.module('sejourApp')
    .controller('ControlAgentController', function ($rootScope, $scope, $state, Principal) {

        
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.userName = account.firstName + ' ' + account.lastName;
        });
    });
