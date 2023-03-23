//Контроллер cartController подключится к модулю market. Если указать в имени angular.module('market', ['ngStorage'])
//то создастся новый модуль. Создание модуля мы выполняем в файле index.js, а здесь подключаемся к уже созданному.
angular.module('market').controller('cartController', function ($rootScope, $scope, $http, $localStorage) {
    const cartPath = 'http://localhost:5555/cart/api/v1/cart';
    const corePath = 'http://localhost:5555/core/api/v1';

$scope.loadCart = function () {
        console.log("loadCart");
        console.log($localStorage.marchMarketGuestCartId);
        console.log('loadCart + ' + cartPath + "/" + $localStorage.marchMarketGuestCartId)
        $http.get(cartPath + "/" + $localStorage.marchMarketGuestCartId)
            .then(function (response) {
                console.log(response.data);
                $scope.cart = response.data;
                $scope.isCartEmpty();
            })
    };

$rootScope.mergeCart = function () {
    console.log("mergeCart");
    $http.get(cartPath + "/" + $localStorage.marchMarketGuestCartId + '/merge')
        .then(function (response) {
            console.log(response.data);
            $scope.cart = response.data;
            $scope.isCartEmpty();
        })

}

    $scope.isCartEmpty = function () {
        console.log($scope.cart.totalPrice)
        if($scope.cart.totalPrice > 0) {
            return true;
        } else {
            return false;
        }
    };


    $scope.changeItemQuantity = function (productId, delta) {
        console.log("changeItemQuantity" + "productId - " + productId + ", delta - " + delta)
        $http({
            url: cartPath + "/" + $localStorage.marchMarketGuestCartId + "/changequantity",
            method: 'GET',
            params: {
                productId: productId,
                delta: delta,

            }
        }).then(function (response) {
        $scope.loadCart();
        })
    };

    $scope.deleteProductFromCart = function (productId) {
        $http({
            url: cartPath + "/" + $localStorage.marchMarketGuestCartId + "/delete",
            method: 'DELETE',
            params: {
                productId: productId,
            }
        }).then(function (response) {
            console.log(response.data)
            $scope.loadCart();
        })
    };

    $scope.clearCart = function () {
        $http.delete(cartPath + "/" + $localStorage.marchMarketGuestCartId + "/clear")
            .then(function (response) {
            console.log(response.data)
            $scope.loadCart();
        })
    };
   //TODO Нужно докрутить создание заказа. В этом js нет связки с LoggedIn
    $scope.makeNewOrder = function () {
            console.log("makeNewOrder")
            console.log($scope.cart.totalPrice)
            console.log($scope.cart.items);

            if($scope.isUserLoggedIn()) {
                $http.post(corePath + "/orders")
                    .then(function successCallback(response){
                        $scope.loadCart();
                        alert("Номер заказа - " + response.data);
                    },function errorCallback(response){
                            console.log("errorMakeOrder" + response)
                        });
            }
    };

    $scope.guestMakeNewOrder = function () {
        console.log("guestMakeNewOrder");
        alert("Требуется авторизоваться!")

    }
    $scope.loadCart();
});