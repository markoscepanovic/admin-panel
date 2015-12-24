'use strict';

angular.module('adminPanelApp')
    .controller('DepartmentDetailController', function ($scope, $rootScope, $stateParams, entity, Department, UserProfile) {
        $scope.department = entity;
        $scope.load = function (id) {
            Department.get({id: id}, function (result) {
                $scope.department = result;
            });
        };
        var unsubscribe = $rootScope.$on('adminPanelApp:departmentUpdate', function (event, result) {
            $scope.department = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
