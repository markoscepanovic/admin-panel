'use strict';

angular.module('adminPanelApp')
    .controller('WorkGroupDeleteController', function ($scope, $uibModalInstance, entity, WorkGroup) {

        $scope.workGroup = entity;
        $scope.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            WorkGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
