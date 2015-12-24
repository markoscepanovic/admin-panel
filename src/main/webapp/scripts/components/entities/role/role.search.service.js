'use strict';

angular.module('adminPanelApp')
    .factory('RoleSearch', function ($resource) {
        return $resource('api/_search/roles/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
