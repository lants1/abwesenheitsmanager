'use strict';

abwesenheitsmanagerApp.controller('LessonController', function ($scope, resolvedLesson, Lesson, resolvedModule, resolvedStudent) {

        $scope.lessons = resolvedLesson;
        $scope.modules = resolvedModule;
        $scope.students = resolvedStudent;

        $scope.create = function () {
            Lesson.save($scope.lesson,
                function () {
                    $scope.lessons = Lesson.query();
                    $('#saveLessonModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.lesson = Lesson.get({id: id});
            $('#saveLessonModal').modal('show');
        };

        $scope.delete = function (id) {
            Lesson.delete({id: id},
                function () {
                    $scope.lessons = Lesson.query();
                });
        };

        $scope.clear = function () {
            $scope.lesson = {date: null, vistied: null, rating: null, id: null};
        };
    });
