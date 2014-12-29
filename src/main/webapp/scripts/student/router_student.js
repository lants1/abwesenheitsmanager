'use strict';

abwesenheitsmanagerApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/student', {
                    templateUrl: 'views/students.html',
                    controller: 'StudentController',
                    resolve:{
                        resolvedStudent: ['Student', function (Student) {
                            return Student.query().$promise;
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
