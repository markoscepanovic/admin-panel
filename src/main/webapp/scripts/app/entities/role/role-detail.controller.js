'use strict';

angular.module('adminPanelApp')
    .controller('RoleDetailController', function ($scope, $rootScope, $stateParams, entity, Role, UserProfile) {
        $scope.role = entity;
        $scope.load = function (id) {
            Role.get({id: id}, function (result) {
                $scope.role = result;
            });
        };
        var unsubscribe = $rootScope.$on('adminPanelApp:roleUpdate', function (event, result) {
            $scope.role = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
