(function(angular) {
  'use strict';
angular.module('mycentre', ['ngRoute', 'ngAnimate'])
  .config(['$routeProvider', '$locationProvider','$sceDelegateProvider',
    function($routeProvider, $locationProvider,$sceDelegateProvider) {
      $routeProvider
        .when('/my/centre', {
          templateUrl: 'my/myData.html',
          controller: 'myCentreMainCtrl',
          controllerAs: 'mycentremc'
        })
         ;
      
      $locationProvider.html5Mode(true);
      
      $sceDelegateProvider.resourceUrlWhitelist([
                                                 'self',
                                                 'http://**.qlogo.cn/**',
                                                 'http://**.tongjijinfen.com/**'
                                                 ]);
  }])
  .controller('myCentreMainCtrl', ['$scope','$http','$route','$routeParams','$location',
    function($scope,$http,$route, $routeParams, $location) {
      this.$route = $route;
      this.$location = $location;
      this.$routeParams = $routeParams;
      $http.get('/my/mydata.json').success(function(data) {
    	    $scope.data= data;
    	  });
  }])
  ;
})(window.angular);
