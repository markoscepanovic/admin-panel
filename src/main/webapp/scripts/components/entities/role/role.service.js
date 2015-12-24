'use strict';

angular.module('adminPanelApp')
    .factory('Role', function ($resource, DateUtils) {
        return $resource('api/roles/:id', {}, {
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
