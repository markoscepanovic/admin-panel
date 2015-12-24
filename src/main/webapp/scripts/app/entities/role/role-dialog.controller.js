'use strict';

angular.module('adminPanelApp').controller('RoleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Role', 'UserProfile',
        function ($scope, $stateParams, $uibModalInstance, entity, Role, UserProfile) {

            $scope.role = entity;
            $scope.userprofiles = UserProfile.query();
            $scope.load = function (id) {
                Role.get({id: id}, function (result) {
                    $scope.role = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('adminPanelApp:roleUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.role.id != null) {
                    Role.update($scope.role, onSaveSuccess, onSaveError);
                } else {
                    Role.save($scope.role, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
