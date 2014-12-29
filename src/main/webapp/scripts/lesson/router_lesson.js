'use strict';

abwesenheitsmanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/lesson', {
                    templateUrl: 'views/lessons.html',
                    controller: 'LessonController',
                    resolve:{
                        resolvedLesson: ['Lesson', function (Lesson) {
                            return Lesson.query().$promise;
                        }],
                        resolvedModule: ['Module', function (Module) {
                            return Module.query().$promise;
                        }],
                        resolvedStudent: ['Student', function (Student) {
                            return Student.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
