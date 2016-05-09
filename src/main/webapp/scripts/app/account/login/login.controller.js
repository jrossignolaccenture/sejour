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
                var returnToState = $rootScope.returnToState;
                var returnToStateParams = $rootScope.returnToStateParams;
                $rootScope.returnToState = null;
                $rootScope.returnToStateParams = null;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('home');
                } else if(returnToState && returnToState.name !== 'login') {
                	$state.go(returnToState.name, returnToStateParams);
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
