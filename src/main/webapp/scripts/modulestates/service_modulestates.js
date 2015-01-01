'use strict';

abwesenheitsmanagerApp.factory('FinishedModule', function ($resource) {
        return $resource('app/rest/finishedModules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
