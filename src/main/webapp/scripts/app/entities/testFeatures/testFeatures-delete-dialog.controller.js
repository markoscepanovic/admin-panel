'use strict';

angular.module('adminPanelApp')
    .controller('TestFeaturesDeleteController', function ($scope, $uibModalInstance, entity, TestFeatures) {

        $scope.testFeatures = entity;
        $scope.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TestFeatures.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
