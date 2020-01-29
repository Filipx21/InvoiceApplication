'use strict'

var app = angular.module('app',
    [ 'ngRoute'
    , 'invoice.service']
);

app.constrant('CONST', {
    REST_ENDPOINT: 'http://localhost:8080/api'
});

app.config([
    '$routeProvider',
    function ($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'index.html',
                controller: 'InvoiceController as ctrl'
            })
    }
]);