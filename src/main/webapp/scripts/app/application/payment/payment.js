'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('payment', {
                parent: 'site',
                url: '/{id}/paiement',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/application/payment/payment.html',
                        controller: 'PaymentController'
                    }
                },
                resolve: {
                    currentApplication: ['$stateParams', 'Application', function($stateParams, Application) {
                        return Application.get({id : $stateParams.id});
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', 'I18N_APPLICATION', 'currentApplication',
                                             function ($translate, $translatePartialLoader, I18N_APPLICATION, currentApplication) {
                    	$translatePartialLoader.addPart(I18N_APPLICATION[currentApplication.type][currentApplication.nature]);
                    	$translatePartialLoader.addPart('payment');
                        return $translate.refresh();
                    }]
                }
            });
    });
