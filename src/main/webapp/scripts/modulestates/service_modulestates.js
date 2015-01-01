'use strict';

abwesenheitsmanagerApp.factory('Modulestates', function ($resource) {
        return $resource('app/rest/modulestates/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
