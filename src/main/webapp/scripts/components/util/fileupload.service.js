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
	        }
	    };
	});
