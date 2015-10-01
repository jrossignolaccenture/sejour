'use strict';

angular.module('sejourApp')
	.factory('File', function ($http) {
	    return {
	        upload: function (file, type) {
	        	var fd = new FormData();
		        fd.append('file', file);
		        fd.append('type', type);
		        return $http.post("/fileUpload/document", fd, {
		            transformRequest: angular.identity,
		            headers: {'Content-Type': undefined}
		        });
	        },
	    	uploadBiometrics: function (uri, type, idDemande) {
	        	var fd = new FormData();
		        fd.append('uri', uri);
		        fd.append('type', type);
		        fd.append('idDemande', idDemande);
		        return $http.post("/fileUpload/biometrics", fd, {
		            transformRequest: angular.identity,
		            headers: {'Content-Type': undefined}
		        });
	    	}
	    };
	});
