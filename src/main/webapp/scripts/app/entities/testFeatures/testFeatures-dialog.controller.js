'use strict';

angular.module('adminPanelApp').controller('TestFeaturesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'TestFeatures',
        function ($scope, $stateParams, $uibModalInstance, DataUtils, entity, TestFeatures) {

            $scope.testFeatures = entity;
            $scope.load = function (id) {
                TestFeatures.get({id: id}, function (result) {
                    $scope.testFeatures = result;
                });
            };

            var onSaveSuccess = function (result) {
                $scope.$emit('adminPanelApp:testFeaturesUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                if ($scope.testFeatures.id != null) {
                    TestFeatures.update($scope.testFeatures, onSaveSuccess, onSaveError);
                } else {
                    TestFeatures.save($scope.testFeatures, onSaveSuccess, onSaveError);
                }
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };

            $scope.abbreviate = DataUtils.abbreviate;

            $scope.byteSize = DataUtils.byteSize;

            $scope.setBlobField = function ($file, testFeatures) {
                if ($file && $file.$error == 'pattern') {
                    return;
                }
                if ($file) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL($file);
                    fileReader.onload = function (e) {
                        var base64Data = e.target.result.substr(e.target.result.indexOf('base64,') + 'base64,'.length);
                        $scope.$apply(function () {
                            testFeatures.blobField = base64Data;
                            testFeatures.blobFieldContentType = $file.type;
                        });
                    };
                }
            };
        }]);
