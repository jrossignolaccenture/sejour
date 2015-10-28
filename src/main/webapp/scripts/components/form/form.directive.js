/* globals $ */
'use strict';

angular.module('sejourApp')
    .directive('showValidation', function() {
        return {
            restrict: 'A',
            require: 'form',
            link: function (scope, element) {
                element.find('.form-group').each(function() {
                    var $formGroup = $(this);
                    var $inputs = $formGroup.find('input[ng-model],textarea[ng-model],select[ng-model]');

                    if ($inputs.length > 0) {
                        $inputs.each(function() {
                            var $input = $(this);
                            scope.$watch(function() {
                                return $input.hasClass('ng-invalid') && $input.hasClass('ng-dirty');
                            }, function(isInvalid) {
                                $formGroup.toggleClass('has-error', isInvalid);
                            });
                        });
                    }
                });
            }
        };
    })
    .directive('fileUpload', function() {
        return {
            restrict: 'E',
            required: '^ngModel',
            scope: {
            	fileName: "=ngModel",
            	inputGroup: "@",
            	inputName: "@"
            },
            templateUrl: 'scripts/components/form/fileUpload.html',
            controller: ['$scope', 'File', function($scope, File) {
            	
            	$scope.file = {};
            	
                $scope.uploadFile = function(element) {
                	$scope.$apply(function(scope) {
                        if(element.files.length > 0) {
                        	File.upload(element.files[0], scope.inputName)
        				        .success(function() {
        	                		scope.file = element.files[0];
        	                    	scope.fileName = element.files[0].name;
        				        })
        				        .error(function() {
        				        });
                        	
                        } else {
                        	scope.file = {};
                        	scope.fileName = "";
                        }
        			});
                };
            }],
            link: function postLink(scope, iElement, iAttrs, ctrl) {

                scope.uploadFileClick = function () {
                	  angular.element('#file_'+scope.inputName).trigger('click');
                };
                
              	$(function () {
            	  $('[data-toggle="popover"]').popover()
            	});
            }
        };
    });;
