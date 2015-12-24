'use strict';

describe('TestFeatures Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockTestFeatures;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTestFeatures = jasmine.createSpy('MockTestFeatures');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'TestFeatures': MockTestFeatures
        };
        createController = function () {
            $injector.get('$controller')("TestFeaturesDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'adminPanelApp:testFeaturesUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
