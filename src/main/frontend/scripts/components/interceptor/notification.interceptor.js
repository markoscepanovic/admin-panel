 'use strict';

angular.module('adminPanelApp')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-adminPanelApp-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-adminPanelApp-params')});
                }
                return response;
            }
        };
    });
