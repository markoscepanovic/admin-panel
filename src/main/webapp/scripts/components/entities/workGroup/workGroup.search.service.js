'use strict';

angular.module('adminPanelApp')
    .factory('WorkGroupSearch', function ($resource) {
        return $resource('api/_search/workGroups/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
