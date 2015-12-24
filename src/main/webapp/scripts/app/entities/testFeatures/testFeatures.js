'use strict';

angular.module('adminPanelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('testFeatures', {
                parent: 'entity',
                url: '/testFeaturess',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'adminPanelApp.testFeatures.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testFeatures/testFeaturess.html',
                        controller: 'TestFeaturesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('testFeatures');
                        $translatePartialLoader.addPart('satus');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('testFeatures.detail', {
                parent: 'entity',
                url: '/testFeatures/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'adminPanelApp.testFeatures.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/testFeatures/testFeatures-detail.html',
                        controller: 'TestFeaturesDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('testFeatures');
                        $translatePartialLoader.addPart('satus');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TestFeatures', function ($stateParams, TestFeatures) {
                        return TestFeatures.get({id: $stateParams.id});
                    }]
                }
            })
            .state('testFeatures.new', {
                parent: 'testFeatures',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/testFeatures/testFeatures-dialog.html',
                        controller: 'TestFeaturesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    validationStringField: null,
                                    validationRegExpField: null,
                                    blobField: null,
                                    blobFieldContentType: null,
                                    enumField: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                        $state.go('testFeatures', null, {reload: true});
                    }, function () {
                        $state.go('testFeatures');
                    })
                }]
            })
            .state('testFeatures.edit', {
                parent: 'testFeatures',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/testFeatures/testFeatures-dialog.html',
                        controller: 'TestFeaturesDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TestFeatures', function (TestFeatures) {
                                return TestFeatures.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('testFeatures', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('testFeatures.delete', {
                parent: 'testFeatures',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/testFeatures/testFeatures-delete-dialog.html',
                        controller: 'TestFeaturesDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TestFeatures', function (TestFeatures) {
                                return TestFeatures.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('testFeatures', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            });
    });
