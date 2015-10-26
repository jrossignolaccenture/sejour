'use strict';

angular.module('sejourApp')
    .controller('DocumentsController', function ($scope, $state, Application, currentApplication) {
    	
    	$scope.studentName = currentApplication.identity.firstName + " " + currentApplication.identity.lastName;
    	$scope.application = currentApplication;
    	
    	$scope.passportOpened = true;
    	$scope.birthActOpened = true;
    	
    	$scope.passport = "assets/fileUpload/passport_kim.soon.jeen@gmail.com.jpg";
    	$scope.birthAct = "assets/fileUpload/birthAct_kim.soon.jeen@gmail.com.png";

        
    	$scope.certify = function() {
    		Application.identify(currentApplication.id, 'documents').then(function(result){
            	$state.go('identification/list')
            });
    	}
    });
