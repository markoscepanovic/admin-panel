'use strict';

angular.module('adminPanelApp')
    .controller('TestFeaturesController', function ($scope, $state, DataUtils, TestFeatures, TestFeaturesSearch) {

        $scope.testFeaturess = [];
        $scope.loadAll = function () {
            TestFeatures.query(function (result) {
                $scope.testFeaturess = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            TestFeaturesSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.testFeaturess = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.testFeatures = {
                validationStringField: null,
                validationRegExpField: null,
                blobField: null,
                blobFieldContentType: null,
                enumField: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
