var app = angular.module('herald', []);
app.controller('messagesController', function($scope) {
    $scope.messages = [];
    $scope.complaint = null;

    const client = axios.create({
        baseURL: 'http://localhost:8080/',
        headers: { 'Content-Type': 'application/json' }
    });

    const complaintId = Cookies.get("complaintId");

    client.get('complaints/' + complaintId)
        .then((response) => response.data)
        .then((complaint) => $scope.complaint = complaint)
        .then((_) => $scope.$apply());

    client.get('complaints/' + complaintId + '/messages')
        .then((response) => response.data)
        .then((messages) => messages.forEach((message) => $scope.messages.push(message)))
        .then((_) => $scope.$apply())
});
