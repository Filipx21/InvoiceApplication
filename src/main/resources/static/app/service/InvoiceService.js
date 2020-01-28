'use strict'

angular.module('invoice.service', [])
    .factory('InvoiceService', ['$http', '$timeout', '$q', 'CONST',
    function ($http, $timeout, $q, CONST) {

        var service = {
            fetchInvoices: fetchInvoices
        };

        function fetchInvoices() {
            var deferred = $q.defer();
            $http.get(CONST.REST_ENDPOINT)
                .then(
                    function (response) {
                       deferred.resolve(response.data);
                    },
                    function (errResponse) {
                        console.log('Error while fetching invoices.');
                        deferred.reject(errResponse);
                    }
                )
            return deferred.promise;
        }

        return service;
    }]);

