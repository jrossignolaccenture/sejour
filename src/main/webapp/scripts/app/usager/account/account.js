'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('usager/account', {
                parent: 'usager',
                url: '/account',
                data: {
                    roles: ['ROLE_USAGER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/usager/account/account.html',
                        controller: 'AccountUsagerController'
                    }
                },
                resolve: {
	            	userApplications: ['Application', function(Application) {
                        return Application.get();
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('account');
                        return $translate.refresh();
                    }]
                }
            });
    });
