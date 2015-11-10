'use strict';

angular.module('sejourApp')
    .controller('ReconstructionController', function ($scope, $state, Application, currentApplication) {

        $scope.application = currentApplication;
        $scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;

        //TODO Ecran en mode POC total donc tout est à revoir évidemment...
        $scope.collectedIdentity = {
        	lastName: currentApplication.identity.francisation ? currentApplication.identity.lastNameFrancise : currentApplication.identity.lastName,
        	usedLastName: currentApplication.identity.usedLastName,
        	firstName: currentApplication.identity.francisation ? currentApplication.identity.firstNameFrancise : currentApplication.identity.firstName,
        	sex: currentApplication.identity.sex,
        	birthDate: currentApplication.identity.birthDate,
        	birthCity: currentApplication.identity.birthCity,
        	birthCountry: currentApplication.identity.birthCountry,
        	nationality: currentApplication.identity.nationality
        }
        
        $scope.proposedIdentity = {
            lastName: $scope.collectedIdentity.lastName,
        	usedLastName: $scope.collectedIdentity.usedLastName,
        	firstName: 'Christian',
        	sex: $scope.collectedIdentity.sex,
        	birthDate: $scope.collectedIdentity.birthDate,
        	birthCity: $scope.collectedIdentity.birthCity,
        	birthCountry: $scope.collectedIdentity.birthCountry,
        	nationality: $scope.collectedIdentity.nationality
        }
        
        
        $scope.validate = function () {
    		Application.reconstruct(currentApplication.id).then(function(result) {
            	$state.go('reconstruction/list')
            });
        }
    });
