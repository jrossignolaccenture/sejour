'use strict';

angular.module('sejourApp')
    .controller('ControlAgentController', function ($rootScope, $scope, Principal) {

        
        Principal.identity().then(function(account) {
            $scope.account = account;
        });
    });
