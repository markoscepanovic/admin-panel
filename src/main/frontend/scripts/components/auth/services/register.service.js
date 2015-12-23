'use strict';

angular.module('adminPanelApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


