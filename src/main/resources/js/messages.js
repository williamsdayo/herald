var app = angular.module('herald', []);
app.controller('messagesController', function($scope) {
    $scope.messages = [];

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    const complaintId = Cookies.get("complaintId");

    client.get('complaint/' + complaintId + '/messages')
        .then((response) => response.data)
        .then((messages) => messages.forEach((message) => $scope.messages.push(message)))
        .then((_) => $scope.$apply())
});
