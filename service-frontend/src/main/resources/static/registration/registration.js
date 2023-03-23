
angular.module('market').controller('registrationController', function ($rootScope, $scope, $http, $localStorage, $location) {
   const authPath = 'http://localhost:5555/authentication';

   $scope.tryToRegister = function () {
      $http.post(authPath + "/registration", $scope.userreg)
          .then(function successCallback(response) {
           if (response.data.token) {
               $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
               $localStorage.marchMarketUser = {username: $scope.userreg.username, token: response.data.token};

               $scope.userreg.username = null;
               $scope.userreg.password = null;
               $scope.userreg.confirmation = null;
               $rootScope.mergeCart();

               $location.path('/');
               console.log('location -' + $location.path)
           }
       }, function errorCallback(response) {
           alert(response.data);
       });
   }

   });