'use strict';

angular.module('adminPanelApp').controller('WorkGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'WorkGroup', 'UserProfile',
        function ($scope, $stateParams, $uibModalInstance, entity, WorkGroup, UserProfile) {

            $scope.workGroup = entity;
            $scope.userprofiles = UserProfile.query();
            $scope.load = function (id) {
                WorkGroup.get({id: id}, function (result) {
                    $scope.workGroup = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('adminPanelApp:workGroupUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.workGroup.id != null) {
                    WorkGroup.update($scope.workGroup, onSaveSuccess, onSaveError);
                } else {
                    WorkGroup.save($scope.workGroup, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
