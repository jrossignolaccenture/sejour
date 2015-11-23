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
	    	uploadBiometrics: function (uri, type, idApplication) {
	        	var fd = new FormData();
		        fd.append('uri', uri);
		        fd.append('type', type);
		        fd.append('idApplication', idApplication);
		        return $http.post("/fileUpload/biometrics", fd, {
		            transformRequest: angular.identity,
		            headers: {'Content-Type': undefined}
		        });
	    	}
	    };
	})
    .directive('fileUpload', function() {
        return {
            restrict: 'E',
            required: '^ngModel',
            scope: {
            	model: "=ngModel",
            	docType: "@",
            	placeholderKey: "@"
            },
            templateUrl: 'scripts/components/form/fileUpload.html',
            controller: ['$scope', 'File', function($scope, File) {
            	
            	$scope.docs = $scope.model.filter(function(doc) {
        			return doc.type === $scope.docType;
        		});
            			
            	if($scope.docs.length == 0) {
            		$scope.docs.push({});
            	}

                $scope.uploadFile = function(element) {
                	$scope.$apply(function(scope) {
                		scope.fileUploadFail = false;
                        if(element.files.length > 0) {
                        	File.upload(element.files[0], scope.docType)
        				        .success(function(doc) {
        				        	scope.model.push(doc);
        	                    	scope.docs[scope.docs.length-1] = doc;
        				        })
        				        .error(function() { scope.fileUploadFail = true });
                        }
        			});
                }

                $scope.addDoc = function(index) {
                	if(index === ($scope.docs.length - 1) && $scope.docs[index].name) {
                		$scope.docs.push({});
                	}
                }

                $scope.removeDoc = function(index) {
                	// TODO delete the file on server
                	$scope.docs.length == 1 ? $scope.docs[0] = {} : $scope.docs.splice(index, 1);
                	$scope.model.splice(index, 1);
                }
            }],
            link: function postLink(scope, iElement, iAttrs, ctrl) {
                scope.uploadFileClick = function (id, index) {
                	if(!scope.docs[index].name) {
                		angular.element('#'+id+'_'+index).trigger('click');
                	}
                };
            }
        };
    });
