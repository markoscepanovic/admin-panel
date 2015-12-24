'use strict';

angular.module('adminPanelApp')
    .controller('DepartmentController', function ($scope, $state, Department, DepartmentSearch) {

        $scope.departments = [];
        $scope.loadAll = function () {
            Department.query(function (result) {
                $scope.departments = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            DepartmentSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.departments = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.department = {
                name: null,
                description: null,
                id: null
            };
        };
    });
