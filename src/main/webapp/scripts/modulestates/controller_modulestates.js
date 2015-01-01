'use strict';

abwesenheitsmanagerApp.controller('FinishedModulesController', function ($scope, resolvedModule, Module, resolvedLesson) {

    $scope.finishedModules = resolvedModule;
    $scope.lessons = resolvedLesson;

    $scope.create = function () {
        Module.save($scope.module,
            function () {
                $scope.finishedModules = Module.query();
                $('#saveModuleModal').modal('hide');
                $scope.clear();
            });
    };

    $scope.update = function (id) {
        $scope.module = Module.get({id: id});
        $('#saveModuleModal').modal('show');
    };

    $scope.delete = function (id) {
        Module.delete({id: id},
            function () {
                $scope.modules = Module.query();
            });
    };

    $scope.clear = function () {
        $scope.module = {type: null, name: null, minLessons: null, passed: null, year: null, id: null};
    };
});

