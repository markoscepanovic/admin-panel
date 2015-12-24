'use strict';

angular.module('adminPanelApp')
    .controller('UserProfileDetailController', function ($scope, $rootScope, $stateParams, entity, UserProfile, Role, User, WorkGroup, Department) {
        $scope.userProfile = entity;
        $scope.load = function (id) {
            UserProfile.get({id: id}, function (result) {
                $scope.userProfile = result;
            });
        };
        var unsubscribe = $rootScope.$on('adminPanelApp:userProfileUpdate', function (event, result) {
            $scope.userProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
