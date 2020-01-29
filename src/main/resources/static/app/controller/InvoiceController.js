'use strict'

var module = angular.module('invoice.controller', []);
module.controller('InvoiceController',
    ['$scope', '$http', '$filter', '$location', 'InvoiceService',
    function ($scope, $http, $filter, $location, InvoiceService) {
        var self = this;

        self.invoices = [];

        function fetchInvoices() {
            InvoiceService.fetchInvoices()
                .then(
                    function (list) {
                        self.invoices = list;
                    },
                    function (errResponse) {
                        console.log('Error while fetching invoices' + errResponse);
                    }
                );
        }













    }]
)