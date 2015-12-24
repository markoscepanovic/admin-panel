'use strict';

angular.module('adminPanelApp')
    .controller('DepartmentController', function ($scope, $state, Department, DepartmentSearch, ParseLinks) {

        $scope.departments = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 0;
        $scope.loadAll = function () {
            Department.query({
                page: $scope.page,
                size: 20,
                sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.departments.push(result[i]);
                }
            });
        };
        $scope.reset = function () {
            $scope.page = 0;
            $scope.departments = [];
            $scope.loadAll();
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
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
            $scope.reset();
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
