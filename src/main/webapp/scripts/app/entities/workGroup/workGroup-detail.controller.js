'use strict';

angular.module('adminPanelApp')
    .controller('WorkGroupDetailController', function ($scope, $rootScope, $stateParams, entity, WorkGroup, UserProfile) {
        $scope.workGroup = entity;
        $scope.load = function (id) {
            WorkGroup.get({id: id}, function (result) {
                $scope.workGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('adminPanelApp:workGroupUpdate', function (event, result) {
            $scope.workGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
