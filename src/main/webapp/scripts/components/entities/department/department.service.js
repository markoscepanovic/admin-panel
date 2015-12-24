'use strict';

angular.module('adminPanelApp')
    .factory('Department', function ($resource, DateUtils) {
        return $resource('api/departments/:id', {}, {
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
