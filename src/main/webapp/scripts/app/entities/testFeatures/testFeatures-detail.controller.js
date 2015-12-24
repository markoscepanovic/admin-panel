'use strict';

angular.module('adminPanelApp')
    .controller('TestFeaturesDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, TestFeatures) {
        $scope.testFeatures = entity;
        $scope.load = function (id) {
            TestFeatures.get({id: id}, function (result) {
                $scope.testFeatures = result;
            });
        };
        var unsubscribe = $rootScope.$on('adminPanelApp:testFeaturesUpdate', function (event, result) {
            $scope.testFeatures = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
