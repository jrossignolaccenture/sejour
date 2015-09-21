'use strict';

angular.module('sejourApp')
    .factory('Demande', function ($http) {
        return {
            init: function () {
            	return $http.put('api/demande/init').then(function (response) {
                    return response.data;
                });
            },
            getInProgressDemande: function (email) {
            	return $http.get('api/demande', {params: {email: email, statut: "draft"}}).then(function (response) {
                    return response.data;
                });
            },
            getDemandeToValidate: function (email) {
            	return $http.get('api/demande', {params: {email: email, statut: "validation"}}).then(function (response) {
                    return response.data;
                });
            },
            update: function (demande, finalStep) {
            	if(finalStep){
            		return $http.put('api/demande/validate', demande).then(function (response) {
                        return response.data;
                    });
            	} else {
	            	return $http.put('api/demande/update', demande).then(function (response) {
	                    return response.data;
	                });
            	}
            },
            prepaid: function () {
            	return $http.put('api/demande/prepaid').then(function (response) {
                    return response.data;
                });
            }
        };
    });
