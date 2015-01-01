'use strict';

abwesenheitsmanagerApp.factory('FinishedModule', function ($resource) {
        return $resource('app/rest/finishedModules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

abwesenheitsmanagerApp.factory('OpenedModule', function ($resource) {
    return $resource('app/rest/openedModules/:id', {}, {
        'query': { method: 'GET', isArray: true},
        'get': { method: 'GET'}
    });
});
