'use strict';

angular.module('sejourApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $location, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function (event) {
            event.preventDefault();
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                $rootScope.previousStateName = null;
                $rootScope.previousStateParams = null;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else if($rootScope.returnToState && $rootScope.returnToState.name !== 'login') {
                	$state.go($rootScope.returnToState.name);
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
