(function(angular) {
  'use strict';
angular.module('tongji', ['ngRoute', 'ngAnimate'])
  .config(['$routeProvider', '$locationProvider','$sceDelegateProvider',
    function($routeProvider, $locationProvider,$sceDelegateProvider) {
      $routeProvider
        .when('/ylh/main.do', {
          templateUrl: 'ylh/yinlinghuimain.html',
          controller: 'YingLinHuiMainCtrl',
          controllerAs: 'ylhmain'
        })
         ;
      
      $locationProvider.html5Mode(true);
      
      $sceDelegateProvider.resourceUrlWhitelist([
                                                 'self',
                                                 'http://**.qlogo.cn/**',
                                                 'http://**.tongjijinfen.com/**'
                                                 ]);
  }])
  .controller('YingLinHuiMainCtrl', ['$scope','$http','$route','$routeParams','$location',
    function($scope,$http,$route, $routeParams, $location) {
      this.$route = $route;
      this.$location = $location;
      this.$routeParams = $routeParams;
      $http.get('/ylh/main.json').success(function(data) {
    	    $scope.data= data;
    	  });
  }])
  ;
})(window.angular);
