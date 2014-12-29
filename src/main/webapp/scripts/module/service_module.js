'use strict';

abwesenheitsmanagerApp.factory('Module', function ($resource) {
        return $resource('app/rest/modules/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
