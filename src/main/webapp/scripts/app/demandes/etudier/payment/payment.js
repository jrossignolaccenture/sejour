'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment', {
                parent: 'etudier',
                url: '/payment',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'etudier.payment.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/demandes/etudier/payment/payment.html',
                        controller: 'PaymentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('etudier');
                        return $translate.refresh();
                    }]
                }
            });
    });
