'use strict';

abwesenheitsmanagerApp.factory('Lesson', function ($resource) {
        return $resource('app/rest/lessons/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
