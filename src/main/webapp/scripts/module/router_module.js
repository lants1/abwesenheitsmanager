'use strict';

abwesenheitsmanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/module', {
                    templateUrl: 'views/modules.html',
                    controller: 'ModuleController',
                    resolve:{
                        resolvedModule: ['Module', function (Module) {
                            return Module.query().$promise;
                        }],
                        resolvedLesson: ['Lesson', function (Lesson) {
                            return Lesson.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
