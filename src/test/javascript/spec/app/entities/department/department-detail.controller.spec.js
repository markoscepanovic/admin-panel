'use strict';

describe('Department Detail Controller', function () {
    var $scope, $rootScope;
    var MockEntity, MockDepartment, MockUserProfile;
    var createController;

    beforeEach(inject(function ($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDepartment = jasmine.createSpy('MockDepartment');
        MockUserProfile = jasmine.createSpy('MockUserProfile');


        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity,
            'Department': MockDepartment,
            'UserProfile': MockUserProfile
        };
        createController = function () {
            $injector.get('$controller')("DepartmentDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function () {
        it('Unregisters root scope listener upon scope destruction', function () {
            var eventType = 'adminPanelApp:departmentUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
