var Pebble = function(nm){
  Pebble.sendMessage = function(title, body){
    Cordova.exec(
        function(){},
        function(){},
        "Pebble",
        "sendMessage",
        [title, body]);
  };

  Pebble.setAppName = function(name){
    Cordova.exec(
        function(){},
        function(){},
        "Pebble",
        "setAppName",
        arguments);
  };

  Pebble.setAppName(nm);
};
