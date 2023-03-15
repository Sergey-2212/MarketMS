(function () {
    angular
        .module('market', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/store', {
                templateUrl: 'store/store.html',
                controller: 'storeController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        const cartPath = 'http://localhost:5555/cart/api/v1/cart';
        console.log('rootScope - ' + $rootScope);
        if ($localStorage.marchMarketUser) {
            try {
                let jwt = $localStorage.marchMarketUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");
                    delete $localStorage.marchMarketUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.marchMarketUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.marchMarketUser.token;
            }
        }
            if (!$localStorage.marchMarketGuestCartId) {
                 $http.get(cartPath + '/generate_id')
                .then(function (response) {
                    $localStorage.marchMarketGuestCartId = response.data.value;
                    console.log($localStorage.marchMarketGuestCartId);
                });
        }
    }
})();

angular.module('market').controller('indexController', function ($rootScope, $scope, $http, $location, $localStorage) {
    const authPath = 'http://localhost:5555/authentication/auth';
    $scope.tryToAuth = function () {
        $http.post(authPath, $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.marchMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $location.path('/');
                    console.log('location -' + $location.path)
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $location.path('/');
    };

    $scope.clearUser = function () {
        delete $localStorage.marchMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.marchMarketUser) {
            return true;
        } else {
            return false;
        }
    };
});


//angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
//    const contextPath = 'http://localhost:8080/market/api/v1';
//    const cartPath = 'http://localhost:5555/cart/api/v1/cart';
//    const corePath = 'http://localhost:5555/core/api/v1';
//    const authPath = 'http://localhost:5555/authentication/auth';
//
//    $scope.tryToAuth = function () {
//        $http.post(authPath, $scope.user)
//            .then(function successCallback(response) {
//                if (response.data.token) {
//                    console.log("tryToAuth  " + response.data.token)
//                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
//                    $localStorage.MarketUser = {username: $scope.user.username, token: response.data.token};
//
//                    $scope.user.username = null;
//                    $scope.user.password = null;
//                }
//            }, function errorCallback(response) {
//            });
//    };
//
//    $scope.tryToLogout = function () {
//        $scope.clearUser();
//        $scope.user = null;
//    };
//
//    $scope.clearUser = function () {
//        delete $localStorage.MarketUser;
//        $http.defaults.headers.common.Authorization = '';
//    };
//
//    $scope.isUserLoggedIn = function () {
//        if ($localStorage.MarketUser) {
//            return true;
//        } else {
//            return false;
//        }
//    };
//
//
//
//
//
//
//    if ($localStorage.MarketUser) {
//        try {
//            let jwt = $localStorage.MarketUser.token;
//            let payload = JSON.parse(atob(jwt.split('.')[1]));
//            let currentTime = parseInt(new Date().getTime() / 1000);
//            if (currentTime > payload.exp) {
//                console.log("Token is expired!!!");
//                delete $localStorage.MarketUser;
//                $http.defaults.headers.common.Authorization = '';
//            }
//        } catch (e) {
//        }
//
//        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.MarketUser.token;
//    }
//
//
//
//    // $scope.loadFilteredProducts = function () {
//    //     console.log("loadFilteredProducts")
//    //     $http({
//    //         url: contextPath + '/products/filter',
//    //         method: 'POST',
//    //         params: {
//    //             min: $scope.productListFilter.min,
//    //             max: $scope.productListFilter.max,
//    //         }
//    //     }).then(function (response) {
//    //             console.log(response);
//    //             $scope.ProductsList = response.data;
//    //         })
//    // };
///* Пример использования условий в JS
//    $scope.currentPage = function () {
//        if($scope.productListFilter.min === null && $scope.productListFilter.max === 0) {
//            $scope.loadProducts();
//        } else {
//            $scope.loadFilteredProducts()
//        }
//    }
//
// */
//
//
//
//    console.log("Перед загрузкой продукта")
//    $scope.loadProducts();
//     console.log("End of html.")
//});