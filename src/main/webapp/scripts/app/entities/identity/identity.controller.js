'use strict';

angular.module('sejourApp')
    .controller('IdentityEntityController', function ($scope, Identity) {
        $scope.identitys = [];
        $scope.loadAll = function() {
            Identity.query(function(result) {
               $scope.identitys = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Identity.get({id: id}, function(result) {
                $scope.identity = result;
                $('#deleteIdentityConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Identity.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteIdentityConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.identity = {lastName: null, usedLastName: null, firstName: null, sex: null, birthDate: null, birthCity: null, birthCountry: null, nationality: null, passportNumber: null, passport: null, birthAct: null, test: null, id: null};
        };
    });
