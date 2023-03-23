//Контроллер cartController подключится к модулю market. Если указать в имени angular.module('market', ['ngStorage'])
//то создастся новый модуль. Создание модуля мы выполняем в файле index.js, а здесь подключаемся к уже созданному.
angular.module('market').controller('ordersController', function ($scope, $http, $localStorage) {
       const corePath = 'http://localhost:5555/core/api/v1';

$scope.loadOrders = function () {
        console.log("loadOrders");
        $http.get(corePath + '/orders/')
            .then(function (response) {
                console.log("response.data  + " + response.data);
                console.log(response.data)
                $scope.orderList = response.data
            })
    };

    $scope.loadOrders();
});