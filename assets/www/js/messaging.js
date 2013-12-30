var Messaging = function(){

  Messaging.getConversations = function(cb){
    Cordova.exec(
        function(conversations){
          cb(null, conversations);
        },
        function(error){
          cb(error);
        },
        "Messaging",
        "getConversations",
        []);
  };

};

Messaging.getConversations = function(cb){
  cb(null, [
      {
        person: "Grace Kennedy",
        address: "15083453910",
        body: "See you there!",
        date: ""+(+new Date()-5e4)
      },
      {
        person: "Karen Muller",
        address: "14135317608",
        body: "Yo, bro!",
        date: ""+(+new Date()-11e5)
      }
      ]);
};
