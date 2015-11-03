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
            	$('[data-toggle="popover"]').popover();
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
    .directive('inputBirthDate', function() {
        return {
            restrict: 'E',
            scope: {
            	ngModel: "="
            },
            template: '<input type="text" class="form-control" datetimepicker datetimepicker-options="{{birthDatePickerOptions}}" name="birthDateTxt" id="field_birthDateTxt" ng-model="birthDateTxt" ng-change="updateDate()" placeholder="{{\'global.form.date.format\' | translate}}">',
            controller: ['$scope', function($scope) {
            	$scope.birthDatePickerOptions = {
        			format: 'DD/MM/YYYY',
        			maxDate: 'moment', 
        			viewMode: 'years', 
        			locale: 'fr',
        			allowInputToggle: true
                }
            	if($scope.ngModel) {
                	$scope.birthDateTxt = moment($scope.ngModel).format("DD/MM/YYYY");
                }
            	$scope.updateDate = function(){
            		$scope.ngModel = moment($scope.birthDateTxt, "DD/MM/YYYY").toDate();
            	}
            }]
        };
    });
