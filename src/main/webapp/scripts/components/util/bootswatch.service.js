/**
 * Created by Marko on 12/21/2015.
 */

'use strict';

angular.module('adminPanelApp')
    .factory('BootSwatchService', function ($http) {
        return {
            get: function () {
                return $http.get('http://bootswatch.com/api/3.json').then(function (response) {
                    return response.data.themes;
                });
            }
        };
    });
