'use strict';

angular.module('sejourApp')
    .controller('DocumentsController', function ($scope, $state, Demande, currentDemande) {
    	
    	$scope.studentName = currentDemande.identity.firstName + " " + currentDemande.identity.lastName;
    	$scope.demande = currentDemande;
    	$scope.passportOpened = true;
    	$scope.birthActOpened = true;
    	
    	$scope.passport = "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg";
    	$scope.birthAct = "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png";
    	
    });
