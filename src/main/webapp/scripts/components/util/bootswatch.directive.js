/**
 * Created by Marko on 12/21/2015.
 */
'use strict';

angular.module('adminPanelApp')
    .directive('jhSwitchTheme', function () {
        /*Directive binds to anchor to update the bootswatch theme selected*/
        return {
            restrict: 'A',
            scope: {
                theme: '=jhSwitchTheme'
            },
            link: function (scope, element, attrs) {
                var currentTheme = $("#bootswatch-css").attr('title');
                if (scope.theme.name === currentTheme) {
                    element.parent().addClass("active");
                }

                element.on('click', function () {
                    $("#bootswatch-css").attr("href", scope.theme.css);
                    $(".theme-link").removeClass("active");
                    element.parent().addClass("active");
                });
            }
        };
    });
