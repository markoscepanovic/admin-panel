'use strict';

angular.module('adminPanelApp')
    .factory('UserProfile', function ($resource, DateUtils) {
        return $resource('api/userProfiles/:id', {}, {
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
