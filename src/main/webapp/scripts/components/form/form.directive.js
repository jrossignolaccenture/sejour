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
    .directive('tooltip', function() {
        return {
            restrict: 'E',
            required: '^tooltipText',
            scope: {
            	tooltipText: "@"
            },
            templateUrl: 'scripts/components/form/tooltip.html',
            link: function postLink(scope, iElement, iAttrs, ctrl) {
            	$(function () {
            		$('[data-toggle="popover"]').popover();
            	});
            }
        };
    })
    .directive('selectCountry', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "=",
            	disabled: "@"
            },
            template: '<select class="form-control" ng-options="country.key as country.name for country in countries | orderBy:\'name\'" ng-model="ngModel" ng-disabled="disabled == \'disabled\'"></select>',
            controller: ['$scope', 'Country', function($scope, Country) {
            	$scope.countries = [];
	            Country.get().then(function(countries) {
	            	$scope.countries = countries;
	            });
            }]
        };
    })
    .directive('selectMaritalStatus', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "=",
            	fieldName: "@",
            	fieldId: "@",
            	disabled: "@"
            },
            templateUrl: 'scripts/components/form/selectMaritalStatus.html'
        };
    })
    .directive('selectActivityType', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "=",
            	fieldName: "@",
            	fieldId: "@",
            	disabled: "@"
            },
            templateUrl: 'scripts/components/form/selectActivityType.html'
        };
    })
    .directive('inputBirthDate', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "=",
            	fieldName: "@",
            	fieldId: "@",
            	readonly: "@"
            },
            templateUrl: 'scripts/components/form/inputDate.html',
            controller: ['$rootScope', '$scope', function($rootScope, $scope) {
            	$scope.datePickerOptions = {
        			format: 'DD/MM/YYYY',
        			maxDate: 'moment', 
        			viewMode: 'years', 
        			locale: 'fr',
        			allowInputToggle: true
                }
            	
            	$scope.updateDate = function(){
            		$scope.ngModel = moment($scope.dateTxt, "DD/MM/YYYY").toDate();
            	}
            	
            	$scope.$watch("ngModel",function(newValue,oldValue) {
            		$scope.dateTxt = newValue ? $rootScope.getFormattedDate(newValue) : null;
                });
            }]
        };
    })
    .directive('inputDate', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "=",
            	readonly: "@",
            	fieldName: "@",
            	fieldId: "@"
            },
            templateUrl: 'scripts/components/form/inputDate.html',
            controller: ['$rootScope', '$scope', function($rootScope, $scope) {
            	$scope.datePickerOptions = {
        			format: 'DD/MM/YYYY',
        			minDate: 'moment', 
        			viewMode: 'years', 
        			locale: 'fr',
        			allowInputToggle: true
                }
            	
            	$scope.updateDate = function(){
            		$scope.ngModel = moment($scope.dateTxt, "DD/MM/YYYY").toDate();
            	}
            	
            	$scope.$watch("ngModel",function(newValue,oldValue) {
                	$scope.dateTxt = newValue ? $rootScope.getFormattedDate(newValue) : null;
                });
            }]
        };
    })
    .directive('checkboxContactType', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "="
            },
            templateUrl: 'scripts/components/form/checkboxContactType.html',
            controller: ['$scope', function($scope) {
            	
            	$scope.contactTypeValues = ['email', 'sms', 'postal'];
            	
            	$scope.updateContactType = function(contactType) {
            		var index = $scope.ngModel.indexOf(contactType);
            		if(index == -1) {
            			$scope.ngModel.push(contactType);
                    } else {
                    	$scope.ngModel.splice(index, 1);
                    }
            	}
            }]
        };
    });
