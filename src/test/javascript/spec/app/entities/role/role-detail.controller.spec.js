'use strict';

describe('Role Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockRole, MockUserProfile;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRole = jasmine.createSpy('MockRole');
        MockUserProfile = jasmine.createSpy('MockUserProfile');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'Role': MockRole,
            'UserProfile': MockUserProfile
        };
        createController = function () {
            $injector.get('$controller')("RoleDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'adminPanelApp:roleUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
