'use strict';

angular.module('sejourApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
