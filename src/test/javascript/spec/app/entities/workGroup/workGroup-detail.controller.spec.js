'use strict';

describe('WorkGroup Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockWorkGroup, MockUserProfile;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockWorkGroup = jasmine.createSpy('MockWorkGroup');
        MockUserProfile = jasmine.createSpy('MockUserProfile');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'WorkGroup': MockWorkGroup,
            'UserProfile': MockUserProfile
        };
        createController = function () {
            $injector.get('$controller')("WorkGroupDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'adminPanelApp:workGroupUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
