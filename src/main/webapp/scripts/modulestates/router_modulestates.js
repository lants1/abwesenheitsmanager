'use strict';

abwesenheitsmanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/modulestates', {
                    templateUrl: 'views/modulestates.html',
                    controller: 'FinishedModulesController',
                    resolve:{
                        resolvedModuleFinished: ['FinishedModule', function (Module) {
                            return Module.query().$promise;
                        }],
                        resolvedModuleOpened: ['OpenedModule', function (Module) {
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
