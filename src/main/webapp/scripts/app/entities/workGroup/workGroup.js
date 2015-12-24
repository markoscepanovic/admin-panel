'use strict';

angular.module('adminPanelApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('workGroup', {
                parent: 'entity',
                url: '/workGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'adminPanelApp.workGroup.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workGroup/workGroups.html',
                        controller: 'WorkGroupController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workGroup');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('workGroup.detail', {
                parent: 'entity',
                url: '/workGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'adminPanelApp.workGroup.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/workGroup/workGroup-detail.html',
                        controller: 'WorkGroupDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('workGroup');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'WorkGroup', function ($stateParams, WorkGroup) {
                        return WorkGroup.get({id: $stateParams.id});
                    }]
                }
            })
            .state('workGroup.new', {
                parent: 'workGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/workGroup/workGroup-dialog.html',
                        controller: 'WorkGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function (result) {
                        $state.go('workGroup', null, {reload: true});
                    }, function () {
                        $state.go('workGroup');
                    })
                }]
            })
            .state('workGroup.edit', {
                parent: 'workGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/workGroup/workGroup-dialog.html',
                        controller: 'WorkGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['WorkGroup', function (WorkGroup) {
                                return WorkGroup.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('workGroup', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('workGroup.delete', {
                parent: 'workGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/workGroup/workGroup-delete-dialog.html',
                        controller: 'WorkGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['WorkGroup', function (WorkGroup) {
                                return WorkGroup.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('workGroup', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            });
    });
