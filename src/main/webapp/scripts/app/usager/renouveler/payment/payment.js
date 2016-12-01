'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('renouveler/payment', {
                parent: 'usager/renouveler',
                url: '/payment',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/renouveler/payment/payment.html',
                        controller: 'RenouvelerPaymentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    	$translatePartialLoader.addPart('payment');
                        return $translate.refresh();
                    }]
                }
            });
    });
