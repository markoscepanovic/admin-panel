'use strict';

angular.module('adminPanelApp')
    .factory('TestFeatures', function ($resource, DateUtils) {
        return $resource('api/testFeaturess/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'}
        });
    });
