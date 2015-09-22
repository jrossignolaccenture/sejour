'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('etudier/payment', {
                parent: 'usager/etudier',
                url: '/payment',
                data: {
                    roles: ['ROLE_USAGER'],
                    pageTitle: 'etudier.payment.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/etudier/payment/payment.html',
                        controller: 'PaymentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        return $translate.refresh();
                    }]
                }
            });
    });
