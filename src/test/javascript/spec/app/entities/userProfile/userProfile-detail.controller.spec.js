'use strict';

describe('UserProfile Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockUserProfile, MockRole, MockUser, MockWorkGroup, MockDepartment;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockUserProfile = jasmine.createSpy('MockUserProfile');
        MockRole = jasmine.createSpy('MockRole');
        MockUser = jasmine.createSpy('MockUser');
        MockWorkGroup = jasmine.createSpy('MockWorkGroup');
        MockDepartment = jasmine.createSpy('MockDepartment');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'UserProfile': MockUserProfile,
            'Role': MockRole,
            'User': MockUser,
            'WorkGroup': MockWorkGroup,
            'Department': MockDepartment
        };
        createController = function () {
            $injector.get('$controller')("UserProfileDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'adminPanelApp:userProfileUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
