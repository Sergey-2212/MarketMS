angular.module('market').controller('administrationController', function ($scope, $http, $localStorage) {

    const corePath = 'http://localhost:5555/core/api/v1';
    const adminCorePath = 'http://localhost:5555/core/admin/api/v1';

    $scope.loadProducts = function (pageNumber = 1) {

        console.log("loadProducts")

        $http({
            url: corePath + '/products',
            method: 'GET',
            params: {
                //делаю универсальную функцию, которая при наличии переданных значений min и max пробрасывает их в контроллер
                // при отсутствии передает null
                min: $scope.productListFilter ? $scope.productListFilter.min : null,
                max: $scope.productListFilter ? $scope.productListFilter.max : null,
                pageNumber: pageNumber,
                pageSize: $scope.productListFilter ? $scope.productListFilter.pageSize : null,
                titleLike: $scope.productListFilter ? $scope.productListFilter.titleLike : null,
                sortProp: $scope.productListFilter ? $scope.productListFilter.sortProp : null,

            }
        }).then(function (response) {
            console.log(response);
            $scope.generatePagesList(response.data.totalPages);
            console.log('response.data.totalPages - ' + response.data.totalPages)
            $scope.ProductsPage = response.data.content;
            console.log(JSON.parse(response.data.pageable.pageSize));
            console.log($scope.ProductsPage.totalPages)
        })

    };

    /* Чтобы функцию созданну в js можно было использовать в html, её нужно положить в scope*/
    $scope.deleteProduct = function (productId) {
        /* alert(studentId); /* выведем в алерте переданный studentId */
        $http({
            url: adminCorePath + '/products/',
            method: 'DELETE',
            params: {
                id: productId,
            }
        })
            .then(function (response) {
                alert('Deleted');
                $scope.loadProducts();  /* перегружаем страницу со списком продуктов */
            });
    }

    $scope.createProduct = function () {
        $scope.newProductJSON.id = '1';
        console.log($scope.newProductJSON);

        $http.post(adminCorePath + '/products', $scope.newProductJSON)
            // $http({
            //     url: contextPath + '/products/',
            //     method: 'POST',
            //     params: {
            //         id: $scope.newProductJSON.id,
            //         title: $scope.newProductJSON.title,
            //         price: $scope.newProductJSON.price,
            //     }
            .then(function(response) {
                alert("Продукт - " + response.data.title + " добавлен.")
                $scope.loadProducts(pageNumber);
            })
    }

    $scope.updateProduct = function () {
        console.log($scope.updateProductJSON);
        $http.put(adminCorePath + "/products", $scope.updateProductJSON)
            .then(function (response) {
                console(response.data);
                alert("Продукт - " + $scope.id + " отредактирован");
                $scope.loadProducts();
            });
    };

    $scope.generatePagesList = function (totalPages) {
        console.log('generatePagesList started. totalPages -  ' + totalPages)
        out = [];
        for (let i = 0; i < totalPages; i++) {
            out.push(i + 1);
        }
        $scope.pagesList = out;
    }

    $scope.loadProducts();

});