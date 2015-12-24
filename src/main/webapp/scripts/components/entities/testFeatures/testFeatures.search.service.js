'use strict';

angular.module('adminPanelApp')
    .factory('TestFeaturesSearch', function ($resource) {
        return $resource('api/_search/testFeaturess/:query', {}, {
            'query': {method: 'GET', isArray: true}
        });
    });
