var LT = angular.module('LTControllers', ['fromNow']);

LT.controller('ChatCtrl', function($scope,$location){
  $scope.convos = [
    {name: "Grace Kennedy",
      number: "15083453910",
      lastText: "See you there!",
      lastTextedAt: (new Date(new Date()-5e4))},
    {name: "Karen Muller",
      number: "14135317608",
      lastText: "Yo, bro!",
      lastTextedAt: (new Date(new Date()-11e5))},
    {name: "Tom Renaud",
      number: "15083407997",
      lastText: "You guys coming up this weekend?",
      lastTextedAt: (new Date(new Date()-864e5))}
  ];
  $scope.seeConversation = function(){
    $location.path('/conversation');
  };
  var fn = function(){
    Messaging.getConversations(function(err, convos){
      if(err) return console.log(err);
      $scope.convos = [];
      for(var i=0;i<convos.length;i++){
        $scope.convos.push({
          name: convos[i].person,
          number: convos[i].address,
          lastText: convos[i].body,
          lastTextedAt: (new Date(parseInt(convos[i].date))).toLocaleString(),
          json: JSON.stringify(convos[i],null,2),
          ts: new Date(parseInt(convos[i].date))
        });
      }
    });
  };
  if(!window.Cordova) return document.addEventListener("deviceready", fn, false);
  fn();
});

LT.controller('NewMessageCtrl', function($scope){
});
