/**
 * Created by Marko on 12/21/2015.
 */
'use strict';

angular.module('adminPanelApp')
    .controller('BootswatchController', function ($scope, BootSwatchService) {
        /*Get the list of availabel bootswatch themes*/
        BootSwatchService.get().then(function (themes) {
            $scope.themes = themes;
            $scope.themes.unshift({name: 'Default', css: ''});
        });
    });
