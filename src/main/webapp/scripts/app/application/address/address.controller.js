'use strict';

angular.module('sejourApp')
    .controller('AddressController', function ($scope, $state, $stateParams, Application, currentApplication) {
        
    	$scope.address = currentApplication.address;
    	$scope.nature = currentApplication.nature; //TODO temporaire pour faire suite à un retour pour la démo (à virer après)
    	
        $scope.contactTypeValues = [ { value: 'email', checked: $scope.address.contactType.indexOf('email') != -1 },
                                     { value: 'sms', checked: $scope.address.contactType.indexOf('sms') != -1 },
                                     { value: 'postal', checked: $scope.address.contactType.indexOf('postal') != -1 },
                               ];
    	$scope.updateContactType = function(contactType) {
    		var index = $scope.address.contactType.indexOf(contactType.value);
    		if(contactType.checked && index == -1) {
    			$scope.address.contactType.push(contactType.value);
            }else if(!contactType.checked && index != -1) {
            	$scope.address.contactType.splice(index, 1);
            }
    	}

        $scope.back = function () {
        	if(currentApplication.nature === 'naturalisation') {
        		$state.go('family', $stateParams);
        	} else {
        		$state.go('identity', $stateParams);
        	}
        };
        $scope.save = function () {
            Application.update(currentApplication).then(function() {
            	$state.go('project', $stateParams);
            });
        };
        
    });
