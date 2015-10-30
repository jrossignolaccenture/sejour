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
    });
