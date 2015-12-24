'use strict';

angular.module('adminPanelApp')
    .controller('WorkGroupController', function ($scope, $state, WorkGroup, WorkGroupSearch) {

        $scope.workGroups = [];
        $scope.loadAll = function () {
            WorkGroup.query(function (result) {
                $scope.workGroups = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            WorkGroupSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.workGroups = result;
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
            $scope.workGroup = {
                name: null,
                description: null,
                id: null
            };
        };
    });
