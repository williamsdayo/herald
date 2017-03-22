var app = angular.module('herald', []);
app.controller('messagesController', function($scope) {
    $scope.complaints = [];

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    client.get('messages')
        .then((response) => response.data)
        .then((complaints) => complaints.forEach((complaint) => $scope.complaints.push(complaint)))
        .then((_) => $scope.$apply())
});
