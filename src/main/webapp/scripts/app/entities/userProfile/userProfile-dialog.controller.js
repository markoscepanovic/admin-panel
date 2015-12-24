'use strict';

angular.module('adminPanelApp').controller('UserProfileDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'UserProfile', 'Role', 'User', 'WorkGroup', 'Department',
        function ($scope, $stateParams, $uibModalInstance, $q, entity, UserProfile, Role, User, WorkGroup, Department) {

            $scope.userProfile = entity;
            $scope.roles = Role.query();
            $scope.users = User.query();
            $scope.workgroups = WorkGroup.query();
            $scope.departments = Department.query();
            $scope.load = function (id) {
                UserProfile.get({id: id}, function (result) {
                    $scope.userProfile = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('adminPanelApp:userProfileUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.userProfile.id != null) {
                    UserProfile.update($scope.userProfile, onSaveSuccess, onSaveError);
                } else {
                    UserProfile.save($scope.userProfile, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
