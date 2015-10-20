'use strict';

angular.module('sejourApp')
    .factory('Application', function ($http) {
        return {
            create: function (type, nature) {
            	var formData = new FormData();
		        formData.append('type', type);
		        formData.append('nature', nature);
		        var params = { headers: {'Content-Type': undefined} }
            	return $http.post('api/application', formData, params).then(function (response) {
                    return response.data;
                });
            },
            delete: function (objectId) {
            	return $http.delete('api/application/' + objectId).then(function (response) {
                    return response.data;
                });
            },
	        get: function (objectId) {
	        	var id = objectId ? '/' + objectId.id : '';
	        	return $http.get('api/application' + id).then(function (response) {
	                return response.data;
	            });
	        },
            getByStatus: function (status, email) {
            	return $http.get('api/application/statut', {params: {status: status, email: email}}).then(function (response) {
                    return response.data;
                });
            },
        	count: function() {
            	return $http.get('api/application/count').then(function (response) {
                    return response.data;
                });
        	},
	        update: function (demande) {
        		return $http.put('api/application', demande).then(function (response) {
                    return response.data;
                });
	        },
	        pay: function (id) {
        		return $http.put('api/application/pay', id).then(function (response) {
                    return response.data;
                });
	        },
            admissibility: function (id) {
            	return $http.put('api/application/admissibility', id).then(function (response) {
                    return response.data;
                });
            },
            schedule: function (application) {
            	return $http.put('api/application/rdv', application).then(function (response) {
                    return response.data;
                });
            },
            identify: function (id, step) {
            	return $http.put('api/application/identification/' + step, id).then(function (response) {
                    return response.data;
                });
            },
            validate: function (id) {
            	return $http.put('api/application/validation', id).then(function (response) {
                    return response.data;
                });
            }
        };
    });
