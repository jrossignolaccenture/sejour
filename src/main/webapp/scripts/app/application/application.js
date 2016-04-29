'use strict';

angular.module('sejourApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('application', {
                abstract: true,
                parent: 'site'
            });
    })
	.constant('I18N_APPLICATION', {
		premiere: {
			sejour_etudiant: "etudier",
			sejour_tmp_etudiant: "etudierTmp",
			sejour_salarie: "etudier"
		},
		renouvellement: {
			sejour_etudiant: "renouveler",
			sejour_tmp_etudiant: "renouvelerTmp",
			sejour_salarie: "renouveler"
		},
		naturalisation: {
			naturalisation: "naturalisation"
		}
	});
