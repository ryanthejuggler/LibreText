var LibreText = angular.module('LibreText', [
    'ngRoute',
    'LTControllers'
    ]);

LibreText.config(['$routeProvider',
    function($routeProvider){
      $routeProvider.
        when('/chats', {
          templateUrl: 'people.html',
          controller: 'ChatCtrl'
        }).
        when('/newMessage', {
          templateUrl: 'newMessage.html',
          controller: 'NewMessageCtrl'
        }).
        when('/settings', {
          templateUrl: 'settings.html',
          controller: 'SettingsCtrl'
        }).
        when('/conversation', {
          templateUrl: 'conversation.html',
          controller: 'ConvoCtrl'
        }).
        when('/contacts', {
          templateUrl: 'contacts.html',
          controller: 'ContactsCtrl'
        }).
        otherwise({
          redirectTo: '/chats'
        });
    }]);

